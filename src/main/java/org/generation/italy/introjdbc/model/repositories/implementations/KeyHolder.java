package org.generation.italy.introjdbc.model.repositories.implementations;

public class KeyHolder {
    private Number key;

    public KeyHolder(Number key) {
        this.key = key;
    }

    public KeyHolder() {
    }

    public Number getKey() {
        return key;
    }

    public void setKey(Number key) {
        this.key = key;
    }
    
}
