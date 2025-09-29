package io.dougluciano.ai.openjarvis.documenter.infrastructure.persistence;

import io.dougluciano.ai.openjarvis.documenter.model.entity.TemplateMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TemplateMetadataRepository extends JpaRepository<TemplateMetadata, UUID> {
}
