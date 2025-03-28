package com.ptcg.pokemon_api.exception;

public class InvalidDescriptionException extends RuntimeException {
    public InvalidDescriptionException(String description) {
        super("Invalid description: " + description);
    }
}
