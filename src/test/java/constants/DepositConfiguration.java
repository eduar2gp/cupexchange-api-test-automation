package constants;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Central configuration class for deposit volumes and exchange order parameters.
 * Contains all configurable values for CUP and USD deposits, and exchange order settings.
 */
public class DepositConfiguration {

    private DepositConfiguration() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // Deposit Volumes
    public static final BigDecimal CUP_DEPOSIT_VOLUME = new BigDecimal("1000000");
    public static final BigDecimal USD_DEPOSIT_VOLUME = new BigDecimal("100000");

    // Limit Order Prices
    public static final BigDecimal CUP_LIMIT_ORDER_BUY_PRICE = new BigDecimal("0.0025");
    public static final BigDecimal CUP_LIMIT_ORDER_SELL_PRICE = new BigDecimal("0.0025");
    public static final BigDecimal USD_LIMIT_ORDER_SELL_PRICE = new BigDecimal("620");
    public static final BigDecimal USD_LIMIT_ORDER_BUY_PRICE = new BigDecimal("616");

    // Market Order Volumes
    public static final BigDecimal MARKET_ORDER_VOLUME = new BigDecimal("50");

    // Limit Order for CUP - Random Volume Configuration
    private static final int CUP_LIMIT_ORDER_MIN_VOLUME = 30;
    private static final int CUP_LIMIT_ORDER_MAX_VOLUME = 50;

    // Limit Order for USD - Fixed Volume
    public static final BigDecimal USD_LIMIT_ORDER_VOLUME = new BigDecimal("1000");

    /**
     * Generates a random volume for CUP limit orders between 12 and 19
     * @return Random BigDecimal volume between 12 and 19
     */
    public static BigDecimal getCupLimitOrderRandomVolume() {
        Random random = new Random();
        int randomVolume = random.nextInt((CUP_LIMIT_ORDER_MAX_VOLUME - CUP_LIMIT_ORDER_MIN_VOLUME) + 1) + CUP_LIMIT_ORDER_MIN_VOLUME;
        return new BigDecimal(randomVolume);
    }

    /**
     * Get CUP deposit volume
     * @return CUP deposit volume
     */
    public static BigDecimal getCupDepositVolume() {
        return CUP_DEPOSIT_VOLUME;
    }

    /**
     * Get USD deposit volume
     * @return USD deposit volume
     */
    public static BigDecimal getUsdDepositVolume() {
        return USD_DEPOSIT_VOLUME;
    }

    /**
     * Get market order volume
     * @return Market order volume
     */
    public static BigDecimal getMarketOrderVolume() {
        return MARKET_ORDER_VOLUME;
    }

    /**
     * Get CUP limit buy order price
     * @return CUP limit buy order price
     */
    public static BigDecimal getCupLimitBuyPrice() {
        return CUP_LIMIT_ORDER_BUY_PRICE;
    }

    /**
     * Get CUP limit sell order price
     * @return CUP limit sell order price
     */
    public static BigDecimal getCupLimitSellPrice() {
        return CUP_LIMIT_ORDER_SELL_PRICE;
    }

    /**
     * Get USD limit sell order price
     * @return USD limit sell order price
     */
    public static BigDecimal getUsdLimitSellPrice() {
        return USD_LIMIT_ORDER_SELL_PRICE;
    }

    /**
     * Get USD limit order volume
     * @return USD limit order volume
     */
    public static BigDecimal getUsdLimitOrderVolume() {
        return USD_LIMIT_ORDER_VOLUME;
    }

    public static BigDecimal getUsdLimitBuyPrice() {
        return USD_LIMIT_ORDER_BUY_PRICE;
    }
}