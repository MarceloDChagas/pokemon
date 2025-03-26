package com.ptcg.pokemon_api.model.valueObject;

public class Description {
    private String description;

    public Description(String description) {
        if(isValidDescription(description)) {
           this.description = "GENERIC DESCRIPTION";
        }
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isValidDescription(String description) {
        return description != null && !description.trim().isEmpty();
    }

    @Override
public String toString() {
        return description;
    }
}
