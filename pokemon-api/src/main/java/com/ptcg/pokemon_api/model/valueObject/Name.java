package com.ptcg.pokemon_api.model.valueObject;

import java.util.regex.Pattern;

import com.ptcg.pokemon_api.exception.InvalidNameException;

public class Name {
    private static final String NAME_REGEX = "^[A-Za-z0-9 .:'-]+$";
    private static final Pattern PATTERN = Pattern.compile(NAME_REGEX);

    private String name;

    public Name(String name) {
        if (!isValidName(name)) {
            throw new InvalidNameException("Invalid Pokémon name: " + name);
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private boolean isValidName(String name) {
        return PATTERN.matcher(name).matches();
    }

    @Override
    public String toString() {
        return name;
    }
}