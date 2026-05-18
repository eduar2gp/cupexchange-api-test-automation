package cupexchange;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        RegisterApiTest.class,
        WalletApiTest.class,
        AccountApiTest.class,
        DepositApiTest.class,
        ExchangeOrderApiTest.class,
        WithdrawalApiTest.class
})
public class TestSuite {

}
