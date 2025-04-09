package com.ptcg.pokemon_api.controller;

import com.ptcg.pokemon_api.model.*;
import com.ptcg.pokemon_api.model.Enum.CardType;
import com.ptcg.pokemon_api.model.Enum.EvolutionStage;
import com.ptcg.pokemon_api.model.Enum.PokemonRarity;
import com.ptcg.pokemon_api.model.Enum.PokemonType;
import com.ptcg.pokemon_api.model.valueobject.*;
import com.ptcg.pokemon_api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import java.util.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User testUser;
    private PokemonCollection testCollection;
    private Pokemon testPokemon;
    private CollectionItem testItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User("testuser", "password123", "test@example.com");
        testUser.setId("user123");

        testCollection = new PokemonCollection.Builder()
                .name(new Name("Minha Coleção"))
                .description(new Description("Descrição da coleção"))
                .createdAt(new Date())
                .build();
        testCollection.setId("col123");

        PokemonStatus status = new PokemonStatus(100, Arrays.asList(new Attack("Tackle", 40)));
        testPokemon = new Pokemon.Builder()
                .id("poke123")
                .name(new Name("Bulbasaur"))
                .type(PokemonType.GRASS)
                .description(new Description("A grass-type Pokémon."))
                .cardType(CardType.POKEMON)
                .rarity(PokemonRarity.COMMON)
                .evolutionStage(EvolutionStage.BASIC)
                .pokemonStatus(status)
                .build();

        testItem = new CollectionItem("poke123", 2);
    }

    @Test
    void testCreateUser() {
        when(userService.createUser(any(User.class))).thenReturn(testUser);

        User createdUser = userController.createUser(testUser);

        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUsername());
        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    void testGetUserById() {
        when(userService.getUserById("user123")).thenReturn(testUser);

        User foundUser = userController.getUserById("user123");

        assertNotNull(foundUser);
        assertEquals("user123", foundUser.getId());
        verify(userService, times(1)).getUserById("user123");
    }

    @Test
    void testGetUserByUsername() {
        when(userService.getUserByUsername("testuser")).thenReturn(testUser);

        User foundUser = userController.getUserByUsername("testuser");

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
        verify(userService, times(1)).getUserByUsername("testuser");
    }

    @Test
    void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(testUser);
        users.add(new User("anotheruser", "pass456", "another@example.com"));
        when(userService.getAllUsers()).thenReturn(users);

        List<User> allUsers = userController.getAllUsers();

        assertNotNull(allUsers);
        assertEquals(2, allUsers.size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testUpdateUser() {
        User updatedUser = new User("updateduser", "newpassword", "updated@example.com");
        when(userService.updateUser(eq("user123"), any(User.class))).thenReturn(updatedUser);

        
        User result = userController.updateUser("user123", updatedUser);

        assertNotNull(result);
        assertEquals("updateduser", result.getUsername());
        verify(userService, times(1)).updateUser(eq("user123"), any(User.class));
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userService).deleteUser("user123");

        ResponseEntity<?> response = userController.deleteUser("user123");

        assertEquals(200, response.getStatusCode().value());
        verify(userService, times(1)).deleteUser("user123");
    }

    @Test
    void testCreateCollection() {
        when(userService.createCollection(eq("user123"), any(PokemonCollection.class))).thenReturn(testCollection);

        PokemonCollection createdCollection = userController.createCollection("user123", testCollection);

        assertNotNull(createdCollection);
        assertEquals("Minha Coleção", createdCollection.getName());
        verify(userService, times(1)).createCollection(eq("user123"), any(PokemonCollection.class));
    }

    @Test
    void testGetUserCollections() {
        List<PokemonCollection> collections = new ArrayList<>();
        collections.add(testCollection);
        when(userService.getUserCollections("user123")).thenReturn(collections);

        List<PokemonCollection> userCollections = userController.getUserCollections("user123");

        assertNotNull(userCollections);
        assertEquals(1, userCollections.size());
        assertEquals("Minha Coleção", userCollections.get(0).getName());
        verify(userService, times(1)).getUserCollections("user123");
    }

    @Test
    void testGetUserCollection() {
        when(userService.getUserCollection("user123", "col123")).thenReturn(testCollection);

        PokemonCollection collection = userController.getUserCollection("user123", "col123");

        assertNotNull(collection);
        assertEquals("col123", collection.getId());
        verify(userService, times(1)).getUserCollection("user123", "col123");
    }

    @Test
    void testUpdateCollection() {
        PokemonCollection updatedCollection = new PokemonCollection.Builder()	
                .name(new Name("Coleção Atualizada"))
                .description(new Description("Descrição Atualizada"))
                .createdAt(new Date())
                .build();
        when(userService.updateCollection(eq("user123"), eq("col123"), any(PokemonCollection.class)))
                .thenReturn(updatedCollection);

        PokemonCollection result = userController.updateCollection("user123", "col123", updatedCollection);

        assertNotNull(result);
        assertEquals("Coleção Atualizada", result.getName());
        verify(userService, times(1)).updateCollection(eq("user123"), eq("col123"), any(PokemonCollection.class));
    }

    @Test
    void testDeleteCollection() {
        doNothing().when(userService).deleteCollection("user123", "col123");

        ResponseEntity<?> response = userController.deleteCollection("user123", "col123");

        assertEquals(200, response.getStatusCode().value());
        verify(userService, times(1)).deleteCollection("user123", "col123");
    }

    @Test
    void testAddPokemonToCollection() {
        Map<String, Object> request = new HashMap<>();
        request.put("pokemonId", "poke123");
        request.put("quantity", 2);

        when(userService.addPokemonToCollection("user123", "col123", "poke123", 2))
                .thenReturn(testItem);

        CollectionItem addedItem = userController.addPokemonToCollection("user123", "col123", request);

        assertNotNull(addedItem);
        assertEquals("poke123", addedItem.getPokemonId());
        assertEquals(2, addedItem.getQuantity());
        verify(userService, times(1)).addPokemonToCollection("user123", "col123", "poke123", 2);
    }

    @Test
    void testAddPokemonToCollectionDefaultQuantity() {
        Map<String, Object> request = new HashMap<>();
        request.put("pokemonId", "poke123");

        when(userService.addPokemonToCollection("user123", "col123", "poke123", 1))
                .thenReturn(new CollectionItem("poke123", 1));

        CollectionItem addedItem = userController.addPokemonToCollection("user123", "col123", request);

        assertNotNull(addedItem);
        assertEquals("poke123", addedItem.getPokemonId());
        assertEquals(1, addedItem.getQuantity());
        verify(userService, times(1)).addPokemonToCollection("user123", "col123", "poke123", 1);
    }

    @Test
    void testRemovePokemonFromCollection() {
        doNothing().when(userService).removePokemonFromCollection("user123", "col123", "poke123");

        ResponseEntity<?> response = userController.removePokemonFromCollection("user123", "col123", "poke123");

        assertEquals(200, response.getStatusCode().value());
        verify(userService, times(1)).removePokemonFromCollection("user123", "col123", "poke123");
    }

    @Test
    void testGetPokemonsInCollection() {
        List<Pokemon> pokemons = Arrays.asList(testPokemon);
        when(userService.getPokemonsInCollection("user123", "col123")).thenReturn(pokemons);

        List<Pokemon> result = userController.getPokemonsInCollection("user123", "col123");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Bulbasaur", result.get(0).getName());
        verify(userService, times(1)).getPokemonsInCollection("user123", "col123");
    }
}