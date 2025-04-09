package com.ptcg.pokemon_api.exception;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String email) {
        super("Invalid email: " + email + ". Email has to follow the pattern.");
    }

    public InvalidEmailException() {
        super("Invalid email:  Email cannot be null or empty.");
    }
    
}
