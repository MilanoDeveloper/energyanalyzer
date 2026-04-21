package br.com.fiap.energyanalyzer.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "energy_alerts")

public class EnergyAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O medidor não pode ser nulo")
    @ManyToOne(optional = false)
    @JoinColumn(name = "meter_id")
    private Meter meter;

    @NotBlank(message = "O tipo de período é obrigatório")
    @Column(name = "period_type", nullable = false, length = 20)
    private String periodType;

    @NotNull(message = "A data de início do período é obrigatória")
    @Column(name = "period_start", nullable = false)
    private LocalDate periodStart;

    @NotNull(message = "A data de fim do período é obrigatória")
    @Column(name = "period_end", nullable = false)
    private LocalDate periodEnd;

    @NotNull(message = "O total de kWh é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O total de kWh deve ser maior que zero")
    @Digits(integer = 15, fraction = 3, message = "O total de kWh deve ter até 15 dígitos inteiros e 3 decimais")
    @Column(name = "total_kwh", nullable = false, precision = 18, scale = 3)
    private BigDecimal totalKwh;

    @NotNull(message = "O limite de kWh é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O limite de kWh deve ser maior que zero")
    @Digits(integer = 15, fraction = 3, message = "O limite de kWh deve ter até 15 dígitos inteiros e 3 decimais")
    @Column(name = "limit_kwh", nullable = false, precision = 18, scale = 3)
    private BigDecimal limitKwh;

    @NotNull(message = "A data de criação é obrigatória")
    @PastOrPresent(message = "A data de criação não pode estar no futuro")
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt = LocalDate.now();

    public Long getId() {
        return id;
    }

    public Meter getMeter() {
        return meter;
    }

    public void setMeter(Meter meter) {
        this.meter = meter;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public LocalDate getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(LocalDate periodStart) {
        this.periodStart = periodStart;
    }

    public LocalDate getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(LocalDate periodEnd) {
        this.periodEnd = periodEnd;
    }

    public BigDecimal getTotalKwh() {
        return totalKwh;
    }

    public void setTotalKwh(BigDecimal totalKwh) {
        this.totalKwh = totalKwh;
    }

    public BigDecimal getLimitKwh() {
        return limitKwh;
    }

    public void setLimitKwh(BigDecimal limitKwh) {
        this.limitKwh = limitKwh;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}