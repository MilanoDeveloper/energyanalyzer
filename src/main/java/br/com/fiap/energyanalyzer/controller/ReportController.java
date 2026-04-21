package br.com.fiap.energyanalyzer.controller;

import br.com.fiap.energyanalyzer.dto.AggregatedItem;
import br.com.fiap.energyanalyzer.entity.Meter;
import br.com.fiap.energyanalyzer.repository.EnergyAlertRepository;
import br.com.fiap.energyanalyzer.service.MeterService;
import br.com.fiap.energyanalyzer.service.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;
    private final EnergyAlertRepository alertRepo;
    private final MeterService meterService;

    public ReportController(ReportService reportService,
                            EnergyAlertRepository alertRepo,
                            MeterService meterService) {
        this.reportService = reportService;
        this.alertRepo = alertRepo;
        this.meterService = meterService;
    }

    @GetMapping("/consumption")
    public ResponseEntity<?> consumption(
            @RequestParam Long meterId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(defaultValue = "day") String groupBy) {
        Meter m = meterService.byId(meterId);
        if (m == null) return ResponseEntity.badRequest().body("Meter not found");
        List<AggregatedItem> data = reportService.aggregate(meterId, from, to, groupBy);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/alerts")
    public ResponseEntity<?> alerts(
            @RequestParam Long meterId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        Meter meter = meterService.byId(meterId);
        if (meter == null) return ResponseEntity.badRequest().body("Meter not found");
        return ResponseEntity.ok(alertRepo.findByMeterAndPeriodStartGreaterThanEqualAndPeriodEndLessThanEqual(meter, from, to));
    }
}
