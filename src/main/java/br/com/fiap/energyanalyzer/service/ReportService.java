package br.com.fiap.energyanalyzer.service;

import br.com.fiap.energyanalyzer.dto.AggregatedItem;
import br.com.fiap.energyanalyzer.repository.EnergyReadingRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final EnergyReadingRepository readingRepo;

    public ReportService(EnergyReadingRepository readingRepo) {
        this.readingRepo = readingRepo;
    }

    public List<AggregatedItem> aggregate(Long meterId, LocalDate from, LocalDate to, String groupBy) {
        List<Object[]> rows = "month".equalsIgnoreCase(groupBy)
                ? readingRepo.aggregateByMonth(meterId, from, to)
                : readingRepo.aggregateByDay(meterId, from, to);

        return rows.stream()
                .map(r -> new AggregatedItem((String) r[0], toBig(r[1])))
                .collect(Collectors.toList());
    }

    private BigDecimal toBig(Object o) {
        if (o instanceof BigDecimal b) return b;
        if (o instanceof Number n) return BigDecimal.valueOf(n.doubleValue());
        return BigDecimal.ZERO;
    }
}
