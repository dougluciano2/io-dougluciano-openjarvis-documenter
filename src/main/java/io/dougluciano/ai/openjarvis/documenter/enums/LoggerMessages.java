package io.dougluciano.ai.openjarvis.documenter.enums;

public enum LoggerMessages {

    MINIO_START("Inicializando o MinIO client"),
    MINIO_BUCKET_NOT_FOUND("Bucket '{}' não encontrado. Criando bucket....."),
    MINIO_BUCKET_CREATE_SUCCESS("Bucket '{}' criado com sucesso no MinIO"),
    MINIO_BUCKET_ALREADY_EXISTS("Bucket '{}' já existe no MinIO"),
    MINIO_BUCKET_CREATE_ERROR("Não foi possível inicializar o bucket do MinIO."),
    MINIO_BUCKET_START_ERROR("Erro ao inicializar o bucket do MinIO: {}");


    private final String value;

    LoggerMessages(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
