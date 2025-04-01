package com.ptcg.pokemon_api.controller;

import com.ptcg.pokemon_api.model.valueObject.CollectionItem;
import com.ptcg.pokemon_api.model.Pokemon;
import com.ptcg.pokemon_api.model.valueObject.PokemonCollection;
import com.ptcg.pokemon_api.model.User;
import com.ptcg.pokemon_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    // User endpoints
    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }
    
    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }
    
    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }
    
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @Valid @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/{userId}/collections")
    public PokemonCollection createCollection(@PathVariable String userId, @Valid @RequestBody PokemonCollection collection) {
        return userService.createCollection(userId, collection);
    }
    
    @GetMapping("/{userId}/collections")
    public List<PokemonCollection> getUserCollections(@PathVariable String userId) {
        return userService.getUserCollections(userId);
    }
    
    @GetMapping("/{userId}/collections/{collectionId}")
    public PokemonCollection getUserCollection(@PathVariable String userId, @PathVariable String collectionId) {
        return userService.getUserCollection(userId, collectionId);
    }
    
    @PutMapping("/{userId}/collections/{collectionId}")
    public PokemonCollection updateCollection(@PathVariable String userId, @PathVariable String collectionId, 
                                             @Valid @RequestBody PokemonCollection collectionDetails) {
        return userService.updateCollection(userId, collectionId, collectionDetails);
    }
    
    @DeleteMapping("/{userId}/collections/{collectionId}")
    public ResponseEntity<?> deleteCollection(@PathVariable String userId, @PathVariable String collectionId) {
        userService.deleteCollection(userId, collectionId);
        return ResponseEntity.ok().build();
    }
    
    // Collection item endpoints
    @PostMapping("/{userId}/collections/{collectionId}/pokemons")
    public CollectionItem addPokemonToCollection(@PathVariable String userId, @PathVariable String collectionId,
                                                @RequestBody Map<String, Object> request) {
        String pokemonId = (String) request.get("pokemonId");
        int quantity = request.get("quantity") != null ? Integer.parseInt(request.get("quantity").toString()) : 1;
        return userService.addPokemonToCollection(userId, collectionId, pokemonId, quantity);
    }
    
    @DeleteMapping("/{userId}/collections/{collectionId}/pokemons/{pokemonId}")
    public ResponseEntity<?> removePokemonFromCollection(@PathVariable String userId, @PathVariable String collectionId,
                                                        @PathVariable String pokemonId) {
        userService.removePokemonFromCollection(userId, collectionId, pokemonId);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{userId}/collections/{collectionId}/pokemons")
    public List<Pokemon> getPokemonsInCollection(@PathVariable String userId, @PathVariable String collectionId) {
        return userService.getPokemonsInCollection(userId, collectionId);
    }
}
