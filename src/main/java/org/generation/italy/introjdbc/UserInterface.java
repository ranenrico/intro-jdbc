package org.generation.italy.introjdbc;

import org.generation.italy.introjdbc.model.repositories.CategoryRepository;

public class UserInterface {
    private CategoryRepository catRepo;

    
    public UserInterface(CategoryRepository catRepo) {
        this.catRepo = catRepo;
    }


    public void start(){
        var cats = catRepo.getAll();
    }
}
