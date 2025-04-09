package com.ptcg.pokemon_api.model.valueobject;

import java.util.Objects;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ptcg.pokemon_api.exception.InvalidNameException;

public final class Name {
    private static final String NAME_REGEX = "^[A-Za-z0-9 .:'-]+$";
    private static final Pattern PATTERN = Pattern.compile(NAME_REGEX);

    private final String name;

    @JsonCreator
    public Name(String name) {
       validateName(name);
        this.name = name;
    }

    public static void validateName(String name) {
        if (!isValidName(name)) {
            throw new InvalidNameException("Invalid Pokémon name: " + name);
        }
    }

    private static boolean isValidName(String name) {
        return PATTERN.matcher(name).matches();
    }

    @JsonValue
    public String toJson() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isSame(Name other) {
        return name.equalsIgnoreCase(other.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Name)) return false;
        Name other = (Name) o;
        return name.equalsIgnoreCase(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }
}
