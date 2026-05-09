package cupexchange;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import report.ExtentReportManager;
import util.ExtentLoggingFilter;
import util.TestResultWatcher;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@ExtendWith(TestResultWatcher.class)
public class BaseTest {

    static final String config = "config-prod.properties";
    static Properties properties = new Properties();
    public static ExtentReports extent;
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @BeforeAll
    public static void setup() {
        RestAssured.replaceFiltersWith(java.util.List.of(
                new AllureRestAssured(),
                new ExtentLoggingFilter()
        ));
        extent = ExtentReportManager.getInstance();

        // 1. Read configuration from properties file
        try (InputStream input = Files.newInputStream(Paths.get("src/test/resources/"+config))) {
            properties.load(input);
            // Set base URI, with fallback default
            RestAssured.baseURI = properties.getProperty("api.url", "http://localhost:8080");
        } catch (Exception e) {
            System.err.println("CRITICAL: Failed to load configuration file. Defaulting to localhost.");
            RestAssured.baseURI = "http://localhost:8080";
        }

    }

    @AfterAll
    public static void teardownReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}