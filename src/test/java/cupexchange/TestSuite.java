package cupexchange;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        RegisterApiTest.class, // Runs first
        WalletApiTest.class,        // Runs second
        DepositApiTest.class
})
public class TestSuite {
    // This class remains empty and serves only as a container
}
