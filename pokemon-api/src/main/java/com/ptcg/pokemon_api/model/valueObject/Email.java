package com.ptcg.pokemon_api.model.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ptcg.pokemon_api.exception.InvalidEmailException;

public class Email {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

    private final String email;

    public Email(String email) {
        validateEmail(email);
        this.email = email;
    }

    private static void validateEmail(String email) {
        isEmailNotNull(email);
        isValidEmail(email);
    }

    private static void isValidEmail(String email) {
        if(!email.matches(EMAIL_REGEX)){
            throw new InvalidEmailException(email);
        }
    }

    private static void isEmailNotNull(String email) {
        if(email == null || email.isBlank()) {
            throw new InvalidEmailException();
        }
    }

    public String getemail() {
        return email;
    }

    @JsonCreator
    public static Email of(@JsonProperty("email") String email) {
        return new Email(email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email)) return false;
        Email other = (Email) o;
        return email.equalsIgnoreCase(other.email);
    }

    @Override
    public int hashCode() {
        return email.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return email;
    }
}

