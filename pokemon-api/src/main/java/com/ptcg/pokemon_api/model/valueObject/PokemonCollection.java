package com.ptcg.pokemon_api.model.valueobject;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
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
}

