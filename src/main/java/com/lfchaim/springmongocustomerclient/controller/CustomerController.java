package com.lfchaim.springmongocustomerclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lfchaim.springmongocustomerclient.service.CustomerService;

import reactor.core.publisher.Mono;

@RestController
public class CustomerController {

	private CustomerService customerService;

	@Autowired
	public CustomerController(final CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping(path = "/customer/{quantity}")
	public Mono<String> generateCustomer(@PathVariable Long quantity) {
		this.customerService.generateCustomer(quantity);
		return Mono.just("Customer generator finished");
	}

}
