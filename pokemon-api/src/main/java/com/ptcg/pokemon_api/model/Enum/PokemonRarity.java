package com.ptcg.pokemon_api.model.Enum;

public enum PokemonRarity {
    COMMON,
    UNCOMMON,
    RARE,
    EX,
    ILUSTRATION,
    FULL_ART,
    IMMERSIVE,
    GOLDEN;

   public static  PokemonRarity getRarity(String rarity) {
        try {
            return PokemonRarity.valueOf(rarity.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown rarity: " + rarity);
        }
    }
}
