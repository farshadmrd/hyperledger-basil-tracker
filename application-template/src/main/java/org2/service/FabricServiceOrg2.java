package org2.service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hyperledger.fabric.client.Contract;
import org.hyperledger.fabric.client.Gateway;
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
import org2.model.Organization;

@Service
public class FabricServiceOrg2 {

    // Path to test-network directory
    private static final Path PATH_TO_TEST_NETWORK = Paths
            .get("/home/farshad/go/src/github.com/farshadmrd/fabric-samples/test-network");
    
    private static final String CHANNEL_NAME = System.getenv().getOrDefault("CHANNEL_NAME", "mychannel");
    private static final String CHAINCODE_NAME = System.getenv().getOrDefault("CHAINCODE_NAME", "basic");
    
    // Gateway peer end point - Using Org2's peer
    private static final String PEER_ENDPOINT_ORG2 = "localhost:9051";
    private static final String OVERRIDE_AUTH = "peer0.org2.example.com";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private ManagedChannel channel;
    private Gateway gateway;

    public void connect() throws Exception {
        ChannelCredentials credentials = TlsChannelCredentials.newBuilder()
                .trustManager(PATH_TO_TEST_NETWORK.resolve(Paths.get(
                        "organizations/peerOrganizations/org2.example.com/" +
                                "peers/peer0.org2.example.com/tls/ca.crt"))
                        .toFile())
                .build();
        
        // The gRPC client connection should be shared by all Gateway connections
        channel = Grpc.newChannelBuilder(PEER_ENDPOINT_ORG2, credentials)
                .overrideAuthority(OVERRIDE_AUTH)
                .build();

        Gateway.Builder builderOrg2 = Gateway.newInstance()
                .identity(new X509Identity("Org2MSP",
                        Identities.readX509Certificate(
                                Files.newBufferedReader(
                                        PATH_TO_TEST_NETWORK.resolve(Paths.get(
                                                "organizations/peerOrganizations/org2.example.com/" +
                                                        "users/User1@org2.example.com/msp/signcerts/cert.pem"))))))
                .signer(
                        Signers.newPrivateKeySigner(
                                Identities.readPrivateKey(
                                        Files.newBufferedReader(
                                                Files.list(PATH_TO_TEST_NETWORK.resolve(
                                                        Paths.get(
                                                                "organizations/peerOrganizations/org2.example.com/"
                                                                        +
                                                                        "users/User1@org2.example.com/msp/keystore")))
                                                        .findFirst().orElseThrow()))))
                .connection(channel)
                // Default timeouts for different gRPC calls
                .evaluateOptions(options -> options.withDeadlineAfter(5, TimeUnit.SECONDS))
                .endorseOptions(options -> options.withDeadlineAfter(15, TimeUnit.SECONDS))
                .submitOptions(options -> options.withDeadlineAfter(5, TimeUnit.SECONDS))
                .commitStatusOptions(options -> options.withDeadlineAfter(1, TimeUnit.MINUTES));

        gateway = builderOrg2.connect();
    }
    
    // Supermarket can only read basil data, not create/update/delete
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

        System.out.println("Calling getAllBasil function on chaincode as Org2...");
        byte[] result = contract.evaluateTransaction("getAllBasil");
        return prettyJson(result);
    }
    
    public String getBasilHistory(String id) throws Exception {
        if (gateway == null) {
            connect();
        }
        
        Contract contract = gateway
                .getNetwork(CHANNEL_NAME)
                .getContract(CHAINCODE_NAME);

        System.out.println("Getting history for basil with QR code: " + id + " as Org2");
        byte[] result = contract.evaluateTransaction("getHistory", id);
        return prettyJson(result);
    }
    
    // Get all organizations
    public List<Organization> getAllOrganizations() {
        List<Organization> organizations = new ArrayList<>();
        
        // Real organizations from the Fabric network (Pittaluga & Fratelli and Supermarket)
        organizations.add(new Organization("Org1MSP", "Pittaluga & Fratelli", "producer", "Fresh basil producer organization"));
        organizations.add(new Organization("Org2MSP", "Supermarket", "retailer", "Organization that sells basil to consumers"));
        
        return organizations;
    }
    
    // Get organization by ID
    public Organization getOrganizationById(String id) {
        return getAllOrganizations().stream()
                .filter(org -> org.getId().equals(id))
                .findFirst()
                .orElse(null);
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