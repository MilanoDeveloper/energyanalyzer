package br.com.fiap.energyanalyzer.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "meters")
public class Meter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @Size(max = 100)
    @NotBlank(message = "Nome para contato é obrigatório")
    private String name;

    @Column(length = 200)
    private String location;

    @NotBlank(message = "O status ativo é obrigatório ('Y' ou 'N')")
    @Pattern(regexp = "^[YN]$", message = "O campo ativo deve ser 'Y' (ativo) ou 'N' (inativo)")
    @Column(nullable = false, length = 1)
    private String active = "Y";

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
