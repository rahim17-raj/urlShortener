package com.abdur.urlShortener.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.abdur.urlShortener.model.Url;
import com.abdur.urlShortener.service.UrlService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UrlController {

	@Autowired
	private UrlService urlService;

	@PostMapping("/urlShorten")
	public String shortenUrl(@RequestParam String originalUrl, @RequestParam(required = false) Integer days,
			ModelMap model) {
		String shortUrl = urlService.shortUrl(originalUrl, days);
		model.addAttribute("shortUrl", shortUrl);
		return "result"; 
	}

	@GetMapping("/{shortUrl}")
	public void redirectToOriginalUrl(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
		Optional<Url> url = urlService.getOriginalUrl(shortUrl);
		if (url.isPresent()) {
			response.sendRedirect(url.get().getOriginalUrl());
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "URL not found or expired");
		}
	}
}
