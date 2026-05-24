# OpenCart Automation Framework

## Overview

This project is a Hybrid Automation Framework developed for testing the OpenCart e-commerce application using Selenium WebDriver, Java, TestNG, Maven, Selenium Grid, Docker, and Jenkins.

The framework supports:

- Local execution
- Remote execution using Selenium Grid
- Headless browser execution
- Cross-browser testing
- Parallel execution
- CI/CD integration with Jenkins
- Screenshot capture on failure
- Logging and reporting

---

# Tech Stack

- Java
- Selenium WebDriver
- TestNG
- Maven
- Selenium Grid
- Docker
- Jenkins
- Log4j
- Apache Commons IO
- Apache Commons Lang
- Git & GitHub

---

# Framework Features

## Local Execution

Run tests directly on local machine browsers.

Supported browsers:
- Chrome
- Edge
- Firefox

---

## Remote Execution

Run tests remotely using Selenium Grid.

Execution controlled through:

```properties
execution_env=remote
```

---

## Headless Execution

Framework supports headless browser execution using config.properties.

```properties
headless=true
```

OR

```properties
headless=false
```

---

## Logging

Framework uses Log4j for execution logging.

Logs are generated inside:

```text
/logs
```

---

## Reporting

Test reports are generated automatically.

Reports location:

```text
/reports
```

---

## Screenshot Capture

Screenshots are captured automatically on failure.

Screenshots location:

```text
/screenshots
```

---

# Project Structure

```text
OpencartV122
│
├── src/test/java
│   ├── testBase
│   ├── pageObjects
│   ├── testCases
│   └── utilities
│
├── src/test/resources
│   └── config.properties
│
├── reports
├── screenshots
├── logs
├── test-output
├── pom.xml
└── master.xml
```

---

# Configuration

## config.properties

```properties
# Execution Mode
execution_env=remote

# Headless Mode
headless=true

# Application URL
appURL1=https://tutorialsninja.com/demo/
appURL2=https://tutorialsninja.com/demo/

# Test Credentials
email=test@gmail.com
password=Test@123
```

---

# Running Tests

## Run Using Maven

```bash
mvn test
```

---

## Run Using TestNG XML

```bash
mvn test -DsuiteXmlFile=master.xml
```

---

# Selenium Grid Setup

## Start Selenium Grid Using Docker

```bash
docker run -d -p 4444:4444 --name selenium-grid selenium/standalone-chrome:latest
```

---

## Open Selenium Grid UI

```text
http://localhost:4444/ui
```

---

# Jenkins Integration

## Start Jenkins

```bash
java -jar jenkins.war
```

---

## Open Jenkins

```text
http://localhost:8080
```

---

## Jenkins Build Command

```bash
mvn clean test -DsuiteXmlFile=master.xml
```

---

# Git Commands

## Push Project To GitHub

```bash
git add .
git commit -m "updated framework"
git push origin master
```

---

# Cross Browser Execution

Supported browsers:

- Chrome
- Edge
- Firefox

Browser execution handled using:

```java
@Parameters({"os","browser"})
```

---

# Headless Browser Setup

Example Chrome headless configuration:

```java
chromeOptions.addArguments("--headless=new");
chromeOptions.addArguments("--window-size=1920,1080");
chromeOptions.addArguments("--disable-dev-shm-usage");
chromeOptions.addArguments("--no-sandbox");
```

---

# Selenium Grid Remote Driver Example

```java
RemoteWebDriver driver =
        new RemoteWebDriver(
                URI.create("http://localhost:4444")
                        .toURL(),
                chromeOptions);
```

---

# Author

Mohd Danish

Automation Test Engineer | Selenium | Java | TestNG | Maven | Jenkins | Selenium Grid

---

# Future Enhancements

- Extent Reports integration
- Database testing
- API automation integration
- Docker Compose Grid setup
- GitHub Actions CI/CD
- Allure Reporting
- Cloud execution using BrowserStack/Sauce Labs

---

# Conclusion

This framework is designed to support scalable and maintainable UI automation with local and remote execution capabilities. It follows industry-standard automation practices and supports CI/CD integration using Jenkins and Selenium Grid.
