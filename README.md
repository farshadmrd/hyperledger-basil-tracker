# 🌿 Hyperledger Basil Tracker

A blockchain-based supply chain solution for tracking basil from farm to table, built with Hyperledger Fabric.

![Basil Tracker Banner](https://img.shields.io/badge/Hyperledger-Basil%20Tracker-brightgreen?style=for-the-badge&logo=hyperledger)

## 📋 Overview

Basil Tracker demonstrates the power of blockchain technology in supply chain management by providing immutable, transparent tracking of basil products. This solution enables:

- 🔍 **Real-time tracking** of basil from origin to consumer
- 🔐 **Immutable records** of the basil's journey
- 🌍 **Provenance verification** for authenticity and quality assurance
- 📱 **RESTful API** for integration with external applications

## 🏗️ Architecture

The project consists of two main components:

1. **Smart Contract (Chaincode)** - The business logic deployed on the Hyperledger Fabric blockchain
2. **Application API** - A Spring Boot REST API that interacts with the blockchain network

## 🚀 Getting Started

### Prerequisites

- [Hyperledger Fabric v2.x](https://hyperledger-fabric.readthedocs.io/en/latest/install.html)
- [Java 11+](https://adoptopenjdk.net/)
- [Gradle 7.x](https://gradle.org/install/)
- [Git](https://git-scm.com/downloads)

### Installation

1. Clone this repository:
   ```bash
   git clone https://github.com/yourusername/hyperledger-basil-tracker.git
   cd hyperledger-basil-tracker
   ```

2. Import the two projects in an IDE as Gradle projects:
   - `chaincode-java`: The Hyperledger Fabric chaincode
   - `application-template`: The Spring Boot REST API

3. Start the Hyperledger Fabric test network:
   ```bash
   cd /path/to/fabric-samples/test-network
   ./network.sh up createChannel -c mychannel -ca
   ./network.sh deployCC -ccn basic -ccp ../path/to/chaincode-java -ccl java
   ```

4. Run the Spring Boot application:
   ```bash
   cd application-template
   ./gradlew bootRun
   ```

## 🔌 API Endpoints

Once the application is running, you can interact with the following endpoints:

- **Create Basil**: `POST http://localhost:8081/api/basil`
  ```json
  {
    "id": "basil001",
    "location": "Italy"
  }
  ```

- **Get Basil by ID**: `GET http://localhost:8081/api/basil/basil001`

- **Get All Basil**: `GET http://localhost:8081/api/basil`

## 🧪 Testing

Run the tests with Gradle:

```bash
# For chaincode
cd chaincode-java
./gradlew test

# For application
cd application-template
./gradlew test
```

## 📦 Project Structure

```
hyperledger-basil-tracker/
├── chaincode-java/        # Smart contract code
│   └── src/               # Source files for chaincode
├── application-template/  # Spring Boot application
│   ├── src/               # Application source files
│   └── build.gradle       # Gradle configuration
└── README.md              # This file
```

## 🛠️ Technologies Used

- **Hyperledger Fabric** - Enterprise-grade blockchain framework
- **Spring Boot** - Java-based framework for building applications
- **Gradle** - Build automation tool
- **Fabric Gateway Java SDK** - For connecting to Fabric networks
- **Google Gson** - For JSON processing

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.

## 🙏 Acknowledgements

- Hyperledger Fabric community
- Spring Boot community
- Everyone who contributed to this project

---

Made with ❤️ for blockchain enthusiasts and supply chain innovators
