package com.shopingcart.foodrecipes;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopingcart.foodrecipes.model.Response;
import com.shopingcart.foodrecipes.recipecontroller.RecipeManagementController;
import com.shopingcart.foodrecipes.recipeenity.Recipe;
import com.shopingcart.foodrecipes.recipeservice.RecipeService;

import org.junit.jupiter.api.Test;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = RecipeManagementController.class)
class RecipeManagementControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;
    
    //Course mockCourse = new Course("Course1", "Spring", "10 Steps", Arrays.asList("Learn Maven", "Import Project", "First Example", "Second Example"));
    Recipe recipe = new Recipe("Lemon Chicken","Non_Veg",Arrays.asList("Lemon","Herbs","Pepper","Salt"),4);
    String exampleCourseJson = "{\"name\":\"Spring\",\"description\":\"10 Steps\",\"steps\":[\"Learn Maven\",\"Import Project\",\"First Example\",\"Second Example\"]}";

    @Test
    public void retrieveRecipeDetail() throws Exception {

        
        Mockito.when(
        		recipeService.getRecipeByName(Mockito.anyString()
                        )).thenReturn(recipe);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "recipe/{name}").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"recipeName\":\"Lemon Chicken\",\"dishType\":\"Non_Veg\",\"dishIngredients\":[\"Lemon\",\"Herbs\",\"Pepper\",\"Salt\"],\"servings\":4}";

        // {"id":"Course1","name":"Spring","description":"10 Steps, 25 Examples and 10K Students","steps":["Learn Maven","Import Project","First Example","Second Example"]}

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }
    

	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void contextLoads() {
	}
 
	@Autowired
	private WebApplicationContext context;

	ObjectMapper om = new ObjectMapper();

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void addRecipeTest() throws Exception {
		Recipe recipe = new Recipe();
		
		recipe.setRecipeName("Lemon Chiken");
		recipe.setDishType("Non_Veg");
		recipe.setServings(4);
		recipe.setDishIngredients(List.of("Garlic","Herbs","Lemon"));
		
		String jsonRequest = om.writeValueAsString(recipe);
		MvcResult result = mockMvc.perform(post("/addRecipe").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
		String resultContent = result.getResponse().getContentAsString();
		Response response = om.readValue(resultContent, Response.class);
		Assert.assertTrue(response.isStatus() == Boolean.TRUE);

	}
	
	@Test
	public void addRecipesTest() throws Exception {
		
		
		Recipe recipe = new Recipe();
		Recipe[] recipes= new Recipe[2];
		
		recipe.setRecipeName("Lemon Chiken");
		recipe.setDishType("Non_Veg");
		recipe.setServings(4);
		recipe.setDishIngredients(List.of("Garlic","Herbs","Lemon"));
		
		Recipe recipe1 = new Recipe();
		
		recipe1.setRecipeName("Pepper Chiken");
		recipe1.setDishType("Non_Veg");
		recipe1.setServings(4);
		recipe1.setDishIngredients(List.of("Garlic","Herbs","Pepper"));
		recipes[0]=recipe;
		recipes[1]=recipe1;
		
		
		String jsonRequest = om.writeValueAsString(recipes);
		MvcResult result = mockMvc.perform(post("/addRecipes").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
		String resultContent = result.getResponse().getContentAsString();
		Response response = om.readValue(resultContent, Response.class);
		
		Assert.assertTrue(response.isStatus() == Boolean.TRUE);
		Assert.assertTrue(response.getMessage().startsWith(String.valueOf(recipes.length)));

	}


}
