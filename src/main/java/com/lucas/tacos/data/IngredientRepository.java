package com.lucas.tacos.data;

import org.springframework.data.repository.CrudRepository;

import com.lucas.tacos.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
