package com.ptcg.pokemon_api.model.valueobject;

import java.util.Objects;

import com.ptcg.pokemon_api.exception.InvalidQuantityException;

public class Quantity {
    private int quantity;

    public Quantity(int quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    private static void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new InvalidQuantityException(quantity);
        }
    }

    public int toInt() {
        return quantity;
    }
    public Quantity increment() {
        return new Quantity(this.quantity + 1);
    }
    
    public Quantity decrement() {
        return new Quantity(this.quantity - 1);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantity quantity1 = (Quantity) o;
        return quantity == quantity1.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }

    @Override
    public String toString() {
        return String.valueOf(quantity);
    }
}
