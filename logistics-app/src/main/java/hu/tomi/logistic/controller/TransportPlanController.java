package hu.tomi.logistic.controller;

import hu.tomi.logistic.dto.DelayRequestDto;
import hu.tomi.logistic.dto.TransportPlanDto;
import hu.tomi.logistic.mapper.TransportPlanMapper;
import hu.tomi.logistic.service.TransportPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transportPlans")
public class TransportPlanController {

    private final TransportPlanService transportPlanService;
    private final TransportPlanMapper transportPlanMapper;

    public TransportPlanController(TransportPlanService transportPlanService,
                                   TransportPlanMapper transportPlanMapper) {
        this.transportPlanService = transportPlanService;
        this.transportPlanMapper = transportPlanMapper;
    }

    @GetMapping
    public List<TransportPlanDto> getAll() {
        return transportPlanService.getAll().stream()
                .map(transportPlanMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransportPlanDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                transportPlanMapper.toDto(transportPlanService.getById(id))
        );
    }

    @PostMapping
    public ResponseEntity<TransportPlanDto> create(@RequestBody TransportPlanDto dto) {
        return ResponseEntity.ok(
                transportPlanMapper.toDto(
                        transportPlanService.save(transportPlanMapper.toEntity(dto))
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transportPlanService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/delay")
    public ResponseEntity<Void> registerDelay(@PathVariable Long id, @RequestBody DelayRequestDto dto) {
        try {
            transportPlanService.registerDelay(id, dto);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

