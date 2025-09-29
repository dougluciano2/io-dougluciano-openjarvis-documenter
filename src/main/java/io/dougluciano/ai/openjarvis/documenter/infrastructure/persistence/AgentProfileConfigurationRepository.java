package io.dougluciano.ai.openjarvis.documenter.infrastructure.persistence;

import io.dougluciano.ai.openjarvis.documenter.agent.configuration.AgentProfileConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AgentProfileConfigurationRepository extends JpaRepository<AgentProfileConfiguration, UUID> {
}
