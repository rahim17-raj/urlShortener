package com.abdur.urlShortener.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String loadHomePage() {
		return "index"; 
	}
}