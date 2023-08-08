package com.lucas.tacos.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lucas.tacos.Ingredient;
import com.lucas.tacos.Taco;
import com.lucas.tacos.TacoOrder;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
	@ModelAttribute
	public void addIngredientsToModel(Model model) {
		List<Ingredient> ingredients = Arrays.asList(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
				new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
				new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
				new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
				new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
				new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
				new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
				new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
				new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
				new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
		Ingredient.Type[] types = Ingredient.Type.values();
		for (Ingredient.Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}
	}

	@ModelAttribute(name = "tacoOrder")
	public TacoOrder order() {
		return new TacoOrder();
	}

	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}

	@GetMapping
	public String showDesignForm() {
		return "design";
	}

	private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
		return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
	}

	@PostMapping
	public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {
		if (errors.hasErrors()) {
			log.info(errors.toString());
			return "design";
		}
		tacoOrder.addTaco(taco);
		log.info("Processing taco: {}", taco);
		return "redirect:/orders/current";
	}
}