package com.ptcg.pokemon_api.model.valueobject;

import java.util.*;
import java.util.stream.Collectors;

public class CollectionItems {

    private final List<CollectionItem> items;

    public CollectionItems() {
        this.items = new ArrayList<>();
    }

    public CollectionItems(List<CollectionItem> items) {
        this.items = new ArrayList<>(Objects.requireNonNull(items));
    }

    public void addItem(CollectionItem item) {
        Objects.requireNonNull(item, "Item não pode ser nulo");
        Optional<CollectionItem> existing = findByPokemonId(item.getPokemonId());
        if (existing.isPresent()) {
            existing.get().incrementQuantity(); 
        } else {
            items.add(item);
        }
    }

    public void removeItem(String pokemonId) {
        if (pokemonId == null || pokemonId.isBlank()) return;

        Optional<CollectionItem> existing = findByPokemonId(pokemonId);
        if (existing.isPresent()) {
            CollectionItem item = existing.get();
            if (item.getQuantity().toInt() > 1) {
                item.decrementQuantity(); 
            } else {
                items.remove(item);
            }
        }
    }

    public Optional<CollectionItem> findByPokemonId(String pokemonId) {
        return items.stream()
                .filter(item -> item.getPokemonId().equalsIgnoreCase(pokemonId))
                .findFirst();
    }

    public List<CollectionItem> asList() {
        return Collections.unmodifiableList(items);
    }

    public boolean contains(String pokemonId) {
        return findByPokemonId(pokemonId).isPresent();
    }

    public int totalItems() {
        return items.size();
    }

    public int totalQuantity() {
        return items.stream()
                .mapToInt(item -> item.getQuantity().toInt())
                .sum();
    }

    @Override
    public String toString() {
        return items.stream()
                .map(CollectionItem::toString)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CollectionItems)) return false;
        CollectionItems that = (CollectionItems) o;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }
}
