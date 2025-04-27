#!/bin/bash

echo "Starting Hyperledger Basil Tracker - Both Organizations"
echo "======================================================="
echo "Org1 (Producer) will run on port 8090"
echo "Org2 (Supermarket) will run on port 8092"
echo "======================================================="

# Start Org2 (Supermarket) in the background
echo "Starting Org2 (Supermarket) on port 8092..."
./gradlew bootRun --args='--spring.profiles.active=org2' > org2.log 2>&1 &
ORG2_PID=$!
echo "Org2 started with PID: $ORG2_PID (logs in org2.log)"

# Sleep for a moment to ensure startup messages don't get mixed
sleep 2

# Start Org1 (Producer) in the foreground
echo "Starting Org1 (Producer) on port 8090..."
./gradlew bootRun

# When Org1 is stopped, also stop Org2
echo "Stopping Org2 (Supermarket) with PID: $ORG2_PID"
kill $ORG2_PID

echo "Both applications have been stopped."