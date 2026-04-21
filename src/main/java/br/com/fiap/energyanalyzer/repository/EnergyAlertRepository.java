package br.com.fiap.energyanalyzer.repository;

import br.com.fiap.energyanalyzer.entity.EnergyAlert;
import br.com.fiap.energyanalyzer.entity.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EnergyAlertRepository extends JpaRepository<EnergyAlert, Long> {
    List<EnergyAlert> findByMeterAndPeriodStartGreaterThanEqualAndPeriodEndLessThanEqual(
            Meter meter, LocalDate from, LocalDate to);
}
