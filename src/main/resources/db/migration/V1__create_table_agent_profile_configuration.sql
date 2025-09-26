CREATE TABLE agent_profile_configuration
(
    -- 1º - Campo de AbstractEntity
    id BINARY(16) NOT NULL,

    -- 2º - Campos da classe filha (AgentProfileConfiguration)
    client_type          VARCHAR(255) NOT NULL,
    semantic_description TEXT         NOT NULL,
    default_definition   JSON         NOT NULL,

    -- 3º - Campos de AbstractFullEntity (auditoria)
    created_by           VARCHAR(255),
    created_at           DATETIME(6) NOT NULL,
    updated_by           VARCHAR(255),
    updated_at           DATETIME(6) NOT NULL,

    -- Chave Primária
    PRIMARY KEY (id)
);