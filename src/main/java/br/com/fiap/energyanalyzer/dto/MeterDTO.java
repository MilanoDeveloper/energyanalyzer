package br.com.fiap.energyanalyzer.dto;

import jakarta.validation.constraints.NotBlank;


public class MeterDTO {
    @NotBlank
    private String name;

    private String location;

    private Boolean active;

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}
