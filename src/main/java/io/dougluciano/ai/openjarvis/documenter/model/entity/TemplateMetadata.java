package io.dougluciano.ai.openjarvis.documenter.model.entity;

import io.dougluciano.ai.openjarvis.documenter.agent.configuration.AgentProfileConfiguration;
import io.dougluciano.ai.openjarvis.documenter.model.abstractions.AbstractFullEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidade principal que representa os metadados de um template de documento
 * armazenado em um object storage.
 *
 * @author Douglas O. Luciano - September, 2025
 */
@Entity
@Table(name = "template_metadata")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class TemplateMetadata extends AbstractFullEntity {

    @Column(name = "template_friendly_name", nullable = false, unique = true)
    private String templateFriendlyName;

    @Column(name = "bucket_name", nullable = false)
    private String bucketName;

    @Column(name = "object_name", nullable = false)
    private String objectName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_profile_configuration_id")
    private AgentProfileConfiguration agentProfileConfiguration;
}
