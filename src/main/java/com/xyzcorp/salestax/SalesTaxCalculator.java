package com.xyzcorp.salestax;

public interface SalesTaxCalculator {
    // Note that we no longer use User, nor do we dig inside
    // the address. (Note: We would use a Money, BigDecimal,
    // etc. in reality).
    float computeSalesTax(Address address, float amount);
}
