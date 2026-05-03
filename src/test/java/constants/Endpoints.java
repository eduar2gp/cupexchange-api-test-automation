package constants;

public class Endpoints {

    private Endpoints() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static final String LOGIN = "/api/v1/auth/login";
    public static final String REGISTER = "/api/v1/auth/register";
    public static final String VERIFY = "/api/v1/auth/verify?code=";
}