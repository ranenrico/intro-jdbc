package org.generation.italy.introjdbc.model.repositories.implementations;

public class KeyHolder {

    private Number key;

    public KeyHolder() {
    }

    public KeyHolder(Number key) {
        this.key = key;
    }

    public Number getKey() {
        return key;
    }

    public void setKey(Number key) {
        this.key = key;
    }
}
