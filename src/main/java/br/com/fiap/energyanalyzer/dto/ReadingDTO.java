package br.com.fiap.energyanalyzer.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReadingDTO {
    @NotNull
    private Long meterId;

    @NotNull
    private LocalDate measuredAt;

    @NotNull
    private BigDecimal kwh;

    public Long getMeterId() {
        return meterId;
    }

    public void setMeterId(Long meterId) {
        this.meterId = meterId;
    }

    public LocalDate getMeasuredAt() {
        return measuredAt;
    }

    public void setMeasuredAt(LocalDate measuredAt) {
        this.measuredAt = measuredAt;
    }

    public BigDecimal getKwh() {
        return kwh;
    }

    public void setKwh(BigDecimal kwh) {
        this.kwh = kwh;
    }
}