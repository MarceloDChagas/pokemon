package com.ptcg.pokemon_api.model;

import org.junit.jupiter.api.Test;

import com.ptcg.pokemon_api.exception.InvalidAttackException;
import com.ptcg.pokemon_api.exception.InvalidHpException;
import com.ptcg.pokemon_api.model.Enum.*;
import com.ptcg.pokemon_api.model.valueobject.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PokemonTest {

    @Test
    public void testPokemonCreationWithBuilder() {
        Attack attack = new Attack("Thundershock", 30);
        PokemonStatus status = new PokemonStatus(50, List.of(attack));

        Pokemon pikachu = new Pokemon.Builder()
                .id("1")
                .name(new Name("Pikachu"))
                .type(PokemonType.ELECTRIC)
                .description(new Description("A friendly electric-type Pokémon."))
                .cardType(CardType.POKEMON)
                .rarity(PokemonRarity.COMMON)
                .evolutionStage(EvolutionStage.BASIC)
                .pokemonStatus(status)
                .build();

        assertNotNull(pikachu);
        assertEquals("1", pikachu.getId());
        assertEquals("Pikachu", pikachu.getName());
        assertEquals(PokemonType.ELECTRIC, pikachu.getType());
        assertEquals("A friendly electric-type Pokémon.", pikachu.getDescription());
        assertEquals(CardType.POKEMON, pikachu.getCardType());
        assertEquals(PokemonRarity.COMMON, pikachu.getRarity());
        assertEquals(EvolutionStage.BASIC, pikachu.getEvolutionStage());
        assertEquals(50, pikachu.getHp());
        assertEquals(1, pikachu.getAttackCount());
        assertEquals("Thundershock", pikachu.hasAttackNamed("Thundershock"));
        assertEquals(30, pikachu.getAttackDamageByName("Thundershock"));
        assertTrue(pikachu.getAttacks().contains(new Attack("Thundershock", 30)));
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
        assertEquals("Charmander", charmander.getName());
        assertEquals(PokemonType.FIRE, charmander.getType());
        assertNull(charmander.getDescription());
        assertNull(charmander.getCardType());
        assertNull(charmander.getPokemonStatus());
    }

    @Test
    public void testInvalidAttackThrowsException() {
        assertThrows(InvalidAttackException.class, () -> new Attack("", 10));
        assertThrows(InvalidAttackException.class, () -> new Attack("Punch", -5));
    }

    @Test
    public void testInvalidHpThrowsException() {
        List<Attack> validAttacks = List.of(new Attack("Tackle", 10));
        assertThrows(InvalidHpException.class, () -> new PokemonStatus(-1, validAttacks));
    }

    @Test
    public void testEmptyAttackListThrowsException() {
        assertThrows(InvalidAttackException.class, () -> new PokemonStatus(10, Collections.emptyList()));
    }

    @Test
    public void testPokemonStatusMethods() {
        PokemonStatus status = new PokemonStatus(100, Arrays.asList(
            new Attack("Slash", 40),
            new Attack("Ember", 20)
        ));

        assertEquals(100, status.getHp());
        assertEquals(2, status.getAttackCount());
        assertTrue(status.hasAttackNamed("Ember"));
        assertEquals(40, status.getAttackDamage(0));
        assertEquals("Slash", status.getAttackName(0));
        assertEquals(20, status.getDamageByName("Ember"));

        assertThrows(IndexOutOfBoundsException.class, () -> status.getAttackName(5));
        assertThrows(IllegalArgumentException.class, () -> status.getDamageByName("Hydro Pump"));
    }
}
