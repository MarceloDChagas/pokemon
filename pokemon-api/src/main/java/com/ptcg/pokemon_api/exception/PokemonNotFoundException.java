package com.ptcg.pokemon_api.exception;

public class PokemonNotFoundException extends RuntimeException {
    public PokemonNotFoundException(String id) {
        super("Pokemon not found: " + id);
    }
}
