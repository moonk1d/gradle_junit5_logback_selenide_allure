package hooks;

import ch.qos.logback.classic.Logger;
import io.qameta.allure.Allure;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public final class LogWatcher implements BeforeEachCallback, AfterEachCallback {

  private static final Logger logger = (Logger) LoggerFactory.getLogger(LogWatcher.class);

  @Override
  public void beforeEach(ExtensionContext context) {
    String testCaseName = buildTestCaseName(context);
    MDC.put("testCaseName", buildTestCaseName(context));
    logger.info(String.format("\n ===STARTING TEST CASE [%s]===\n", testCaseName));
  }

  @Override
  public void afterEach(ExtensionContext context) {
    logStackTraceIfTestFail(context);

    Path content = Paths
        .get(String.format("%s/build/logs/%s.log", System.getProperty("user.dir"),
            buildTestCaseName(context)));
    try (InputStream inputStream = Files.newInputStream(content)) {
      Allure.addAttachment("log", inputStream);
    } catch (IOException e) {
      logger.warn("Log file not found", e);
    }
  }

  private void logStackTraceIfTestFail(ExtensionContext context) {
    Optional<Throwable> exception = context.getExecutionException();
    exception.ifPresent(throwable -> {
      StringWriter sw = new StringWriter();
      throwable.printStackTrace(new PrintWriter(sw));
      logger.error(sw.toString());
    });
  }

  private String buildTestCaseName(ExtensionContext context) {
    return String.format("%s.%s", context.getTestClass().get().getName(),
        context.getTestMethod().get().getName());
  }
}
