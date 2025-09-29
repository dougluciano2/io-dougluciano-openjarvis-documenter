package io.dougluciano.ai.openjarvis.documenter.service;

import io.dougluciano.ai.openjarvis.documenter.enums.LoggerMessages;
import io.dougluciano.ai.openjarvis.documenter.exceptions.DomainException;
import io.dougluciano.ai.openjarvis.documenter.exceptions.ResourceNotFoundException;
import io.dougluciano.ai.openjarvis.documenter.infrastructure.persistence.TemplateMetadataRepository;
import io.dougluciano.ai.openjarvis.documenter.infrastructure.web.dto.TemplateMetadataResponseDTO;
import io.dougluciano.ai.openjarvis.documenter.infrastructure.web.dto.TemplateMetadataUpdateDTO;
import io.dougluciano.ai.openjarvis.documenter.model.entity.TemplateMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TemplateMetadataService {

    private final TemplateMetadataRepository repository;
    private final ObjectStorageService objectStorageService;
    private final AgentProfileConfigurationService agentProfileService;

    @Value("${object.storage.bucket-name}")
    private String bucketName;

    @Transactional(readOnly = true)
    public Page<TemplateMetadataResponseDTO> findAll(Pageable pageable){

        Page<TemplateMetadata> templates = repository.findAll(pageable);

        return templates.map(this::convertToDtoWithUrl);
    }
    @Transactional(readOnly = true)
    public TemplateMetadataResponseDTO findById(UUID id){
        TemplateMetadata template =  repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("templateMetadata", id));

        return convertToDtoWithUrl(template);
    }

    @Transactional
    public TemplateMetadata create(TemplateMetadata template, MultipartFile file){
        log.info(LoggerMessages.TEMPLATE_SAVE_WITH_FILE_BEGIN.getValue());

        String objectName = generateUniqueObjectName(file.getOriginalFilename());

        try {
            objectStorageService.uploadFile(
                    objectName,
                    file.getInputStream(),
                    file.getSize(),
                    file.getContentType()
            );
        } catch (IOException e) {
            throw new DomainException(LoggerMessages.TEMPLATE_SAVE_WITH_FILE_FAIL.getValue());
        }

        template.setObjectName(objectName);
        template.setBucketName(bucketName);

        log.info(LoggerMessages.TEMPLATE_SAVE_METADATA_BEGIN.getValue());

        TemplateMetadata savedTemplate = repository.save(template);

        log.info(LoggerMessages.TEMPLATE_SAVE_WITH_FILE_SUCCESS.getValue());

        return savedTemplate;
    }

    @Transactional
    public TemplateMetadata update(UUID id, TemplateMetadataUpdateDTO updateDTO, MultipartFile file){

        TemplateMetadata existingMetadata = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(LoggerMessages.ENTITY_NOT_FOUND.getValue(), id));

        if (updateDTO.templateFriendlyName() != null && !updateDTO.templateFriendlyName().isBlank()){
            existingMetadata.setTemplateFriendlyName(updateDTO.templateFriendlyName());
        }

        if (updateDTO.agentProfileConfigurationId() != null){
            var newProfile = agentProfileService.findById(updateDTO.agentProfileConfigurationId());
            existingMetadata.setAgentProfileConfiguration(newProfile);
        }

        if (file != null && !file.isEmpty()){
            log.info(LoggerMessages.TEMPLATE_UPDATING.getValue(), id);
            try {
                objectStorageService.uploadFile(
                        existingMetadata.getObjectName(),
                        file.getInputStream(),
                        file.getSize(),
                        file.getContentType()
                );
            } catch (IOException e){
                throw new DomainException(LoggerMessages.TEMPLATE_UPDATING_FAIL.getValue() + e.getMessage());
            }
        }

        return repository.save(existingMetadata);

    }

    @Transactional
    public void delete(UUID id){
        log.info(LoggerMessages.OBJECT_STORAGE_DELETING_BEGIN.getValue(), id);

        TemplateMetadata template = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TemplateMetadata", id));

        objectStorageService.deleteFile(template.getObjectName());

        repository.delete(template);
    }

    private String generateUniqueObjectName(String originalFilename) {
        log.info(LoggerMessages.FILE_GENERATING_NAME_BEGIN.getValue());

        String newFileName = UUID.randomUUID() + "_" + originalFilename;

        log.info(LoggerMessages.FILE_GENERATING_NAME_SUCCESS.getValue(), newFileName);

        return newFileName;
    }

    private TemplateMetadataResponseDTO convertToDtoWithUrl(TemplateMetadata entity){

        URL downloadUrl = objectStorageService.getPresignedDownloadUrl(entity.getObjectName());

        return new TemplateMetadataResponseDTO(
                entity.getId(),
                entity.getTemplateFriendlyName(),
                entity.getBucketName(),
                entity.getObjectName(),
                downloadUrl,
                entity.getAgentProfileConfiguration()
        );
    }
}
