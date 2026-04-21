package br.com.fiap.energyanalyzer.controller;

import br.com.fiap.energyanalyzer.dto.ReadingDTO;
import br.com.fiap.energyanalyzer.entity.EnergyReading;
import br.com.fiap.energyanalyzer.entity.Meter;
import br.com.fiap.energyanalyzer.service.MeterService;
import br.com.fiap.energyanalyzer.service.ReadingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/readings")
public class ReadingController {

    private final ReadingService readingService;
    private final MeterService meterService;

    public ReadingController(ReadingService readingService, MeterService meterService) {
        this.readingService = readingService;
        this.meterService = meterService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ReadingDTO req) {
        Meter meter = meterService.byId(req.getMeterId());
        if (meter == null) return ResponseEntity.badRequest().body("Meter not found");
        EnergyReading energyReading = new EnergyReading();
        energyReading.setMeter(meter);
        energyReading.setMeasuredAt(req.getMeasuredAt());
        energyReading.setKwh(req.getKwh());
        return ResponseEntity.ok(readingService.saveAndCheckAlerts(energyReading));
    }
}