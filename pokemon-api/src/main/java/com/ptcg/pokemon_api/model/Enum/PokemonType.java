package com.ptcg.pokemon_api.model.Enum;

public enum PokemonType {
    FIRE,
    WATER,
    GRASS,
    ELECTRIC,
    PSYCHIC,
    FIGHTING,
    NORMAL,
    ROCK,
    GHOST,
    DRAGON;

    public static PokemonType fromString(String type) {
        try {
            return PokemonType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}