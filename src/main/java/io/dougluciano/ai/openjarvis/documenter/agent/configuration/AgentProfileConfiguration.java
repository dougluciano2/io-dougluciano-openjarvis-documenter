package io.dougluciano.ai.openjarvis.documenter.agent.configuration;

import io.dougluciano.ai.openjarvis.documenter.model.abstractions.AbstractFullEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *Componente embutível que armazena as configurações de comportamento
 *para um agente de IA operar sobre uma entidade.
 *
 * @author Douglas O. Luciano - September, 2025
 */
@Entity
@Table(name = "agent_profile_configuration")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class AgentProfileConfiguration extends AbstractFullEntity {

    @Column(name = "client_type", nullable = false)
    private String clientType;

    @Column(columnDefinition = "Text", name = "semantic_description", nullable = false)
    private String semanticDescription;

    @Column(columnDefinition = "JSON", name = "default_definition", nullable = false)
    private String defaultDefinition;

}
