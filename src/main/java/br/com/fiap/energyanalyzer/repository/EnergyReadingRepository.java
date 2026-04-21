package br.com.fiap.energyanalyzer.repository;

import br.com.fiap.energyanalyzer.entity.EnergyReading;
import br.com.fiap.energyanalyzer.entity.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EnergyReadingRepository extends JpaRepository<EnergyReading, Long> {

    List<EnergyReading> findByMeterAndMeasuredAtBetweenOrderByMeasuredAtAsc(
            Meter meter, LocalDate from, LocalDate to);

    @Query(value = """
      SELECT TO_CHAR(measured_at,'YYYY-MM-DD') AS d, SUM(kwh) AS total
      FROM energy_readings
      WHERE meter_id = :meterId AND measured_at BETWEEN :from AND :to
      GROUP BY TO_CHAR(measured_at,'YYYY-MM-DD')
      ORDER BY d
      """, nativeQuery = true)
    List<Object[]> aggregateByDay(@Param("meterId") Long meterId,
                                  @Param("from") LocalDate from,
                                  @Param("to") LocalDate to);

    @Query(value = """
      SELECT TO_CHAR(measured_at,'YYYY-MM') AS m, SUM(kwh) AS total
      FROM energy_readings
      WHERE meter_id = :meterId AND measured_at BETWEEN :from AND :to
      GROUP BY TO_CHAR(measured_at,'YYYY-MM')
      ORDER BY m
      """, nativeQuery = true)
    List<Object[]> aggregateByMonth(@Param("meterId") Long meterId,
                                    @Param("from") LocalDate from,
                                    @Param("to") LocalDate to);
}
