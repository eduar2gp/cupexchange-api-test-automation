package cupexchange;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        RegisterApiTest.class,
        WalletApiTest.class,
        DepositApiTest.class,
        AccountApiTest.class
})
public class TestSuite {

}
