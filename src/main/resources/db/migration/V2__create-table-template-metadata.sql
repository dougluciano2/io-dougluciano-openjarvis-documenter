CREATE TABLE template_metadata
(
    -- 1º - Campo de AbstractEntity
    id BINARY(16) NOT NULL,

    -- 2º - Campos da classe filha (TemplateMetadata)
    template_friendly_name           VARCHAR(255) NOT NULL,
    bucket_name                      VARCHAR(255) NOT NULL,
    object_name                      VARCHAR(255) NOT NULL,
    agent_profile_configuration_id   BINARY(16),

    -- 3º - Campos de AbstractFullEntity (auditoria)
    created_by                       VARCHAR(255),
    created_at                       DATETIME(6) NOT NULL,
    updated_by                       VARCHAR(255),
    updated_at                       DATETIME(6) NOT NULL,

    -- Chave Primária e Constraints
    PRIMARY KEY (id),
    UNIQUE KEY uk_template_friendly_name (template_friendly_name),
    CONSTRAINT fk_template_to_agent_profile
        FOREIGN KEY (agent_profile_configuration_id)
            REFERENCES agent_profile_configuration (id)
);