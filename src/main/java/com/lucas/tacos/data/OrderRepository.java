package com.lucas.tacos.data;

import org.springframework.data.repository.CrudRepository;

import com.lucas.tacos.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
	
}