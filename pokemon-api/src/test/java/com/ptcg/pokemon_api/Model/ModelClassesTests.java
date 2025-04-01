package com.ptcg.pokemon_api.model;

import org.junit.jupiter.api.Test;

import com.ptcg.pokemon_api.model.valueObject.CollectionItem;
import com.ptcg.pokemon_api.model.valueObject.PokemonCollection;

import static org.junit.jupiter.api.Assertions.*;

public class ModelClassesTests {

    @Test
    void testUserCreation() {
        User user = new User("mario", "senha123", "mario@example.com");
        
        assertEquals("mario", user.getUsername());
        assertEquals("senha123", user.getPassword());
        assertEquals("mario@example.com", user.getEmail());
        assertTrue(user.getCollections().isEmpty());
    }
    
    @Test
    void testUserAddCollection() {
        
        User user = new User("mario", "senha123", "mario@example.com");
        PokemonCollection collection = new PokemonCollection("Meus Favoritos", "Minha coleção favorita");
        collection.setId("col1");
        
        user.addCollection(collection);
        
        assertEquals(1, user.getCollections().size());
        assertEquals("Meus Favoritos", user.getCollections().get(0).getName());
    }
    
    @Test
    void testUserRemoveCollection() {
        User user = new User("mario", "senha123", "mario@example.com");
        PokemonCollection collection = new PokemonCollection("Meus Favoritos", "Minha coleção favorita");
        collection.setId("col1");
        user.addCollection(collection);
        
        user.removeCollection("col1");
        
        assertTrue(user.getCollections().isEmpty());
    }
    
    @Test
    void testPokemonCollectionCreation() {
        PokemonCollection collection = new PokemonCollection("Raros", "Pokémons raros");
        
        assertEquals("Raros", collection.getName());
        assertEquals("Pokémons raros", collection.getDescription());
        assertNotNull(collection.getCreatedAt());
        assertTrue(collection.getItems().isEmpty());
    }
    
    @Test
    void testPokemonCollectionAddItem() {
        PokemonCollection collection = new PokemonCollection("Raros", "Pokémons raros");
        CollectionItem item = new CollectionItem("poke1", 3);
        
        collection.addItem(item);
        
        assertEquals(1, collection.getItems().size());
        assertEquals("poke1", collection.getItems().get(0).getPokemonId());
        assertEquals(3, collection.getItems().get(0).getQuantity());
    }
    
    @Test
    void testPokemonCollectionRemoveItem() {
        PokemonCollection collection = new PokemonCollection("Raros", "Pokémons raros");
        CollectionItem item = new CollectionItem("poke1", 3);
        collection.addItem(item);
        
        collection.removeItem("poke1");
        
        assertTrue(collection.getItems().isEmpty());
    }
    
    @Test
    void testCollectionItemCreation() {
        CollectionItem item = new CollectionItem("poke1", 5);
        
        assertEquals("poke1", item.getPokemonId());
        assertEquals(5, item.getQuantity());
        assertNotNull(item.getAddedAt());
    }
    
    @Test
    void testCollectionItemDefaultQuantity() {
        CollectionItem item = new CollectionItem("poke1");
        
        assertEquals(1, item.getQuantity());
    }
    
    @Test
    void testCollectionItemIncrementQuantity() {
        CollectionItem item = new CollectionItem("poke1", 2);
        
        item.incrementQuantity();
        
        assertEquals(3, item.getQuantity());
    }
    
    @Test
    void testCollectionItemDecrementQuantity() {
        CollectionItem item = new CollectionItem("poke1", 3);
        
        item.decrementQuantity();
        
        assertEquals(2, item.getQuantity());
    }
    
    @Test
    void testCollectionItemDecrementQuantityWithZero() {
        CollectionItem item = new CollectionItem("poke1", 0);
        
        item.decrementQuantity();
        
        assertEquals(0, item.getQuantity(), "Quantity should not go below zero");
    }
}