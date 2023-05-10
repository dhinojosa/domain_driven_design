package com.xyzcorp.salestax;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SalesTaxCalculatorLambdaTest {
    @Test
    void testSalesTaxCalculation() {
        SalesTaxCalculatorLambda salesTaxCalculatorLambda =
            new SalesTaxCalculatorLambda(a -> .30f);
        float result = salesTaxCalculatorLambda.computeSalesTax(new Address(), 100.00f);
        assertThat(result).isEqualTo(30);
    }

    @Test
    void testSalesTaxCalculationWithActualTaxTable() {
        TaxTable taxTable = new TaxTable();
        SalesTaxCalculatorLambda salesTaxCalculatorLambda =
            new SalesTaxCalculatorLambda(taxTable::getTaxRate);
        float result = salesTaxCalculatorLambda.computeSalesTax(new Address(), 100.00f);
        assertThat(result).isEqualTo(30);
    }
}
