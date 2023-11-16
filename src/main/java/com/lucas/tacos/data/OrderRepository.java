package com.lucas.tacos.data;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.lucas.tacos.TacoOrder;
import com.lucas.tacos.User;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

	public List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

	
	
}