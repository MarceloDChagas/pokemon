package com.ptcg.pokemon_api.model;

import org.junit.jupiter.api.Test;

import com.ptcg.pokemon_api.model.Enum.*;
import com.ptcg.pokemon_api.model.valueObject.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class PokemonTest {

    @Test
    public void testPokemonCreationWithBuilder() {
        Pokemon pikachu = new Pokemon.Builder()
                .id("1")
                .name(new Name("Pikachu"))
                .type(PokemonType.ELECTRIC)
                .description(new Description("A friendly electric-type Pokémon.")) 
                .cardType(CardType.POKEMON)
                .rarity(PokemonRarity.COMMON)
                .evolutionStage(EvolutionStage.BASIC)
                .pokemonStatus(new PokemonStatus(50, Arrays.asList(new Attack("Thundershock", 30))))
                .build();
    
        assertNotNull(pikachu);
        assertEquals("1", pikachu.getId());
        assertEquals("Pikachu", pikachu.getName().toString());
        assertEquals(PokemonType.ELECTRIC, pikachu.getType());
        assertEquals("A friendly electric-type Pokémon.", pikachu.getDescription().toString());
        assertEquals(CardType.POKEMON, pikachu.getCardType());
        assertEquals(PokemonRarity.COMMON, pikachu.getRarity());
        assertEquals(EvolutionStage.BASIC, pikachu.getEvolutionStage());
        assertEquals(50, pikachu.getPokemonStatus().getHp());
        assertEquals(1, pikachu.getPokemonStatus().getAttacks().size());
        assertEquals("Thundershock", pikachu.getPokemonStatus().getAttacks().get(0).getName());
        assertEquals(30, pikachu.getPokemonStatus().getAttacks().get(0).getDamage());
    }

    @Test
    public void testPokemonBuilderWithOptionalValues() {
        Pokemon charmander = new Pokemon.Builder()
                .id("2")
                .name(new Name("Charmander"))
                .type(PokemonType.FIRE)
                .build();
    
        assertNotNull(charmander);
        assertEquals("2", charmander.getId());
        assertEquals("Charmander", charmander.getName().toString());
        assertEquals(PokemonType.FIRE, charmander.getType());
        assertNull(charmander.getDescription());
    }
}
