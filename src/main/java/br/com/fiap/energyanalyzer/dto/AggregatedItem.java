package br.com.fiap.energyanalyzer.dto;

import java.math.BigDecimal;

public class AggregatedItem {
    private String key;
    private BigDecimal total;

    public AggregatedItem(String key, BigDecimal total) {
        this.key = key;
        this.total = total;
    }

    public String getKey() {
        return key;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
