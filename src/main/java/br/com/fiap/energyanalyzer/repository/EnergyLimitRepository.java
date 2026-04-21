package br.com.fiap.energyanalyzer.repository;

import br.com.fiap.energyanalyzer.entity.EnergyLimit;
import br.com.fiap.energyanalyzer.entity.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnergyLimitRepository extends JpaRepository<EnergyLimit, Long> {
    Optional<EnergyLimit> findByMeterAndPeriod(Meter meter, String period);
}
