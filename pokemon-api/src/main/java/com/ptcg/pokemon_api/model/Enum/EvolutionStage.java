package com.ptcg.pokemon_api.model.Enum;

public enum EvolutionStage {
    BASIC,
    STAGE_ONE,
    STAGE_TWO;

    public static EvolutionStage getType(EvolutionStage evolutionStage) {
        try {
            return EvolutionStage.valueOf(evolutionStage.toString().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown evolution stage: " + evolutionStage);
        }
    }
}