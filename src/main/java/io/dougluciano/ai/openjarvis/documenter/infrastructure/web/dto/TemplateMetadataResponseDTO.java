package io.dougluciano.ai.openjarvis.documenter.infrastructure.web.dto;

import io.dougluciano.ai.openjarvis.documenter.agent.configuration.AgentProfileConfiguration;

import java.net.URL;
import java.util.UUID;

public record TemplateMetadataResponseDTO(
        UUID id,
        String templateFriendlyName,
        String bucketName,
        String objectName,
        URL downloadFileUrl,
        AgentProfileConfiguration agentProfileConfiguration
) {
}
