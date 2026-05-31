package cupexchange;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        RegisterApiTest.class,
        ProfileApiTest.class,
        WalletApiTest.class,
        AccountApiTest.class,
        DepositApiTest.class,
        ExchangeLimitOrderApiTest.class,
        ExchangeMarketOrderApiTest.class,
        WithdrawalApiTest.class
})
public class TestSuite {

}
