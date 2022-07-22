package com.shopingcart.foodrecipes.error;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ErrorResponse {
	
	@Schema(example = "Error message")
    private String message;
    @Schema(example = "[\"recipeType\",\"recipeId\"]")
    private List<String> invalidFields;

}
