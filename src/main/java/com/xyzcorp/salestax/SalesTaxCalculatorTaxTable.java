package com.xyzcorp.salestax;

class SalesTaxCalculatorTaxTable implements SalesTaxCalculator {
    TaxTable taxTable;

    SalesTaxCalculatorTaxTable(TaxTable taxTable) {
        this.taxTable = taxTable;
    }

    // Note that we no longer use User, nor do we dig inside
    // the address. (Note: We would use a Money, BigDecimal,
    // etc. in reality).
    @Override
    public float computeSalesTax(Address address, float amount) {
        return amount * taxTable.getTaxRate(address);
    }
}
