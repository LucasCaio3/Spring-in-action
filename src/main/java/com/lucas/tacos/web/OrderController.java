package com.lucas.tacos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.lucas.tacos.TacoOrder;
import com.lucas.tacos.User;
import com.lucas.tacos.data.OrderRepository;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
	@Autowired
	OrderRepository orderRepository;

	@GetMapping("/current")
	public String orderForm() {
		return "orderForm";
	}

	@PostMapping
	public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus) {
		if (errors.hasErrors()) {
			return "orderForm";
		}
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		order.setUser((User)authentication.getPrincipal());
		orderRepository.save(order);
		log.info("Order submitted: {}", order);
		sessionStatus.setComplete();
		return "redirect:/home";
	}
}
