package org1.service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.hyperledger.fabric.client.Contract;
import org.hyperledger.fabric.client.Gateway;
import org.hyperledger.fabric.client.GatewayException;
import org.hyperledger.fabric.client.identity.Identities;
import org.hyperledger.fabric.client.identity.Signers;
import org.hyperledger.fabric.client.identity.X509Identity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import io.grpc.ChannelCredentials;
import io.grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.TlsChannelCredentials;

@Service
public class FabricService {

    // Path to your test-network directory
    private static final Path PATH_TO_TEST_NETWORK = Paths
            .get("/home/farshad/go/src/github.com/farshadmrd/fabric-samples/test-network");
    
    private static final String CHANNEL_NAME = System.getenv().getOrDefault("CHANNEL_NAME", "mychannel");
    private static final String CHAINCODE_NAME = System.getenv().getOrDefault("CHAINCODE_NAME", "basic");
    
    // Gateway peer end point
    private static final String PEER_ENDPOINT_ORG1 = "localhost:7051";
    private static final String OVERRIDE_AUTH = "peer0.org1.example.com";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private ManagedChannel channel;
    private Gateway gateway;

    public void connect() throws Exception {
        ChannelCredentials credentials = TlsChannelCredentials.newBuilder()
                .trustManager(PATH_TO_TEST_NETWORK.resolve(Paths.get(
                        "organizations/peerOrganizations/org1.example.com/" +
                                "peers/peer0.org1.example.com/tls/ca.crt"))
                        .toFile())
                .build();
        
        // The gRPC client connection should be shared by all Gateway connections
        channel = Grpc.newChannelBuilder(PEER_ENDPOINT_ORG1, credentials)
                .overrideAuthority(OVERRIDE_AUTH)
                .build();

        Gateway.Builder builderOrg1 = Gateway.newInstance()
                .identity(new X509Identity("Org1MSP",
                        Identities.readX509Certificate(
                                Files.newBufferedReader(
                                        PATH_TO_TEST_NETWORK.resolve(Paths.get(
                                                "organizations/peerOrganizations/org1.example.com/" +
                                                        "users/User1@org1.example.com/msp/signcerts/cert.pem"))))))
                .signer(
                        Signers.newPrivateKeySigner(
                                Identities.readPrivateKey(
                                        Files.newBufferedReader(
                                                Files.list(PATH_TO_TEST_NETWORK.resolve(
                                                        Paths.get(
                                                                "organizations/peerOrganizations/org1.example.com/"
                                                                        +
                                                                        "users/User1@org1.example.com/msp/keystore")))
                                                        .findFirst().orElseThrow()))))
                .connection(channel)
                // Default timeouts for different gRPC calls
                .evaluateOptions(options -> options.withDeadlineAfter(5, TimeUnit.SECONDS))
                .endorseOptions(options -> options.withDeadlineAfter(15, TimeUnit.SECONDS))
                .submitOptions(options -> options.withDeadlineAfter(5, TimeUnit.SECONDS))
                .commitStatusOptions(options -> options.withDeadlineAfter(1, TimeUnit.MINUTES));

        gateway = builderOrg1.connect();
    }
    
    public String createBasil(String id, String location) throws Exception {
        if (gateway == null) {
            connect();
        }
        
        Contract contract = gateway
                .getNetwork(CHANNEL_NAME)
                .getContract(CHAINCODE_NAME);

        byte[] result = contract.submitTransaction("createBasil", id, location);
        return new String(result);
    }
    
    public String getBasil(String id) throws Exception {
        if (gateway == null) {
            connect();
        }
        
        Contract contract = gateway
                .getNetwork(CHANNEL_NAME)
                .getContract(CHAINCODE_NAME);

        byte[] result = contract.evaluateTransaction("readBasil", id);
        return prettyJson(result);
    }
    
    public String getAllBasil() throws Exception {
        if (gateway == null) {
            connect();
        }
        
        Contract contract = gateway
                .getNetwork(CHANNEL_NAME)
                .getContract(CHAINCODE_NAME);

        byte[] result = contract.evaluateTransaction("GetAllBasil");
        return prettyJson(result);
    }
    
    public void closeConnection() {
        if (gateway != null) {
            gateway.close();
        }
        
        if (channel != null) {
            try {
                channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    private String prettyJson(final byte[] json) {
        return prettyJson(new String(json, StandardCharsets.UTF_8));
    }

    private String prettyJson(final String json) {
        var parsedJson = JsonParser.parseString(json);
        return gson.toJson(parsedJson);
    }
}