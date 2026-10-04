package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PredictionOrderRequest {
    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public OrderSide getSide() {
        return side;
    }

    public void setSide(OrderSide side) {
        this.side = side;
    }

    public OutcomePosition getOutcomePosition() {
        return outcomePosition;
    }

    public void setOutcomePosition(OutcomePosition outcomePosition) {
        this.outcomePosition = outcomePosition;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    private Long appUserId;

    private Long walletId;

    private Long marketId;

    private OrderSide side;

    private OutcomePosition outcomePosition;

    private BigDecimal price;

    private Integer quantity;

    // --- Enums ---

    public enum OrderSide {
        @JsonProperty("BUY")
        BUY,
        @JsonProperty("SELL")
        SELL
    }

    public enum OutcomePosition {
        @JsonProperty("YES")
        YES,
        @JsonProperty("NO")
        NO
    }
}