# Gradle + jUnit5 + logback + Selenide + Allure reports

A sample gradle base project which uses jUnit5 for running tests, logback library for logging,
Selenide for browser manipulations and Allure framework for reporting.

# To Run

* will run all the test inside `src/test/tests`.
  ```shell
  $ gradlew clean test --tests tests.* -i
  ```
* will run all the test inside `src/test/tests` on Prod env.
  ```shell
  $ gradlew clean test --tests tests.* -i -Denv=prod
  ```
* will run all the test inside `src/test/tests` on Prod env in `firefox` browser.
  ```shell
  $ gradlew clean test --tests tests.* -i -Denv=prod -Dselenide.browser=firefox
  ```

After the tests are ran, you can see:

* jUnit test reports under `build/reports/tests/index.html`
* logs from sifter appender under `build/logs/test_case_name.log`
* screenshots and saved page sources from Selenide `build/reports/tests`
* Allure results `build/allure-results`
* Allure report `build/reports/allure-report`

## Allure configuration

* [Allure CLI](https://docs.qameta.io/allure/#_commandline) should be installed
* Allure results stored in `build/allure-results`
* Allure report stored in `build/reports/allure-report`
* To open allure report
  ```shell
  $ allure open path_to_allure_report_folder
  ```

### Allure-Selenide

* Allure report will contain Selenide browser interaction history, screenshots/page sources for
  failing test cases

### Allure-Logback

* Each test case will have log file attached as an attachment

## Selenide configuration

Selenide provides a lot of things out of the box, no extra configuration required for sample
project. In case you would like to do custom configuration you can find an example in
AppConfiguration.class.

* Default browser is `chrome`
* By default Selenide puts screenshots and .html to folder `build/reports/tests`
* To change the browser you can use `-Dselenide.browser=firefox`

## Logback configuration

You can find logback configuration here `src/test/resources`

Current configuration contains two appenders:

* ConsoleAppender will output logs to system out stream
* SiftingAppender will output logs to separate `build/logs/${testCaseName}.log` for each test case

## Framework configuration

### Page objects

Path `src/main/pages`

### AppConfiguration

This class makes generic configuration for the framework and allows us to run tests on different
environments by passing system property.

`./gradlew clean test --tests tests.* -i -Denv=prod`

### ConfigurationSetupHook

Initialize application configuration Before test run

### LogWatcher

Creates separate logger before each test case and attaches log file to allure report after each test
case

### RunContext

Represents test run context storage which allows to save/get data and share it between test
cases/test classes during test run.  

Example:  
<code>
RunContext.put("key", 123);
</code>

<code>
int value = RunContext.get("key", Integer.class);
</code>

## Libraries Used

* [Junit](https://junit.org/junit5/docs/current/user-guide/) - 5.7.0
* [Gradle](https://gradle.org/guides) - 6.8.3
* [Logback](http://logback.qos.ch/manual/index.html) - 1.2.3
* [Selenide](https://rselenide.org/documentation.html) - 5.20.1
* [Allure](https://docs.qameta.io/allure/) - 2.13.9
* [Allure-Selenide](https://selenide.org/documentation/reports.html) - 2.13.9
* [Selenide](https://github.com/selenide/selenide/wiki)