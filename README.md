# Introduction 
This sample is designed for use in IntelliJ and includes source code for testing the Mobile frontend using SauceLabs 
and service API backend using RestAssured. In addition, it uses the following testing libraries:
- TestNG
- Cucumber

# Getting Started
TODO: Guide users through getting your code up and running on their own system. In this section you can talk about:
1. Install JDK 8
2. Setup JAVA_HOME
3. Install Maven
4. Setup MAVEN_HOME & M2_HOME
5. Specify Maven bin directory in System path
6. Acquire a license to use Saucelabs
7. Setup SAUCELABS_USERNAME & SAUCELABS_ACCESS_KEY environment variables based on account credentials
8. Make sure JDK 1.8 is specified under File -> Settings ->Build, Execution, & Deployment ->Build Tools-> Maven -> Runner 
9. Make sure JDK 1.8 is specified under File->Project Structure ->Project -> Project Settings
10. Upload app to SauceLabs through manual upload or Rest CURL command
### Documentation
If you want to learn more refer the following:
- [RestAssured Tutorial](https://www.toolsqa.com/rest-assured-tutorial/)
- [SauceLabs App Automate](https://docs.saucelabs.com/mobile-apps/automated-testing/appium/)
- [Cucumber Docs](https://cucumber.io/docs/cucumber/)
- [TestNG Docs](https://testng.org/doc/documentation-main.html)

# Build and Test
1. From command line navigate to source project folder and perform a _mvn clean install_ to install dependencies
2. mvn clean test -P=_{specify profile in pom}_

# Contribute
If you would like to contribute to the source, please reach out to Director of Quality Engineering, Antwan Maddox,
or the Automation Team within the Quality Guild.
