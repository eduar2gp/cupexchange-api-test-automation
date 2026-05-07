package constants;

public class Endpoints {

    private Endpoints() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static final String LOGIN = "/api/v1/auth/login";
    public static final String REGISTER = "/api/v1/auth/register";
    public static final String VERIFY = "/api/v1/auth/verify?code=";
    public static final String CREATE_WALLET = "/api/v1/wallet/add";
    public static final String DEPOSIT_CASH_ORDER = "/api/v1/merchant/add-cash-order";
    public static final String CREATE_ACCOUNT = "/api/v1/accounts";
    public static final String ACTIVE_ACCOUNTS = "/api/v1/accounts/active?gatewayCode=";
    public static final String ADD_PAYMENT = "/api/v1/payment/add";
    public static final String ADD_RECEIPT = "/api/v1/payment/{ID}/receipt";
    public static final String DEPOSTI = "/api/v1/transaction/deposit";

}