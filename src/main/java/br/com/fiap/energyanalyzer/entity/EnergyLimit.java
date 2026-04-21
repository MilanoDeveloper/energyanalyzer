package br.com.fiap.energyanalyzer.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name = "energy_limits",
        uniqueConstraints = @UniqueConstraint(columnNames = {"meter_id", "period"}))
public class EnergyLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O medidor é obrigatório")
    @ManyToOne(optional = false)
    @JoinColumn(name = "meter_id")
    private Meter meter;

    @NotBlank(message = "O período é obrigatório")
    @Size(max = 20, message = "O período deve ter no máximo 20 caracteres")
    @Column(nullable = false, length = 20)
    @Size(min = 1, max = 20)
    private String period;

    @NotNull(message = "O valor máximo de kWh é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O valor máximo deve ser maior que zero")
    @Digits(integer = 15, fraction = 3, message = "O valor máximo deve ter até 15 dígitos inteiros e 3 decimais")
    @Column(name = "max_kwh", nullable = false, precision = 18, scale = 3)
    private BigDecimal maxKwh;

    public Long getId() {
        return id;
    }

    public Meter getMeter() {
        return meter;
    }

    public void setMeter(Meter meter) {
        this.meter = meter;
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
