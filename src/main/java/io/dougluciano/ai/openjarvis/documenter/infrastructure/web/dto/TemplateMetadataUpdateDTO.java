package io.dougluciano.ai.openjarvis.documenter.infrastructure.web.dto;

import java.util.UUID;

public record TemplateMetadataUpdateDTO(
        String templateFriendlyName,
        UUID agentProfileConfigurationId
) {
}
