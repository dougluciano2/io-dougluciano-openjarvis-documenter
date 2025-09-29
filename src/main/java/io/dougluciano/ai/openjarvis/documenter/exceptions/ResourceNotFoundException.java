package io.dougluciano.ai.openjarvis.documenter.exceptions;

import java.util.UUID;

public class ResourceNotFoundException extends DomainException{

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, UUID id){
        super(String.format("%s com ID %s não encontrado.", resourceName, id));
    }
}
