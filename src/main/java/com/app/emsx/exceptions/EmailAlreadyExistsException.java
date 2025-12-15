package com.app.emsx.exceptions;

/**
 * EmailAlreadyExistsException
 * Excepción lanzada cuando se intenta registrar un email que ya existe
 * en cualquier entidad del sistema (User, Speaker, Participant)
 */
public class EmailAlreadyExistsException extends RuntimeException {
    
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
    
    public EmailAlreadyExistsException(String email, String entity) {
        super("El correo " + email + " ya está registrado en el sistema");
    }
}
