package com.lucas.tacos;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Taco {
	@NotNull @NotBlank()
	private String name;
	@NotNull
	@Size(min=1, message="You must choose at least 1 ingredient")
	private List<Ingredient> ingredients;
}