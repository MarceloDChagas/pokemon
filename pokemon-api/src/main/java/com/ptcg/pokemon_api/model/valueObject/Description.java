package com.ptcg.pokemon_api.model.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ptcg.pokemon_api.exception.InvalidDescriptionException;

import java.util.Objects;

public class Description {
    private final String description;

    @JsonCreator
    public Description(String description) {
        validateDescription(description);
        this.description = description.trim();
    }

    private static void validateDescription(String description) {
        if (!isValidDescription(description)) {
            throw new InvalidDescriptionException(description);
        }
    }

    private static boolean isValidDescription(String description) {
        return description != null && !description.trim().isEmpty();
    }

    @JsonValue
    public String toJson() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Description)) return false;
        Description that = (Description) o;
        return description.equalsIgnoreCase(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description.toLowerCase());
    }

    public boolean isSame(Description other) {
        return description.equalsIgnoreCase(other.description);
    }

    @Override
    public String toString() {
        return description;
    }
}
