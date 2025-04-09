package com.ptcg.pokemon_api.model;

import com.ptcg.pokemon_api.exception.ResourceAlreadyExistsException;
import com.ptcg.pokemon_api.exception.ResourceNotFoundException;
import com.ptcg.pokemon_api.model.Enum.CardType;
import com.ptcg.pokemon_api.model.Enum.EvolutionStage;
import com.ptcg.pokemon_api.model.Enum.PokemonRarity;
import com.ptcg.pokemon_api.model.Enum.PokemonType;
import com.ptcg.pokemon_api.model.valueobject.Attack;
import com.ptcg.pokemon_api.model.valueobject.CollectionItem;
import com.ptcg.pokemon_api.model.valueobject.Description;
import com.ptcg.pokemon_api.model.valueobject.Name;
import com.ptcg.pokemon_api.model.valueobject.PokemonCollection;
import com.ptcg.pokemon_api.model.valueobject.PokemonStatus;
import com.ptcg.pokemon_api.repository.UserRepository;
import com.ptcg.pokemon_api.service.PokemonService;
import com.ptcg.pokemon_api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PokemonService pokemonService;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private PokemonCollection testCollection;
    private Pokemon testPokemon;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User("testuser", "password123", "test@example.com");
        testUser.setId("user123");

        testCollection = new PokemonCollection("Minha Coleção", "Minha primeira coleção de Pokémon");
        testCollection.setId("collection123");

        PokemonStatus status = new PokemonStatus(100, Arrays.asList(new Attack("Thunderbolt", 90)));
        testPokemon = new Pokemon.Builder()
                .id("pokemon123")
                .name(new Name("Pikachu"))
                .type(PokemonType.ELECTRIC)
                .description(new Description("A friendly electric-type Pokémon."))
                .cardType(CardType.POKEMON)
                .rarity(PokemonRarity.COMMON)
                .evolutionStage(EvolutionStage.BASIC)
                .pokemonStatus(status)
                .build();
    }

    @Test
    void testCreateUser() {
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User createdUser = userService.createUser(testUser);

        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUsername());
        assertEquals("test@example.com", createdUser.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUserWithExistingUsername() {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> userService.createUser(testUser));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testCreateUserWithExistingEmail() {
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> userService.createUser(testUser));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById("user123")).thenReturn(Optional.of(testUser));

        User foundUser = userService.getUserById("user123");

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById("nonexistent")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById("nonexistent"));
    }

    @Test
    void testGetUserByUsername() {
        when(userRepository.findByUsername("testuser")).thenReturn(testUser);

        User foundUser = userService.getUserByUsername("testuser");

        assertNotNull(foundUser);
        assertEquals("user123", foundUser.getId());
    }

    @Test
    void testGetUserByUsernameNotFound() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserByUsername("nonexistent"));
    }

    @Test
    void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(testUser);
        User user2 = new User("user2", "password456", "user2@example.com");
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        List<User> allUsers = userService.getAllUsers();

        assertNotNull(allUsers);
        assertEquals(2, allUsers.size());
        assertEquals("testuser", allUsers.get(0).getUsername());
    }

    @Test
    void testUpdateUser() {
        User updatedDetails = new User("newtestuser", "newpassword", "newemail@example.com");
        
        when(userRepository.findById("user123")).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updatedUser = userService.updateUser("user123", updatedDetails);

        assertNotNull(updatedUser);
        assertEquals("newtestuser", updatedUser.getUsername());
        assertEquals("newemail@example.com", updatedUser.getEmail());
    }

    @Test
    void testDeleteUser() {
        when(userRepository.findById("user123")).thenReturn(Optional.of(testUser));
        doNothing().when(userRepository).delete(testUser);

        userService.deleteUser("user123");

        verify(userRepository, times(1)).delete(testUser);
    }

    @Test
    void testCreateCollection() {
        when(userRepository.findById("user123")).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        PokemonCollection createdCollection = userService.createCollection("user123", testCollection);

        assertNotNull(createdCollection);
        assertEquals("Minha Coleção", createdCollection.getName());
        assertNotNull(createdCollection.getId());
    }

    @Test
    void testGetUserCollections() {
        testUser.addCollection(testCollection);
        when(userRepository.findById("user123")).thenReturn(Optional.of(testUser));

        List<PokemonCollection> collections = userService.getUserCollections("user123");

        assertNotNull(collections);
        assertEquals(1, collections.size());
        assertEquals("Minha Coleção", collections.get(0).getName());
    }

    @Test
    void testGetUserCollection() {
        testUser.addCollection(testCollection);
        when(userRepository.findById("user123")).thenReturn(Optional.of(testUser));

        PokemonCollection foundCollection = userService.getUserCollection("user123", "collection123");

        assertNotNull(foundCollection);
        assertEquals("Minha Coleção", foundCollection.getName());
    }

    @Test
    void testGetUserCollectionNotFound() {
        when(userRepository.findById("user123")).thenReturn(Optional.of(testUser));

        assertThrows(ResourceNotFoundException.class, 
                     () -> userService.getUserCollection("user123", "nonexistent"));
    }

    @Test
    void testUpdateCollection() {
        testUser.addCollection(testCollection);
        PokemonCollection updatedDetails = new PokemonCollection("Coleção Atualizada", "Descrição atualizada");
        
        when(userRepository.findById("user123")).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        PokemonCollection updatedCollection = userService.updateCollection("user123", "collection123", updatedDetails);

        assertNotNull(updatedCollection);
        assertEquals("Coleção Atualizada", updatedCollection.getName());
        assertEquals("Descrição atualizada", updatedCollection.getDescription());
    }

    @Test
    void testDeleteCollection() {
        testUser.addCollection(testCollection);
        when(userRepository.findById("user123")).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        userService.deleteCollection("user123", "collection123");

        assertTrue(testUser.getCollections().isEmpty());
    }

    @Test
    void testAddPokemonToCollection() {
        testUser.addCollection(testCollection);
        
        when(userRepository.findById("user123")).thenReturn(Optional.of(testUser));
        when(pokemonService.getPokemonById("pokemon123")).thenReturn(testPokemon);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        CollectionItem addedItem = userService.addPokemonToCollection("user123", "collection123", "pokemon123", 2);

        assertNotNull(addedItem);
        assertEquals("pokemon123", addedItem.getPokemonId());
        assertEquals(2, addedItem.getQuantity());
    }

    @Test
    void testAddPokemonToCollectionWithNonExistentPokemon() {
        testUser.addCollection(testCollection);
        
        when(userRepository.findById("user123")).thenReturn(Optional.of(testUser));
        when(pokemonService.getPokemonById("nonexistent")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, 
                     () -> userService.addPokemonToCollection("user123", "collection123", "nonexistent", 1));
    }

    @Test
    void testRemovePokemonFromCollection() {
        testUser.addCollection(testCollection);
        testCollection.addItem(new CollectionItem("pokemon123", 2));
        
        when(userRepository.findById("user123")).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        userService.removePokemonFromCollection("user123", "collection123", "pokemon123");

        assertTrue(testCollection.getItems().isEmpty());
    }

    @Test
    void testGetPokemonsInCollection() {
        testUser.addCollection(testCollection);
        testCollection.addItem(new CollectionItem("pokemon123", 2));
        
        when(userRepository.findById("user123")).thenReturn(Optional.of(testUser));
        when(pokemonService.getPokemonById("pokemon123")).thenReturn(testPokemon);

        List<Pokemon> pokemons = userService.getPokemonsInCollection("user123", "collection123");

        assertNotNull(pokemons);
        assertEquals(1, pokemons.size());
        assertEquals("Pikachu", pokemons.get(0).getName());
    }
}