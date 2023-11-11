package com.lucas.tacos;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
@Data
@Entity			
public class Ingredient {
	@Id
	private final String id;
	private final String name;
	@Enumerated(EnumType.STRING)
	private final Type type;

	public enum Type {
		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
	}
}
