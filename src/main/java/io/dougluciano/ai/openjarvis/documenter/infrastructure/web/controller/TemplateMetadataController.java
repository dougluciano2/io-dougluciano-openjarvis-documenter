package io.dougluciano.ai.openjarvis.documenter.infrastructure.web.controller;

import io.dougluciano.ai.openjarvis.documenter.infrastructure.web.dto.TemplateMetadataResponseDTO;
import io.dougluciano.ai.openjarvis.documenter.infrastructure.web.dto.TemplateMetadataUpdateDTO;
import io.dougluciano.ai.openjarvis.documenter.model.entity.TemplateMetadata;
import io.dougluciano.ai.openjarvis.documenter.service.ObjectStorageService;
import io.dougluciano.ai.openjarvis.documenter.service.TemplateMetadataService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/templates")
@RequiredArgsConstructor
public class TemplateMetadataController {

    private final TemplateMetadataService service;
    private final ObjectStorageService objectStorageService;

    @GetMapping
    public ResponseEntity<Page<TemplateMetadataResponseDTO>> findAll(Pageable pageable){
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemplateMetadataResponseDTO> findById(@PathVariable UUID id){
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * Cria metadados de um arquivo junto com o arquivo em si.
     * <p>
     * Este endpoint espera uma requisição do tipo {@code multipart/form-data} contendo duas partes:
     * <ol>
     * <li><b>metadata</b>: Uma parte contendo um JSON que representa a entidade {@link TemplateMetadata}.</li>
     * <li><b>file</b>: A parte contendo o arquivo de template (ex: .docx, .odt).</li>
     * </ol>
     * O método orquestra o upload do arquivo para o object storage e a subsequente
     * persistência dos metadados no banco de dados dentro de uma única transação de serviço.
     *
     * @param metadata A parte 'metadata' da requisição, contendo o JSON com os dados do template.
     * @param file A parte 'file' da requisição, contendo o arquivo físico do template.
     * @return Um {@link ResponseEntity} com status <b>HTTP 201 Created</b> em caso de sucesso.
     * O corpo da resposta contém a entidade {@link TemplateMetadata} completa e persistida
     * (incluindo o ID gerado), e o header 'Location' contém a URL para o novo recurso.
     * @throws BusinessException (ou subclasses) caso ocorra uma falha na lógica de negócio,
     * como um erro durante o upload do arquivo.
     */
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<TemplateMetadata> create(
            @RequestPart TemplateMetadata template,
            @RequestPart("file")MultipartFile file) {

        var toPersist = service.create(template, file);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(toPersist.getId())
                .toUri();

        return ResponseEntity.created(location).body(toPersist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TemplateMetadataResponseDTO> update(
            @PathVariable UUID id,
            @RequestPart TemplateMetadataUpdateDTO template,
            @RequestPart(value = "file", required = false) MultipartFile file){
        service.update(id,template, file);

        TemplateMetadataResponseDTO updated = service.findById(id);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
