# Encryption Key Management & Performance Analysis System

This project is a local application designed to manage encryption keys and file security operations backed by a relational database. It focuses on secure storage of metadata, key management, and a comparative analysis of cryptographic performance.

The core objective is to implement a **Key Management System (KMS)** that supports both Symmetric (AES) and Asymmetric (RSA) algorithms, while benchmarking the efficiency (Time & Memory) of **OpenSSL** against alternative cryptographic frameworks.

## Key Features

1.  **Local Key Management:**
    *   Generation and management of encryption keys (AES-256, RSA-2048).
    *   Secure mapping of keys to encrypted files within the database.

2.  **File Encryption & Decryption:**
    *   Supports encryption of arbitrary file types.
    *   **Primary Engine:** Integration with **OpenSSL** (via CLI or wrapper).
    *   **Secondary Engine:** Integration with an alternative framework (e.g., *PyCryptodome* or *Bouncy Castle*) for performance comparison.

3.  **Database Integration (CRUD):**
    *   Stores file metadata (original name, encrypted path, extension).
    *   Manages algorithm details and key references.
    *   Logs historical data for all operations.

4.  **Performance Benchmarking:**
    *   **Execution Time:** Measures encryption/decryption duration.
    *   **Memory Usage:** Monitors RAM consumption during operations.
    *   Persists performance metrics in the DB for reporting and analysis.

## 🛠️ System Architecture

### Database Schema (ER Model)
The application utilizes a relational database with the following core entities:
*   **`Algorithms`**: Stores supported types (e.g., AES-CBC, RSA-OAEP).
*   **`Keys`**: References to generated keys (storing paths or encrypted blobs).
*   **`Files`**: Registry of processed files linked to their specific keys.
*   **`PerformanceLogs`**: Comparative data (Engine, Operation, Time, Memory).

## Prerequisites

Before running the application, ensure you have the following installed:

1.  **OpenSSL**: Must be installed and added to your system's PATH.
    *   *Verify:* Run `openssl version` in your terminal.
2.  **Runtime Environment**: [Python 3.x / JDK 17 / .NET 6]
3.  **Database Driver**: (If not using SQLite).

This project is intended for educational purposes.
