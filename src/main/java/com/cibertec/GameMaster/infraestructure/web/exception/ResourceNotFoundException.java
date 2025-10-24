package com.cibertec.GameMaster.infraestructure.web.exception;

public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String resourceName, Object resourceId) {
    super(String.format("El recurso '%s' con ID %s no existe", resourceName, resourceId));
  }
}

