package br.com.fiap.energyanalyzer.service;

import br.com.fiap.energyanalyzer.entity.EnergyAlert;
import br.com.fiap.energyanalyzer.entity.EnergyReading;
import br.com.fiap.energyanalyzer.entity.Meter;

import br.com.fiap.energyanalyzer.repository.EnergyAlertRepository;
import br.com.fiap.energyanalyzer.repository.EnergyReadingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class ReadingService {

    private final EnergyReadingRepository readingRepo;
    private final EnergyAlertRepository alertRepo;
    private final LimitService limitService;

    public ReadingService(EnergyReadingRepository readingRepo,
                          EnergyAlertRepository alertRepo,
                          LimitService limitService) {
        this.readingRepo = readingRepo;
        this.alertRepo = alertRepo;
        this.limitService = limitService;
    }

    @Transactional
    public EnergyReading saveAndCheckAlerts(EnergyReading energyReading) {
        EnergyReading saved = readingRepo.save(energyReading);
        checkDaily(saved.getMeter(), saved.getMeasuredAt());
        checkMonthly(saved.getMeter(), YearMonth.from(saved.getMeasuredAt()));
        return saved;
    }

    private void checkDaily(Meter meter, LocalDate day) {
        var from = day;
        var to = day;
        BigDecimal total = sumBetween(meter, from, to);
        limitService.findByMeterAndPeriod(meter, "DAILY").ifPresent(limit -> {
            if (total.compareTo(limit.getMaxKwh()) > 0) {
                createAlert(meter, "DAILY", from, to, total, limit.getMaxKwh());
            }
        });
    }

    private void checkMonthly(Meter meter, YearMonth ym) {
        LocalDate from = ym.atDay(1);
        LocalDate to = ym.atEndOfMonth();
        BigDecimal total = sumBetween(meter, from, to);
        limitService.findByMeterAndPeriod(meter, "MONTHLY").ifPresent(limit -> {
            if (total.compareTo(limit.getMaxKwh()) > 0) {
                createAlert(meter, "MONTHLY", from, to, total, limit.getMaxKwh());
            }
        });
    }

    private BigDecimal sumBetween(Meter meter, LocalDate from, LocalDate to) {
        List<EnergyReading> list = readingRepo
                .findByMeterAndMeasuredAtBetweenOrderByMeasuredAtAsc(meter, from, to);
        return list.stream()
                .map(EnergyReading::getKwh)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void createAlert(Meter meter, String type, LocalDate start, LocalDate end,
                             BigDecimal total, BigDecimal limit) {
        EnergyAlert energyAlert = new EnergyAlert();
        energyAlert.setMeter(meter);
        energyAlert.setPeriodType(type);
        energyAlert.setPeriodStart(start);
        energyAlert.setPeriodEnd(end);
        energyAlert.setTotalKwh(total);
        energyAlert.setLimitKwh(limit);
        alertRepo.save(energyAlert);
    }
}
