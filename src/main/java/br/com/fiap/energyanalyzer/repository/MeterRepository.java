package br.com.fiap.energyanalyzer.repository;

import br.com.fiap.energyanalyzer.entity.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeterRepository extends JpaRepository<Meter, Long> {
}
