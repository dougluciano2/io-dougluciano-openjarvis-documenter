package io.dougluciano.ai.openjarvis.documenter.enums;

public enum LoggerMessages {

    ENTITY_NOT_FOUND("Entidade com ID {} não encontrada"),

    OBJECT_STORAGE_START("Inicializando o MinIO client"),
    OBJECT_STORAGE_BUCKET_NOT_FOUND("Bucket '{}' não encontrado. Criando bucket....."),
    OBJECT_STORAGE_CREATE_SUCCESS("Bucket '{}' criado com sucesso no MinIO"),
    OBJECT_STORAGE_ALREADY_EXISTS("Bucket '{}' já existe no MinIO"),
    OBJECT_STORAGE_CREATE_ERROR("Não foi possível inicializar o bucket do MinIO."),
    OBJECT_STORAGE_START_ERROR("Erro ao inicializar o bucket do MinIO: {}"),
    OBJECT_STORAGE_UPLOAD_START("Inicializando upload do objeto '{}' para o bucket '{}'"),
    OBJECT_STORAGE_UPLOAD_SUCCESS("Upload do objeto '{}' concluído com sucesso"),
    OBJECT_STORAGE_DOWNLOAD_FILE("Baixando o objeto '{}' do bucket '{}'"),
    OBJECT_STORAGE_DOWNLOAD_SUCCESS("Download do objeto '{}' concluído com sucesso"),
    OBJECT_STORAGE_DOWNLOAD_NOT_FOUND("Arquivo não encontrado no bucket: '{}'"),
    OBJECT_STORAGE_GENERATE_URL("Gerando URL pré-assinada para o objeto '{}'"),
    OBJECT_STORAGE_GENERATE_URL_SUCCESS("URL gerada com sucesso. Validade: 15 minutos."),
    OBJECT_STORAGE_DELETING_BEGIN("Deletando o objeto '{}' do bucket '{}'"),
    OBJECT_STORAGE_DELETING_SUCCESS("Objeto '{}' deletado com sucesso do bucket '{}'"),
    OBJECT_STORAGE_DELETING_FAIL("Falha ao deletar o objeto '{}' do bucket '{}'"),


    TEMPLATE_SAVE_WITH_FILE_BEGIN("Iniciando a criação de template com arquivo."),
    TEMPLATE_SAVE_WITH_FILE_SUCCESS("Template com arquivo criado com sucesso."),
    TEMPLATE_SAVE_WITH_FILE_FAIL("Falha ao processar o arquivo para upload: "),
    TEMPLATE_SAVE_METADATA_BEGIN("Salvando metadados do template no banco de dados."),
    TEMPLATE_DELETING_BEGIN("Iniciando processo de deleção para o template ID: {}"),
    TEMPLATE_DELETING_SUCCESS("Template com ID {} e arquivo '{}' deletados com sucesso."),


    FILE_GENERATING_NAME_BEGIN("Iniciando a geração de nome de arquivo"),
    FILE_GENERATING_NAME_SUCCESS("Nome do arquivo gerado com sucesso: {}"),

    TEMPLATE_UPDATING("Novo arquivo recebido para o template ID {}. Fazendo upload..."),
    TEMPLATE_UPDATING_FAIL("Falha ao processar o novo arquivo para upload: ");



    private final String value;

    LoggerMessages(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
