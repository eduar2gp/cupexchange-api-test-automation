package util;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.ExtentTest;
import cupexchange.BaseTest;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

public class TestResultWatcher implements TestWatcher, BeforeEachCallback, AfterEachCallback {

    @Override
    public void testSuccessful(ExtensionContext context) {
        if (BaseTest.test.get() != null) {
            BaseTest.test.get().pass(context.getDisplayName() + " passed.");
        }
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        if (BaseTest.test.get() != null) {
            // Log the failure status
            BaseTest.test.get().fail("Test Failed");

            // Log the exact exception and stack trace
            BaseTest.test.get().log(Status.FAIL, cause);

            // Optional: Add a custom message or detail
            BaseTest.test.get().info("Failure Reason: " + cause.getMessage());

            String responseBody = "Check console/logs for full API trace.";
            BaseTest.test.get().info("Details: " + responseBody);
        }
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        if (BaseTest.test.get() != null) {
            BaseTest.test.get().skip("Test Aborted");
        }
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        if (BaseTest.test.get() != null) {
            BaseTest.test.get().skip("Test Disabled: " + reason.orElse("No reason provided"));
        }
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        // Create an ExtentTest instance for each test and set it into the ThreadLocal
        try {
            if (BaseTest.extent != null) {
                ExtentTest extentTest = BaseTest.extent.createTest(context.getDisplayName());
                BaseTest.test.set(extentTest);
            }
        } catch (Exception ignore) {
            // Don't let reporting initialization break tests
        }
    }

    @Override
    public void afterEach(ExtensionContext context) {
        // Clear the ThreadLocal to avoid leaking between tests
        try {
            BaseTest.test.remove();
        } catch (Exception ignore) {
        }
    }
}

