package com.shopingcart.foodrecipes.recipeenity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RECIPE")
public class Recipe {
	
	public Recipe() {
		
	}
	
	public Recipe(String recName,String dType, List<String> disIntegre,Integer serv) {
		this.recipeName=recName;
		this.dishType=dType;
		this.dishIngredients=disIntegre;
		this.servings=serv;
	}
	@Id
    @GeneratedValue
	private int id;
	private String recipeName;
	private String dishType;
	@ElementCollection
	private List<String> dishIngredients;
	private int servings;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRecipeName() {
		return recipeName;
	}
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}
	public String getDishType() {
		return dishType;
	}
	public void setDishType(String dishType) {
		this.dishType = dishType;
	}
	public List<String> getDishIngredients() {
		return dishIngredients;
	}
	public void setDishIngredients(List<String> dishIngredients) {
		this.dishIngredients = dishIngredients;
	}
	public int getServings() {
		return servings;
	}
	public void setServings(int servings) {
		this.servings = servings;
	}
	
	

}
