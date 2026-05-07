package util;

import com.aventstack.extentreports.Status;
import cupexchange.BaseTest;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

public class TestResultWatcher implements TestWatcher {

    @Override
    public void testSuccessful(ExtensionContext context) {
        BaseTest.test.get().pass(context.getDisplayName() + " passed.");
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        // Log the failure status
        BaseTest.test.get().fail("Test Failed");

        // Log the exact exception and stack trace
        BaseTest.test.get().log(Status.FAIL, cause);

        // Optional: Add a custom message or detail
        BaseTest.test.get().info("Failure Reason: " + cause.getMessage());

        String responseBody = "Check console/logs for full API trace.";
        BaseTest.test.get().info("Details: " + responseBody);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        BaseTest.test.get().skip("Test Aborted");
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        BaseTest.test.get().skip("Test Disabled: " + reason.orElse("No reason provided"));
    }
}
