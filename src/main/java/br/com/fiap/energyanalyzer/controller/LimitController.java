package br.com.fiap.energyanalyzer.controller;

import br.com.fiap.energyanalyzer.dto.LimitDTO;
import br.com.fiap.energyanalyzer.entity.EnergyLimit;
import br.com.fiap.energyanalyzer.entity.Meter;
import br.com.fiap.energyanalyzer.service.LimitService;
import br.com.fiap.energyanalyzer.service.MeterService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/limits")
public class LimitController {

    private final LimitService limitService;
    private final MeterService meterService;

    public LimitController(LimitService limitService, MeterService meterService) {
        this.limitService = limitService;
        this.meterService = meterService;
    }

    @PostMapping
    public ResponseEntity<?> upsert(@Valid @RequestBody LimitDTO req) {
        Meter meter = meterService.byId(req.getMeterId());
        if (meter == null) return ResponseEntity.badRequest().body("Meter not found");

        EnergyLimit energyLimit = limitService.findByMeterAndPeriod(meter, req.getPeriod())
                .orElseGet(EnergyLimit::new);
        energyLimit.setMeter(meter);
        energyLimit.setPeriod(req.getPeriod());
        energyLimit.setMaxKwh(req.getMaxKwh());
        return ResponseEntity.ok(limitService.save(energyLimit));
    }
}
