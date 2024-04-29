package org.generation.italy.introjdbc.model;

public class Category {
    private int id;
    private String name;
    private String description;

    public Category(int id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }
}
