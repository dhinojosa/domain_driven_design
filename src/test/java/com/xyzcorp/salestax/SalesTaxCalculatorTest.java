package com.xyzcorp.salestax;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class SalesTaxCalculatorTest {
    @Test
    void testSalesTax() {
        TaxTable mock = mock(TaxTable.class);
        SalesTaxCalculator salesTaxCalculator =
            new SalesTaxCalculatorTaxTable(mock);
        salesTaxCalculator.computeSalesTax(new Address(), 1002.0f);
    }
}
