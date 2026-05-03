package cupexchange;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import report.ExtentReportManager;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class BaseTest {

    static final String config = "config.properties";
    static Properties properties = new Properties();
    public static ExtentReports extent;
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @BeforeAll
    public static void setup() {

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

        // 2. Configure Jackson globally to prevent errors on unknown JSON properties
//        RestAssured.config = RestAssured.config().objectMapperConfig(
//                ObjectMapperConfig.objectMapperConfig().jackson2ObjectMapperFactory(
//                        new Jackson2ObjectMapperFactory() {
//                            @Override
//                            public ObjectMapper create(Class cls, String charset) {
//                                ObjectMapper mapper = new ObjectMapper();
//                                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//                                return mapper;
//                            }
//                        }
//                )
//        );
    }

    @AfterAll
    public static void teardownReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
