package com.ptcg.pokemon_api.exception;

public class InvalidHpException extends RuntimeException {
    public InvalidHpException(int hp) {
        super("Invalid HP value: " + hp);
    }
}
