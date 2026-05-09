package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PendingDeposit {
    private Long id;
    private String referenceId;
    private BigDecimal amount;
    private String status;
    private String type;
    private String timestamp;
    private String receiptPaymentUrl;
    private Long userId;
    private Long managedById;
    private String createdAt;
    private String method;

    public String getReferenceId() {
        return referenceId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }
}