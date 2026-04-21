package br.com.fiap.energyanalyzer.controller;

import br.com.fiap.energyanalyzer.dto.MeterDTO;
import br.com.fiap.energyanalyzer.entity.Meter;
import br.com.fiap.energyanalyzer.service.MeterService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meters")
public class MeterController {

    private final  MeterService service;

    public MeterController(MeterService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Meter> create(@Valid @RequestBody MeterDTO req) {

        Meter meter = new Meter();
        meter.setName(req.getName());
        meter.setLocation(req.getLocation());

        if (req.getActive() != null){
            meter.setActive(req.getActive() ? "Y" : "N");
        }

        return ResponseEntity.ok(service.create(meter));
    }

    @GetMapping
    public ResponseEntity<List<Meter>> list() {
        return ResponseEntity.ok(service.list());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Meter> update(@PathVariable Long id, @RequestBody MeterDTO dto) {
        Meter updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }
}
