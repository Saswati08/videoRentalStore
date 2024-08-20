package com.casumo.videorental.service.calculation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegularRentalPriceCalculationStrategyTest {

    private RegularRentalPriceCalculationStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new RegularRentalPriceCalculationStrategy();
    }

    @Test
    void calculateVideoRentalCost_shouldReturnBasicPriceWhenDaysLessThanOrEqualBasePriceForRegularVideosValid() {
        int days = 3;
        double expectedCost = 30.0;

        double actualCost = strategy.calculateVideoRentalCost(days);

        assertEquals(expectedCost, actualCost);
    }

    @Test
    void calculateVideoRentalCost_shouldReturnAdditionalCostWhenDaysGreaterThanBasePriceForRegularVideosValid() {
        int days = 5;
        double expectedCost = 30.0 + (5 - 3) * 30.0;

        double actualCost = strategy.calculateVideoRentalCost(days);

        assertEquals(expectedCost, actualCost);
    }

    @Test
    void calculateVideoRentalCost_shouldReturnBasicPriceWhenDaysEqualBasePriceForRegularVideosValid() {
        int days = 3;
        double expectedCost = 30.0;

        double actualCost = strategy.calculateVideoRentalCost(days);

        assertEquals(expectedCost, actualCost);
    }

    @Test
    void calculateVideoRentalCost_shouldReturnZeroWhenDaysIsZero() {
        int days = 0;
        double expectedCost = 0.0;

        double actualCost = strategy.calculateVideoRentalCost(days);

        assertEquals(expectedCost, actualCost);
    }
}
