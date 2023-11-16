package com.securepass.SecurePass.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class StandardCredPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Boolean symbols;
    private Boolean uppercase;
    private Boolean ambiguousCharacters;
    private Boolean similarCharacters;
    private int length;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getSymbols() {
        return symbols;
    }

    public void setSymbols(Boolean symbols) {
        this.symbols = symbols;
    }

    public Boolean getUppercase() {
        return uppercase;
    }

    public void setUppercase(Boolean uppercase) {
        this.uppercase = uppercase;
    }

    public Boolean getAmbiguousCharacters() {
        return ambiguousCharacters;
    }

    public void setAmbiguousCharacters(Boolean ambiguousCharacters) {
        this.ambiguousCharacters = ambiguousCharacters;
    }

    public Boolean getSimilarCharacters() {
        return similarCharacters;
    }

    public void setSimilarCharacters(Boolean similarCharacters) {
        this.similarCharacters = similarCharacters;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

}
