
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
import java.util.Optional;
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
    
    // User methods
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ResourceAlreadyExistsException("Username already exists: " + user.getUsername());
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already exists: " + user.getEmail());
        }
        
        // Encriptar a senha antes de salvar
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
        
        // Se uma nova senha for fornecida, encriptar antes de atualizar
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        
        return userRepository.save(user);
    }
    
    public void deleteUser(String id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
    
    // Método para verificar senha - útil para autenticação
    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    
    // Resto dos métodos permanecem inalterados...
    
    // Collection methods
    public PokemonCollection createCollection(String userId, PokemonCollection collection) {
        User user = getUserById(userId);
        
        // Generate ID for the collection
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
        
        existingCollection.setName(collectionDetails.getName());
        existingCollection.setDescription(collectionDetails.getDescription());
        
        userRepository.save(user);
        return existingCollection;
    }
    
    public void deleteCollection(String userId, String collectionId) {
        User user = getUserById(userId);
        user.removeCollection(collectionId);
        userRepository.save(user);
    }
    
    // Collection item methods
    public CollectionItem addPokemonToCollection(String userId, String collectionId, String pokemonId, int quantity) {
        // Verify pokemon exists
        Pokemon pokemon = pokemonService.getPokemonById(pokemonId);
        if (pokemon == null) {
            throw new ResourceNotFoundException("Pokemon not found with id: " + pokemonId);
        }
        
        User user = getUserById(userId);
        PokemonCollection collection = getUserCollection(userId, collectionId);
        
        // Check if pokemon already exists in collection
        Optional<CollectionItem> existingItem = collection.getItems().stream()
                .filter(item -> item.getPokemonId().equals(pokemonId))
                .findFirst();
        
        if (existingItem.isPresent()) {
            // Update quantity
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            // Add new item
            CollectionItem newItem = new CollectionItem(pokemonId, quantity);
            collection.addItem(newItem);
        }
        
        userRepository.save(user);
        return existingItem.orElse(new CollectionItem(pokemonId, quantity));
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
