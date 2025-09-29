package io.dougluciano.ai.openjarvis.documenter.infrastructure.configuration;

import io.dougluciano.ai.openjarvis.documenter.enums.LoggerMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;

import java.net.URI;

@Configuration
@Slf4j
public class ObjectStorageConfiguration {

    @Value("${object.storage.url}")
    private String url;

    @Value("${object.storage.access-key}")
    private String accessKey;

    @Value("${object.storage.secret-key}")
    private String secretKey;

    @Value("${object.storage.bucket-name}")
    private String bucketName;

    @Bean
    public S3Client s3Client(){
        log.info(LoggerMessages.OBJECT_STORAGE_START.getValue());

        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        Region region = Region.US_EAST_1;

        S3Client s3Client = S3Client.builder()
                .endpointOverride(URI.create(url))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(region)
                .forcePathStyle(true)
                .build();

        try{

            HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build();
            s3Client.headBucket(headBucketRequest);
            log.info(LoggerMessages.OBJECT_STORAGE_ALREADY_EXISTS.getValue(), bucketName);

        } catch (NoSuchBucketException e){
            log.info(LoggerMessages.OBJECT_STORAGE_BUCKET_NOT_FOUND.getValue(), bucketName);
            try {
                CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                        .bucket(bucketName)
                        .build();
                s3Client.createBucket(createBucketRequest);
                log.info(LoggerMessages.OBJECT_STORAGE_CREATE_SUCCESS.getValue(), bucketName);
            } catch (Exception ex){
                log.error(LoggerMessages.OBJECT_STORAGE_START_ERROR.getValue(), ex);
                throw new RuntimeException(LoggerMessages.OBJECT_STORAGE_CREATE_ERROR.getValue(), ex);
            }
        } catch (Exception ex) {
            log.error(LoggerMessages.OBJECT_STORAGE_START_ERROR.getValue(), ex);
            throw new RuntimeException(LoggerMessages.OBJECT_STORAGE_CREATE_ERROR.getValue(), ex);
        }

        return s3Client;

    }

}
