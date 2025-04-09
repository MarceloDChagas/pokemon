package com.ptcg.pokemon_api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptcg.pokemon_api.model.Pokemon;
import com.ptcg.pokemon_api.model.Enum.PokemonType;
import com.ptcg.pokemon_api.model.valueobject.*;
import com.ptcg.pokemon_api.service.PokemonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(PokemonController.class)
public class PokemonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PokemonService pokemonService;

    @Autowired
    private ObjectMapper objectMapper;

    private Attack createAttack(String name, int damage) {
        return new Attack(name, damage);
    }

    private PokemonStatus createPokemonStatus(int hp, List<Attack> attacks) {
        return new PokemonStatus(hp, attacks);
    }

    @Test
    public void testGetPokemonById_Success() throws Exception {
        Pokemon mockPokemon = new Pokemon.Builder()
                .id("1")
                .name(new Name("Pikachu"))
                .type(PokemonType.ELECTRIC)
                .pokemonStatus(createPokemonStatus(
                    50, 
                    List.of(createAttack("Thunder Shock", 10), createAttack("Quick Attack", 0))
                ))
                .build();

        when(pokemonService.getPokemonById("1")).thenReturn(mockPokemon);

        mockMvc.perform(get("/1")) 
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Pikachu"))
                .andExpect(jsonPath("$.pokemonStatus.hp").value(50))
                .andExpect(jsonPath("$.pokemonStatus.attack[0].name").value("Thunder Shock"));
    }

    @Test
    public void testGetPokemonByType_Success() throws Exception {
        Pokemon electricPokemon = new Pokemon.Builder()
                .id("1")
                .name(new Name("Pikachu"))
                .type(PokemonType.ELECTRIC)
                .pokemonStatus(createPokemonStatus(
                    50, 
                    List.of(createAttack("Thunder Shock", 10), createAttack("Thunderbolt", 40))
                ))
                .build();
        
        List<Pokemon> mockList = List.of(electricPokemon);

        when(pokemonService.getPokemonByType("ELECTRIC")).thenReturn(mockList);

        mockMvc.perform(get("/type/ELECTRIC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("ELECTRIC"))
                .andExpect(jsonPath("$[0].pokemonStatus.attack[0].damage").value(10));
    }

    @Test
    public void testGetAllPokemons_Success() throws Exception {
        Pokemon p1 = new Pokemon.Builder()
                .id("1")
                .name(new Name("Pikachu"))
                .type(PokemonType.ELECTRIC)
                .pokemonStatus(createPokemonStatus(50, List.of(createAttack("Thunder Shock", 10))))
                .build();
                
        Pokemon p2 = new Pokemon.Builder()
                .id("2")
                .name(new Name("Charmander"))
                .type(PokemonType.FIRE)
                .pokemonStatus(createPokemonStatus(39, List.of(createAttack("Ember", 40))))
                .build();
                
        List<Pokemon> mockList = Arrays.asList(p1, p2);

        when(pokemonService.getAllPokemons()).thenReturn(mockList);

        mockMvc.perform(get("/pokemons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].pokemonStatus.hp").value(39));
    }

    @Test
    public void testCreatePokemon_Success() throws Exception {
        Attack attack = new Attack("Ember", 40);
        PokemonStatus status = new PokemonStatus(39, List.of(attack)); 
        
        Pokemon newPokemon = new Pokemon.Builder()
                .id("3")
                .name(new Name("Charmander"))
                .type(PokemonType.FIRE)
                .pokemonStatus(status) 
                .build();
    
        when(pokemonService.savePokemon(any(Pokemon.class))).thenReturn(newPokemon);
    
        mockMvc.perform(post("/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPokemon)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("3"));
    }
}
