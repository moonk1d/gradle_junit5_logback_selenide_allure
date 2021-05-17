package configuration;

import ch.qos.logback.classic.Logger;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.slf4j.LoggerFactory;

public final class AppConfiguration {

  private static final Logger logger = (Logger) LoggerFactory.getLogger(AppConfiguration.class);
  private static Environment environment;

  public static Environment getEnvironment() {
    return environment;
  }

  public static void init() {
    setupEnvironment();
    setupSelenideConfiguration();
  }

  private static void setupSelenideConfiguration() {
    logger.info("Configuring Selenide");
    Configuration.startMaximized = true;
    Configuration.baseUrl = environment.getUrl();

    logger.info("Configuring AllureSelenide");
    SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
  }

  private static void setupEnvironment() {
    String envProperty = System.getProperty("env");

    if (envProperty == null) {
      envProperty = "default";
    }

    switch (envProperty) {
      case "prod":
        environment = Environment.PROD;
        break;
      case "test":
        environment = Environment.TEST;
        break;
      default:
        // TODO: Add default environment
        environment = Environment.PROD;
        break;
    }

    logger.info("Setting up environment URL as [{}]", environment.getUrl());
  }
}
