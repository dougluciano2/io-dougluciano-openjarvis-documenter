package io.dougluciano.ai.openjarvis.documenter.infrastructure.persistence;

import io.dougluciano.ai.openjarvis.documenter.model.entity.TemplateMetadata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TemplateMetadataRepository extends JpaRepository<TemplateMetadata, UUID> {

    /**
     * Busca todos os metadados de template, forçando o carregamento (eager fetch)
     * da associação com AgentProfileConfiguration para evitar problemas de N+1.
     */
    @Override
    @Query("select tm from TemplateMetadata tm join fetch tm.agentProfileConfiguration")
    Page<TemplateMetadata> findAll(Pageable pageable);

    /**
     * Busca um metadado de template pelo seu ID, forçando o carregamento (eager fetch)
     * da associação com AgentProfileConfiguration.
     */
    @Override
    @Query("select tm from TemplateMetadata tm join fetch tm.agentProfileConfiguration where tm.id = :id")
    Optional<TemplateMetadata> findById(@Param("id") UUID id);

}
