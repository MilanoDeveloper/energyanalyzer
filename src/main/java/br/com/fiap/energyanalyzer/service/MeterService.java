package br.com.fiap.energyanalyzer.service;


import br.com.fiap.energyanalyzer.dto.MeterDTO;
import br.com.fiap.energyanalyzer.entity.Meter;
import br.com.fiap.energyanalyzer.repository.MeterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeterService {
    private final MeterRepository repo;

    public MeterService(MeterRepository repo) {
        this.repo = repo;
    }

    public Meter create(Meter m) {
        return repo.save(m);
    }

    public List<Meter> list() {
        return repo.findAll();
    }

    public Meter byId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Meter update(Long id, MeterDTO dto) {
        Meter existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Meter not found with id " + id));

        existing.setName(dto.getName());
        existing.setLocation(dto.getLocation());
        existing.setActive(dto.getActive() != null && dto.getActive() ? "Y" : "N");

        return repo.save(existing);
    }

}
