package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentResponse {
    private Long id;
    private AccountDetails fromAccount;
    private AccountDetails toAccount;
    private String receiptImageUrl;
    private BigDecimal amount;
    private String requestType;
    private String method;
    private String status;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AccountDetails {
        private Long id;
        private User user;
        private PaymentGateway paymentGateway;
        private String accountName;
        private String accountId;
        private String cardNumber;
        private String phoneNumber;
        private String email;
        private boolean isProvider;
        private boolean isEnabled;
        private String currencyCode;
        private String accountType;
        private boolean enabled;
        private boolean provider;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User {
        private Long id;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PaymentGateway {
        private Long id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
