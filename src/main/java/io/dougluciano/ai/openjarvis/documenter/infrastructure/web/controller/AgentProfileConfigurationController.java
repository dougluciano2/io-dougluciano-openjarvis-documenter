package io.dougluciano.ai.openjarvis.documenter.infrastructure.web.controller;

import io.dougluciano.ai.openjarvis.documenter.agent.configuration.AgentProfileConfiguration;
import io.dougluciano.ai.openjarvis.documenter.service.AgentProfileConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/agent-profiles")
@RequiredArgsConstructor
public class AgentProfileConfigurationController {

    private final AgentProfileConfigurationService service;

    @GetMapping
    public ResponseEntity<List<AgentProfileConfiguration>> findAll(){

        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgentProfileConfiguration> findById(@PathVariable UUID id){

        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<AgentProfileConfiguration> create(@RequestBody AgentProfileConfiguration profile){
        AgentProfileConfiguration toPersist = service.create(profile);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(toPersist.getId())
                .toUri();

        return ResponseEntity.created(location).body(toPersist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgentProfileConfiguration> update(@PathVariable UUID id, @RequestBody AgentProfileConfiguration profile){

        AgentProfileConfiguration toUpdate = service.update(id, profile);

        return ResponseEntity.ok(toUpdate);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
