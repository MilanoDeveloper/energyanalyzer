package br.com.fiap.energyanalyzer.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "energy_readings")
public class EnergyReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O medidor é obrigatório")
    @ManyToOne(optional = false)
    @JoinColumn(name = "meter_id")
    private Meter meter;

    @NotNull(message = "A data da medição é obrigatória")
    @Column(name = "measured_at", nullable = false)
    private LocalDate measuredAt;

    @NotNull(message = "O valor de kWh é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O valor de kWh deve ser maior que zero")
    @Digits(integer = 15, fraction = 3, message = "O valor de kWh deve ter até 15 dígitos inteiros e 3 decimais")
    @Column(nullable = false, precision = 18, scale = 3)
    private BigDecimal kwh;

    public Long getId() {
        return id;
    }

    public Meter getMeter() {
        return meter;
    }

    public void setMeter(Meter meter) {
        this.meter = meter;
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
