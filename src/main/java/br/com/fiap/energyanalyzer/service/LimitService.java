package br.com.fiap.energyanalyzer.service;

import br.com.fiap.energyanalyzer.entity.EnergyLimit;
import br.com.fiap.energyanalyzer.entity.Meter;
import br.com.fiap.energyanalyzer.repository.EnergyLimitRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LimitService {
    private final EnergyLimitRepository repo;

    public LimitService(EnergyLimitRepository repo) { this.repo = repo; }

    public EnergyLimit save(EnergyLimit l) { return repo.save(l); }

    public Optional<EnergyLimit> findByMeterAndPeriod(Meter meter, String period) {
        return repo.findByMeterAndPeriod(meter, period);
    }
}