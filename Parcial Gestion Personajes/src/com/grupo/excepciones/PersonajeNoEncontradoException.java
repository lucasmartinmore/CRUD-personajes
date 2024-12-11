package com.grupo.excepciones;

public class PersonajeNoEncontradoException extends Exception {
    public PersonajeNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
