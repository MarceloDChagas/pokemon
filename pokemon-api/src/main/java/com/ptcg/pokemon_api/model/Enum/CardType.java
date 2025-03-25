package com.ptcg.pokemon_api.model.Enum;

public enum CardType {
    POKEMON,
    TRAINER,
    SUPPORTER,
    ITEM;

    public static CardType fromString(String type) {
        try {
            return CardType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown card type: " + type);
        }
    }
}