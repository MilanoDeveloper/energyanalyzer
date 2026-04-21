package br.com.fiap.energyanalyzer.dto;


import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public class LimitDTO {

    @NotNull
    private Long meterId;

    @NotNull
    private String period;

    @NotNull
    private BigDecimal maxKwh;

    public Long getMeterId() {
        return meterId;
    }

    public void setMeterId(Long meterId) {
        this.meterId = meterId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public BigDecimal getMaxKwh() {
        return maxKwh;
    }

    public void setMaxKwh(BigDecimal maxKwh) {
        this.maxKwh = maxKwh;
    }
}