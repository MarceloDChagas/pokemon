package com.ptcg.pokemon_api.exception;

public class InvalidNameException extends RuntimeException {
    public InvalidNameException(String name) {
        super("Invalid Pokémon name: " + name);
    }
}
