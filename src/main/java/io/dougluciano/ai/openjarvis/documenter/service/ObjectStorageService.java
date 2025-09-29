package io.dougluciano.ai.openjarvis.documenter.service;

import io.dougluciano.ai.openjarvis.documenter.enums.LoggerMessages;
import io.dougluciano.ai.openjarvis.documenter.exceptions.DomainException;
import io.dougluciano.ai.openjarvis.documenter.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.InputStream;
import java.net.URL;
import java.time.Duration;

/**
 * Classe responsável pela persistência e recuperação de arquivos, bem como seus metadados
 *
 * @author Douglas O. Luciano - September, 2025
 */
@Service
@Slf4j
public class ObjectStorageService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final String bucketName;

    /**
     * Construtor para inicializar os componentes injetados
     * O S3Presiner é responsável por criar URL's assinadas.
     * Criamos ele a partir da configuração do S3Client principal para garantir consistência
     */
    public ObjectStorageService(S3Client s3Client, S3Configuration s3Configuration, @Value("${object.storage.bucket-name}") String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;

        this.s3Presigner = S3Presigner.builder()
                .region(s3Client.serviceClientConfiguration().region())
                .credentialsProvider(s3Client.serviceClientConfiguration().credentialsProvider())
                .endpointOverride(s3Client.serviceClientConfiguration().endpointOverride().orElse(null))
                .serviceConfiguration(s3Configuration)
                .build();
    }

    /**
     * Faz o upload de um arquivo para o bucket de armazenamento.
     *
     * @param objectName O nome completo do arquivo (chave) no bucket. Ex: "documento-final.docx"
     * @param inputStream O fluxo de dados do arquivo a ser salvo.
     * @param size O tamanho do arquivo em bytes.
     * @param contentType O tipo de mídia do arquivo. Ex: "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
     */
    public void uploadFile(String objectName, InputStream inputStream, long size, String contentType){
        log.info(LoggerMessages.OBJECT_STORAGE_UPLOAD_START.getValue(), objectName, bucketName);
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectName)
                .contentType(contentType)
                .contentLength(size)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, size));
        log.info(LoggerMessages.OBJECT_STORAGE_UPLOAD_SUCCESS.getValue(), objectName);
    }

    /**
     * Baixa o conteúdo de um arquivo do bucket como um array de bytes.
     *
     * @param objectName O nome completo do arquivo (chave) no bucket.
     * @return O conteúdo do arquivo em um array de bytes.
     * @throws ResourceNotFoundException se o arquivo não for encontrado no bucket.
     */
    public byte[] downloadFile(String objectName){
        log.info(LoggerMessages.OBJECT_STORAGE_DOWNLOAD_FILE.getValue(), objectName, bucketName);
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectName)
                .build();
        try {

            ResponseBytes<GetObjectResponse> responseBytes = s3Client.getObjectAsBytes(getObjectRequest);
            log.info(LoggerMessages.OBJECT_STORAGE_DOWNLOAD_SUCCESS.getValue(), objectName);
            return responseBytes.asByteArray();

        } catch (NoSuchKeyException e){
            throw new ResourceNotFoundException("Arquivo '" + objectName + "' não encontrado no storage.");
        }
    }

    /**
     * Gera uma URL de download pré-assinada e temporária para um objeto no bucket.
     *
     * @param objectName O nome completo do arquivo (chave) no bucket.
     * @return Uma URL válida para download que expira em 15 minutos.
     */
    public URL getPresignedDownloadUrl(String objectName){
        log.info(LoggerMessages.OBJECT_STORAGE_GENERATE_URL.getValue(), objectName);

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectName)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(15))
                .getObjectRequest(getObjectRequest)
                .build();

        PresignedGetObjectRequest presignerRequest = s3Presigner.presignGetObject(presignRequest);
        URL url = presignerRequest.url();

        log.info(LoggerMessages.OBJECT_STORAGE_GENERATE_URL_SUCCESS.getValue());
        return url;
    }

    /**
     * Deleta um objeto específico do bucket.
     *
     * @param objectName O nome completo (chave) do objeto a ser deletado.
     */
    public void deleteFile(String objectName) {
        log.info(LoggerMessages.OBJECT_STORAGE_DELETING_BEGIN.getValue(), objectName, bucketName);

        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
            log.info(LoggerMessages.OBJECT_STORAGE_DELETING_SUCCESS.getValue(), objectName, bucketName);
        } catch (Exception e) {
            log.error(LoggerMessages.OBJECT_STORAGE_DELETING_FAIL.getValue(), objectName, bucketName, e);
            throw new DomainException("Falha ao deletar arquivo do object storage: " + e.getMessage());
        }
    }

}
