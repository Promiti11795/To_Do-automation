# 🎭 Playwright Todo Automation Framework

<p align="center">
  <img src="https://playwright.dev/img/playwright-logo.svg" alt="Playwright Logo" width="200"/>
</p>

<p align="center">
  <strong>A comprehensive test automation framework for Todo application</strong><br>
  Built with Playwright + Java + TestNG + Allure
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java"/>
  <img src="https://img.shields.io/badge/Playwright-1.59.0-45ba4b?style=for-the-badge&logo=playwright&logoColor=white" alt="Playwright"/>
  <img src="https://img.shields.io/badge/TestNG-7.11.0-CD6839?style=for-the-badge" alt="TestNG"/>
  <img src="https://img.shields.io/badge/Allure-2.25.0-FFA500?style=for-the-badge" alt="Allure"/>
  <img src="https://img.shields.io/badge/Maven-3.9+-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white" alt="Maven"/>
</p>

---

## 📋 Table of Contents

- [✨ Features](#-features)
- [🛠 Tech Stack](#-tech-stack)
- [📁 Project Structure](#-project-structure)
- [📌 Prerequisites](#-prerequisites)
- [🔧 Installation](#-installation)
- [🚀 Running Tests](#-running-tests)
- [📊 Allure Reports](#-allure-reports)
- [🧪 Test Coverage](#-test-coverage)
- [🌐 API Endpoints Tested](#-api-endpoints-tested)
- [⚙️ Configuration](#️-configuration)
- [📸 Screenshots](#-screenshots)
- [🤝 Contributing](#-contributing)
- [👤 Author](#-author)

---

## ✨ Features

| Feature | Description |
|---------|-------------|
| 🌐 **UI Testing** | Browser automation using Playwright |
| 🔌 **API Testing** | REST API testing with Playwright's APIRequestContext |
| 📄 **Page Object Model** | Clean separation of test logic and page interactions |
| 📊 **Allure Reporting** | Beautiful HTML reports with screenshots and steps |
| ☕ **Java 8+ Features** | Lambda, Stream API, Optional, Functional Interfaces |
| 🧵 **Multithreading** | ExecutorService, CompletableFuture examples |
| 📸 **Screenshot on Failure** | Automatic screenshot capture for failed tests |
| 🔄 **CI/CD Ready** | Easy integration with Jenkins, GitHub Actions |

---

## 🛠 Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| ☕ Java | 17 | Programming Language |
| 🎭 Playwright | 1.59.0 | Browser & API Automation |
| 🧪 TestNG | 7.11.0 | Test Framework |
| 📊 Allure | 2.25.0 | Test Reporting |
| 📦 Maven | 3.9+ | Build & Dependency Management |
| 📝 Gson | 2.10.1 | JSON Parsing |
| 📝 Jackson | 2.16.1 | JSON Parsing (Alternative) |

---

## 📁 Project Structure
To_Do-automation/
│
├── 📁 src/test/java/
│   │
│   ├── 📁 api/                          # API Layer
│   │   ├── 📄 ApiClient.java            # HTTP Client (GET, POST, PUT, DELETE)
│   │   ├── 📄 TodoApiService.java       # Todo API operations
│   │   └── 📄 UserApiService.java       # User API operations
│   │
│   ├── 📁 models/                       # Data Models (POJOs)
│   │   ├── 📄 ApiResponse.java          # Generic API response wrapper
│   │   ├── 📄 Todo.java                 # Todo entity with Builder pattern
│   │   └── 📄 User.java                 # User entity
│   │
│   ├── 📁 pages/                        # Page Object Model
│   │   ├── 📄 BasePage.java             # Base page with common methods
│   │   └── 📄 TodoPage.java             # Todo app page object
│   │
│   ├── 📁 tests/                        # Test Classes
│   │   ├── 📄 BaseTest.java             # UI test base (browser setup)
│   │   ├── 📄 BaseApiTest.java          # API test base (client setup)
│   │   ├── 📄 TodoTest.java             # UI test cases
│   │   ├── 📄 TodoApiTest.java          # Todo API test cases
│   │   └── 📄 UserApiTest.java          # User API test cases
│   │
│   └── 📁 utils/                        # Utilities
│       ├── 📄 ThreadManager.java        # Multithreading utilities
│       ├── 📄 TodoDataProvider.java     # Stream API examples
│       └── 📄 TodoTask.java             # Functional Interface
│
├── 📁 src/test/resources/
│   └── 📄 allure.properties             # Allure configuration
│
├── 📄 pom.xml                           # Maven configuration
├── 📄 testng.xml                        # TestNG suite configuration
├── 📄 .gitignore                        # Git ignore rules
└── 📄 README.md                         # Project documentation

