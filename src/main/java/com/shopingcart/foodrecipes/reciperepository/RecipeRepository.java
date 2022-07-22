package com.shopingcart.foodrecipes.reciperepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shopingcart.foodrecipes.recipeenity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe,Integer> {
	
	Recipe findByRecipeName(String recipeName);
	List<Recipe> findByDishType(String dishType);
	@Query("SELECT re FROM Recipe re WHERE re.dishType= ?1 and re.servings= ?2")
	List<Recipe> findByDisTypeAndServings(String disType, Integer servings);

}
