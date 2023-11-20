package com.lucas.tacos.web;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.lucas.tacos.TacoOrder;
import com.lucas.tacos.User;
import com.lucas.tacos.config.OrderProps;
import com.lucas.tacos.data.TacoOrderRepository;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
@ConfigurationProperties(prefix = "taco.orders")
public class OrderController {
	
	TacoOrderRepository orderRepository;

	private OrderProps props;
	
	public OrderController(TacoOrderRepository orderRepository,
	OrderProps props) {
	this.orderRepository = orderRepository;
	this.props = props;
	}
	
	@GetMapping("/current")
	public String orderForm() {
		return "orderForm";
	}

	@PostMapping
	public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus) {
		if (errors.hasErrors()) {
			return "orderForm";
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		order.setUser((User) authentication.getPrincipal());
		orderRepository.save(order);
		log.info("Order submitted: {}", order);
		sessionStatus.setComplete();
		return "redirect:/home";
	}

	@GetMapping("/list")
	public String ordersForUser(@AuthenticationPrincipal User user, Model model) {
		Pageable pageable = PageRequest.of(0, props.getPageSize());
		model.addAttribute("orders", orderRepository.findByUserOrderByPlacedAtDesc(user, pageable));
		return "orderList";
	}
}
