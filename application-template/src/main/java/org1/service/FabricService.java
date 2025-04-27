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

        System.out.println("Creating basil with qrCode: " + id + ", origin: " + location);
        // Map id to qrCode and location to origin as expected by the chaincode
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

        System.out.println("Calling getAllBasil function on chaincode...");
        byte[] result = contract.evaluateTransaction("getAllBasil");
        return prettyJson(result);
    }
    
    public String testChaincodeConnectivity() throws Exception {
        if (gateway == null) {
            connect();
        }
        
        Contract contract = gateway
                .getNetwork(CHANNEL_NAME)
                .getContract(CHAINCODE_NAME);
        
        StringBuilder results = new StringBuilder();
        results.append("Chaincode connectivity test results:\\n");
        results.append("- Channel: ").append(CHANNEL_NAME).append("\\n");
        results.append("- Chaincode: ").append(CHAINCODE_NAME).append("\\n\\n");

        // Test common function names from asset-transfer-basic chaincode
        String[] functionNames = {
            // Original asset-transfer-basic functions
            "GetAllAssets", "getAllAssets", "ReadAsset", "readAsset",
            // Potential basil-specific functions
            "GetAllBasil", "getAllBasil", "ReadBasil", "readBasil", "queryAllBasil"
        };
        
        for (String functionName : functionNames) {
            try {
                results.append("Testing function '").append(functionName).append("': ");
                if (functionName.contains("All")) {
                    // Try to query all assets/basil
                    byte[] queryResult = contract.evaluateTransaction(functionName);
                    results.append("SUCCESS ✓\\n");
                    results.append("Result: ").append(prettyJson(queryResult)).append("\\n\\n");
                } else if (functionName.contains("Read") || functionName.contains("read")) {
                    // Create test asset first if needed, then try to read it
                    try {
                        String testId = "test-asset-" + System.currentTimeMillis();
                        // Try different create function names
                        try {
                            contract.submitTransaction("CreateAsset", testId, "yellow", "10", "TestOwner", "100");
                        } catch (Exception e1) {
                            try {
                                contract.submitTransaction("createAsset", testId, "yellow", "10", "TestOwner", "100");
                            } catch (Exception e2) {
                                try {
                                    contract.submitTransaction("createBasil", testId, "TestLocation");
                                } catch (Exception e3) {
                                    // Ignore creation errors and try to read an existing asset
                                }
                            }
                        }
                        
                        // Now try to read
                        byte[] readResult = contract.evaluateTransaction(functionName, testId);
                        results.append("SUCCESS ✓\\n");
                        results.append("Result: ").append(prettyJson(readResult)).append("\\n\\n");
                    } catch (Exception e) {
                        // Try with a default ID
                        try {
                            byte[] readResult = contract.evaluateTransaction(functionName, "asset1");
                            results.append("SUCCESS with default ID ✓\\n");
                            results.append("Result: ").append(prettyJson(readResult)).append("\\n\\n");
                        } catch (Exception e2) {
                            results.append("FAILED ✗\\n");
                            results.append("Error: ").append(e2.getMessage()).append("\\n\\n");
                        }
                    }
                }
            } catch (Exception e) {
                results.append("FAILED ✗\\n");
                results.append("Error: ").append(e.getMessage()).append("\\n\\n");
            }
        }
        
        return results.toString();
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

    // Delete basil
    public String deleteBasil(String id) throws Exception {
        if (gateway == null) {
            connect();
        }
        
        Contract contract = gateway
                .getNetwork(CHANNEL_NAME)
                .getContract(CHAINCODE_NAME);

        System.out.println("Deleting basil with QR code: " + id);
        byte[] result = contract.submitTransaction("deleteBasil", id);
        return "Basil deleted successfully";
    }
    
    // Update basil state
    public String updateBasilState(String id, String gps, Long timestamp, String temp, String humidity, String status) throws Exception {
        if (gateway == null) {
            connect();
        }
        
        Contract contract = gateway
                .getNetwork(CHANNEL_NAME)
                .getContract(CHAINCODE_NAME);

        System.out.println("Updating basil state for QR code: " + id);
        byte[] result = contract.submitTransaction(
            "updateBasilState", 
            id, 
            gps, 
            String.valueOf(timestamp), 
            temp, 
            humidity,
            status
        );
        return "Basil state updated successfully";
    }
    
    // Get basil history
    public String getBasilHistory(String id) throws Exception {
        if (gateway == null) {
            connect();
        }
        
        Contract contract = gateway
                .getNetwork(CHANNEL_NAME)
                .getContract(CHAINCODE_NAME);

        System.out.println("Getting history for basil with QR code: " + id);
        byte[] result = contract.evaluateTransaction("getHistory", id);
        return prettyJson(result);
    }
    
    // Transfer ownership
    public String transferOwnership(String id, String newOrgId, String newName) throws Exception {
        if (gateway == null) {
            connect();
        }
        
        Contract contract = gateway
                .getNetwork(CHANNEL_NAME)
                .getContract(CHAINCODE_NAME);

        System.out.println("Transferring ownership of basil with QR code: " + id + " to org: " + newOrgId + ", name: " + newName);
        byte[] result = contract.submitTransaction("transferOwnership", id, newOrgId, newName);
        return "Ownership transferred successfully";
    }
}