package io.dougluciano.ai.openjarvis.documenter.service;

import io.dougluciano.ai.openjarvis.documenter.agent.configuration.AgentProfileConfiguration;
import io.dougluciano.ai.openjarvis.documenter.exceptions.ResourceNotFoundException;
import io.dougluciano.ai.openjarvis.documenter.infrastructure.persistence.AgentProfileConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgentProfileConfigurationService {

    private final AgentProfileConfigurationRepository repository;

    @Transactional(readOnly = true)
    public List<AgentProfileConfiguration> findAll(){
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public AgentProfileConfiguration findById(UUID id){
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("AgentProfileConfiguration", id));
    }

    @Transactional
    public AgentProfileConfiguration create(AgentProfileConfiguration profile){
            return repository.save(profile);

    }

    @Transactional
    public AgentProfileConfiguration update(UUID id, AgentProfileConfiguration profile){
        var toPersist = this.findById(id);

        toPersist.setClientType(profile.getClientType());
        toPersist.setSemanticDescription(profile.getSemanticDescription());
        toPersist.setDefaultDefinition(profile.getDefaultDefinition());

        return repository.save(toPersist);
    }

    @Transactional
    public void delete(UUID id){
        repository.deleteById(id);
    }
}
