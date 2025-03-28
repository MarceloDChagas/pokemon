package com.ptcg.pokemon_api.model.valueObject;

import com.ptcg.pokemon_api.exception.InvalidDescriptionException;

public class Description {
    private String description;

    public Description(String description) {
        if (!isValidDescription(description)) {
            throw new InvalidDescriptionException(description);
        }
        this.description = description.trim(); 
    }

    public String getDescription() {
        return description;
    }

    public boolean isValidDescription(String description) {
        return description != null && !description.trim().isEmpty();
    }

    @Override
    public String toString() {
        return description;
    }
}
