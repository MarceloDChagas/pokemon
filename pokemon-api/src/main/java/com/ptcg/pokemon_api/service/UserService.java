
package com.ptcg.pokemon_api.service;

import com.ptcg.pokemon_api.exception.ResourceAlreadyExistsException;
import com.ptcg.pokemon_api.exception.ResourceNotFoundException;
import com.ptcg.pokemon_api.model.Pokemon;
import com.ptcg.pokemon_api.model.User;
import com.ptcg.pokemon_api.model.valueobject.CollectionItem;
import com.ptcg.pokemon_api.model.valueobject.PokemonCollection;
import com.ptcg.pokemon_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PokemonService pokemonService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ResourceAlreadyExistsException("Username already exists: " + user.getUsername());
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already exists: " + user.getEmail());
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        return userRepository.save(user);
    }
    
    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
    
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with username: " + username);
        }
        return user;
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User updateUser(String id, User userDetails) {
        User user = getUserById(id);
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        
        return userRepository.save(user);
    }
    
    public void deleteUser(String id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
    
    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    
    
    public PokemonCollection createCollection(String userId, PokemonCollection collection) {
        User user = getUserById(userId);
        
        collection.setId(UUID.randomUUID().toString());
        
        user.addCollection(collection);
        userRepository.save(user);
        return collection;
    }
    
    public List<PokemonCollection> getUserCollections(String userId) {
        User user = getUserById(userId);
        return user.getCollections();
    }
    
    public PokemonCollection getUserCollection(String userId, String collectionId) {
        User user = getUserById(userId);
        return user.getCollections().stream()
                .filter(c -> c.getId().equals(collectionId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Collection not found with id: " + collectionId));
    }
    
    public PokemonCollection updateCollection(String userId, String collectionId, PokemonCollection collectionDetails) {
        User user = getUserById(userId);
        PokemonCollection existingCollection = getUserCollection(userId, collectionId);
    
        PokemonCollection.Builder builder = new PokemonCollection.Builder()
                .id(existingCollection.getId()) 
                .name(collectionDetails.getName() != null ? collectionDetails.getName() : existingCollection.getName())
                .description(collectionDetails.getDescription() != null ? collectionDetails.getDescription() : existingCollection.getDescription())
                .createdAt(existingCollection.getCreatedAt());
    
        PokemonCollection updatedCollection = existingCollection.updatePokemonCollection(builder);
    
        user.updateCollection(updatedCollection);
    
        userRepository.save(user);
    
        return updatedCollection;
    }
    
    
    public void deleteCollection(String userId, String collectionId) {
        User user = getUserById(userId);
        user.removeCollection(collectionId);
        userRepository.save(user);
    }
    
    public CollectionItem addPokemonToCollection(String userId, String collectionId, String pokemonId, int quantity) {
        // Verifica existência do Pokémon
        Pokemon pokemon = pokemonService.getPokemonById(pokemonId);
        if (pokemon == null) {
            throw new ResourceNotFoundException("Pokemon not found with id: " + pokemonId);
        }
    
        User user = getUserById(userId);
        PokemonCollection collection = getUserCollection(userId, collectionId);
    
        // Cria novo item (pode ser novo ou atualização)
        CollectionItem newItem = CollectionItem.create(pokemonId, quantity);
    
        // Atualiza a coleção com o novo item
        PokemonCollection updatedCollection = collection.withAddedItem(newItem);
    
        // Atualiza a lista de coleções do usuário
        user.updateCollection(updatedCollection); // já vimos como implementar esse método
    
        // Persiste as alterações
        userRepository.save(user);
    
        // Retorna o item final (com quantidade atualizada)
        return updatedCollection.getItems().stream()
                .filter(item -> item.getPokemonId().equals(pokemonId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unexpected error: item not found after update"));
    }
    
    
    public void removePokemonFromCollection(String userId, String collectionId, String pokemonId) {
        User user = getUserById(userId);
        PokemonCollection collection = getUserCollection(userId, collectionId);
        collection.removeItem(pokemonId);
        userRepository.save(user);
    }
    
    public List<Pokemon> getPokemonsInCollection(String userId, String collectionId) {
        PokemonCollection collection = getUserCollection(userId, collectionId);
        return collection.getItems().stream()
                .map(item -> pokemonService.getPokemonById(item.getPokemonId()))
                .collect(Collectors.toList());
    }
}
