package com.shopingcart.foodrecipes.recipecontroller;

//import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.shopingcart.foodrecipes.error.ErrorResponse;
import com.shopingcart.foodrecipes.model.Response;
import com.shopingcart.foodrecipes.recipeenity.Recipe;
import com.shopingcart.foodrecipes.recipeservice.RecipeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
@RestController
public class RecipeManagementController {
	
	@Autowired
	RecipeService recipeService;
	
	@Operation(summary = "API for adding single Recipe", description = "This API adds the  Recipes.", tags = {"Recipe Adding"})

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Response for successful adding recipe in application",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    })
    })
	@PostMapping("/addRecipe")
    public Response addRecipe(@RequestBody Recipe recipe) {
		recipeService.saveRecipe(recipe);
		Response response = new Response();
		response.setMessage(recipe.getRecipeName() + "Added successfully in Application");
		response.setStatus(Boolean.TRUE);
        return response;
    }
	
	
	@Operation(summary = "API for adding multiple Recipe", description = "This API adds the different Recipes.", tags = {"Recipe Adding"})

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Response for successful adding different recipes in application",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    })
    })
    @PostMapping("/addRecipes")
    public Response addRecipes(@RequestBody List<Recipe> recipes) {
		recipeService.saveRecipes(recipes);
		Response response = new Response();
		Long addedRecipeCount = recipes.stream().count();
		response.setMessage(addedRecipeCount+ "Recipes are added successfully in Application");
		response.setStatus(Boolean.TRUE);
        return response;
        
    }
    
	@Operation(summary = "API for retrieving all Recipes", description = "This API Retrieves all Recipes from application .", tags = {"Recipe Access"})

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Response for successful retriving all recipes from the system",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    })
    })
    @GetMapping("/recipes")
    public List<Recipe> findAllRecipes() {
        return recipeService.getRecipes();
    }    
    
    @Operation(summary = "API for retrieving Recipe", description = "This API retrieves either Vegetarian or Non Vegetarian Recipe .", tags = {"Recipe Access"})

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Response for successful recipe retriving with given search",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Resource Not Found",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
            
    })
    
    @GetMapping("/recipeByType/{type}")
    public List<Recipe> findRecipeByType(@PathVariable String type) {
    	List<Recipe> recipes=recipeService.getRecipeByType(type);
    	if (recipes == null || recipes.size()<= 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return recipeService.getRecipeByType(type);
    }
    
    @Operation(summary = "API for retrieving Recipe using Recipe name", description = "This API Retrives Recipe by using given name .", tags = {"Recipe Access"})

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Response for successful recipe retrieving with given search",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Resource Not Found",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
            
    })

    @GetMapping("recipe/{name}")
    public Recipe findRecipeByName(@PathVariable String name) {
    	
    	Recipe recipe=recipeService.getRecipeByName(name);
    	
    	if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return recipe;
        
    }
    
    @Operation(summary = "API for Updating Recipe", description = "This API updates the Recipe fields .", tags = {"Recipe Update"})

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Response for successful updating recipe details in application",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Resource Not Found",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
            
    })

    @PutMapping("/update")
    public Recipe updateRecipe(@RequestBody Recipe recipe) {
        return recipeService.updateRecipe(recipe);
    }
    
    @Operation(summary = "API for Deleting Recipe", description = "This API deletes the Recipe from application by using given id .", tags = {"Recipe Delete"})

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Response for successfully deleting Recipe",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Resource Not Found",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
            
    })
    @DeleteMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable int id) {
        return recipeService.deleteRecipe(id);
    }
    @Operation(summary = "API for retrieving Recipe using search criteria", description = "This API retrives the Recipe from application by using given search criteria .", tags = {"Recipe Access"})

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Response for successfully retrieving Recipe",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Resource Not Found",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
            
    })

    @GetMapping("/recipeBySelection")
    public List<Recipe> findRecipeByMultipleSelection(@RequestParam(value = "recipeType") String recipeType,
    		  @RequestParam(value = "servings") Integer servings,
    	      @RequestParam(value = "inlcudeIngre") String inlcudeIngre, 
    	      @RequestParam(value = "exclcudeIngre") String exclcudeIngre) 
    		  {
    	
    	List<Recipe> recipes=recipeService.getRecipesMultipleSelection(recipeType, servings, inlcudeIngre, exclcudeIngre);
    	if (recipes == null || recipes.size()<= 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return recipes;
    }
    
   /* @GetMapping("/recipeByCriteria")
    public Object findRecipeByCriteria(@RequestBody Object searchValue) {
    	
    	@SuppressWarnings("unchecked")
		LinkedHashMap<String,Object> val= (LinkedHashMap<String,Object>)searchValue;
    	
    	String inputValue = " This is the input value"+val.get("recipeName")+ "     "+val.get("dishIngredients");
    	
        return inputValue;
    }*/

    

}
