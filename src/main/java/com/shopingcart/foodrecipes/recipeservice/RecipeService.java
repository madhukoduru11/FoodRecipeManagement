package com.shopingcart.foodrecipes.recipeservice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopingcart.foodrecipes.recipeenity.Recipe;
import com.shopingcart.foodrecipes.reciperepository.RecipeRepository;

@Service
public class RecipeService {
	
	@Autowired
    private RecipeRepository recipeRepository;

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public List<Recipe> saveRecipes(List<Recipe> recipes) {
        return recipeRepository.saveAll(recipes);
    }

    public List<Recipe> getRecipes() {
        return recipeRepository.findAll();
    }

    public List<Recipe> getRecipeByType(String recipeType) {
        return recipeRepository.findByDishType(recipeType);
    }

    public Recipe getRecipeByName(String name) {
        return recipeRepository.findByRecipeName(name);
    }

    public String deleteRecipe(int id) {
    	recipeRepository.deleteById(id);
        return "product removed !! " + id;
    }

    public Recipe updateRecipe(Recipe recipe) {
    	Recipe existingRecipe = recipeRepository.findById(recipe.getId()).orElse(null);
    	
    	existingRecipe.setRecipeName(recipe.getRecipeName());
    	existingRecipe.setDishType(recipe.getDishType());
    	existingRecipe.setServings(recipe.getServings());
    	existingRecipe.setDishIngredients(recipe.getDishIngredients());

        return recipeRepository.save(existingRecipe);
    }
    
    public List<Recipe> getRecipesMultipleSelection(String disType, Integer servings, String inclRecipes, String exclRecipes) {
    	
    	List<Recipe> recipes=recipeRepository.findByDisTypeAndServings(disType, servings);
    	
    	List<Recipe> inclueRecipes= recipes.stream().filter(recipe -> recipe.getDishIngredients()
    			.contains(inclRecipes)).collect(Collectors.toList());
    	
    	List<Recipe> excludeRecipes = inclueRecipes.stream().filter(recipe -> !recipe.getDishIngredients()
    			.contains(exclRecipes)).collect(Collectors.toList());
        return excludeRecipes;
    }


}
