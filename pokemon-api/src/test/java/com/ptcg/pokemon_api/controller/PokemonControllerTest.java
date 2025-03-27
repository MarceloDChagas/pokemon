package com.ptcg.pokemon_api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptcg.pokemon_api.model.Pokemon;
import com.ptcg.pokemon_api.model.Enum.PokemonType;
import com.ptcg.pokemon_api.model.valueObject.*;
import com.ptcg.pokemon_api.service.PokemonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@WebMvcTest(PokemonController.class) // Carrega apenas o contexto web do Spring
public class PokemonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PokemonService pokemonService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetHelloPokemon() throws Exception {
        mockMvc.perform(get("/pokemon"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, Pokemon!"));
    }

    @Test
    public void testGetPokemonById_Success() throws Exception {
        Pokemon mockPokemon = new Pokemon.Builder()
                .id("1")
                .name(new Name("Pikachu"))
                .type(PokemonType.ELECTRIC)
                .build();

        when(pokemonService.getPokemonById("1")).thenReturn(mockPokemon);

        mockMvc.perform(get("/pokemon/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name.name").value("Pikachu"));
    }

   @Test
    public void testGetPokemonById_NotFound() throws Exception {
    when(pokemonService.getPokemonById("999")).thenReturn(null);

    mockMvc.perform(get("/pokemon/999"))
            .andExpect(status().isNotFound()); // Espera 404, não 500
}

    @Test
    public void testGetPokemonByType_Success() throws Exception {
        Pokemon electricPokemon = new Pokemon.Builder().type(PokemonType.ELECTRIC).build();
        List<Pokemon> mockList = Arrays.asList(electricPokemon);

        when(pokemonService.getPokemonByType("ELECTRIC")).thenReturn(mockList);

        mockMvc.perform(get("/pokemon/type/ELECTRIC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("ELECTRIC"));
    }

    @Test
    public void testGetPokemonByType_EmptyList() throws Exception {
        when(pokemonService.getPokemonByType("UNKNOWN")).thenReturn(Collections.emptyList());
    
        mockMvc.perform(get("/pokemon/type/UNKNOWN"))
                .andExpect(status().isNotFound()); // Espera 404
    }

    @Test
    public void testGetAllPokemons_Success() throws Exception {
        Pokemon p1 = new Pokemon.Builder().id("1").build();
        Pokemon p2 = new Pokemon.Builder().id("2").build();
        List<Pokemon> mockList = Arrays.asList(p1, p2);

        when(pokemonService.getAllPokemons()).thenReturn(mockList);

        mockMvc.perform(get("/pokemon/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetAllPokemons_EmptyList() throws Exception {
        when(pokemonService.getAllPokemons()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/pokemon/all"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreatePokemon_Success() throws Exception {
        Pokemon newPokemon = new Pokemon.Builder()
                .id("3")
                .name(new Name("Charmander"))
                .type(PokemonType.FIRE)
                .build();

        when(pokemonService.savePokemon(any(Pokemon.class))).thenReturn(newPokemon);

        mockMvc.perform(post("/pokemon/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPokemon)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("3"));
    }
}