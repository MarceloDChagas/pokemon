package com.ptcg.pokemon_api.model.valueobject;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PokemonCollection {

    @Id
    private String id;

    @Field("name")
    private final Name name;

    @Field("description")
    private final Description description;
    
    @Field("createdAt")
    private final Date createdAt;
   
    @Field("items")
    private CollectionItems items;

    private PokemonCollection(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.createdAt = builder.createdAt;
    }

    public static class Builder{
        private String id;
        private Name name;
        private Description description;
        private Date createdAt;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(Name name) {
            this.name = name;
            return this;
        }

        public Builder description(Description description) {
            this.description = description;
            return this;
        }

        public Builder createdAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PokemonCollection build() {
            return new PokemonCollection(this);
        }
    }

    public String getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public Date getCreatedAt() {
        return new Date(createdAt.getTime());
    }

    public List<CollectionItem> getItems() {
        return items.asList();
    }
    
    public void addItem(CollectionItem item) {
        this.items.addItem(item);
    }
    
    public void removeItem(String pokemonId) {
        this.items.removeItem(pokemonId);
    }
    public void setId(String id) {
        this.id = id;
    }

    public PokemonCollection withAddedItem(CollectionItem newItem) {
    List<CollectionItem> updatedItems = new ArrayList<>(this.items.asList());

    boolean found = false;
    for (int i = 0; i < updatedItems.size(); i++) {
        CollectionItem current = updatedItems.get(i);
        if (current.getPokemonId().equals(newItem.getPokemonId())) {
            CollectionItem updated = current.withUpdatedQuantity(current.getQuantity().toInt() + newItem.getQuantity().toInt());
            updatedItems.set(i, updated);
            found = true;
            break;
        }
    }

    if (!found) {
        updatedItems.add(newItem);
    }

    return new PokemonCollection.Builder()
            .id(this.id)
            .name(this.name)
            .description(this.description)
            .createdAt(this.createdAt)
            .build()
            .withItems(updatedItems);
    }

    public PokemonCollection withItems(List<CollectionItem> newItems) {
        PokemonCollection updated = new PokemonCollection.Builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .createdAt(this.createdAt)
                .build();
        updated.items = new CollectionItems(newItems); 
        return updated;
    }

    public PokemonCollection updatePokemonCollection(Builder builder) {
        return new PokemonCollection.Builder()
                .id(builder.id != null ? builder.id : this.id)
                .name(builder.name != null ? builder.name : this.name)
                .description(builder.description != null ? builder.description : this.description)
                .createdAt(this.createdAt)
                .build();
    }

}

