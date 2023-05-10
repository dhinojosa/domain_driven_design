package com.xyzcorp.salestax;

import java.util.function.Function;

public class SalesTaxCalculatorLambda implements SalesTaxCalculator {

    private final Function<Address, Float> taxLookup;

    public SalesTaxCalculatorLambda(Function<Address, Float> taxLookup) {
        this.taxLookup = taxLookup;
    }

    @Override
    public float computeSalesTax(Address address, float amount) {
        return amount * taxLookup.apply(address);
    }
}
