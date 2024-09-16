package com.abdur.urlShortener.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdur.urlShortener.model.Url;
import com.abdur.urlShortener.repository.UrlRepository;

@Service
public class UrlService {

	private static final String CHAR_POOL = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final int SHORT_URL_LENGTH = 6;

	@Autowired
	private UrlRepository urlRepository;

	public String shortUrl(String originalUrl, Integer days) {
		// Generate a unique short URL
		String shortUrl;
		do {
			shortUrl = generateShortUrl();
		} while (urlRepository.findByShortUrl(shortUrl).isPresent());

		// Calculate expiration date
		LocalDate expirationDate = calculateExpirationDate(days);

		// Save new URL record
		Url url = new Url();
		url.setOriginalUrl(originalUrl);
		url.setShortUrl(shortUrl);
		url.setExpirationDate(expirationDate);
		urlRepository.save(url);

		return shortUrl;
	}

	public Optional<Url> getOriginalUrl(String shortUrl) {
		Optional<Url> url = urlRepository.findByShortUrl(shortUrl);
		if (url.isPresent() && isExpired(url.get())) {
			urlRepository.delete(url.get());
			return Optional.empty();
		}
		return url;
	}

	private String generateShortUrl() {
		Random random = new Random();
		StringBuilder shortUrl = new StringBuilder();

		for (int i = 0; i < SHORT_URL_LENGTH; i++) {
			shortUrl.append(CHAR_POOL.charAt(random.nextInt(CHAR_POOL.length())));
		}
		return shortUrl.toString();
	}

	private LocalDate calculateExpirationDate(Integer days) {
		if (days == null || days <= 0) {
			// Default expiration time 7 Days
			return LocalDateTime.now().plusDays(7).toLocalDate();
		}
		return LocalDateTime.now().plusDays(days).toLocalDate();
	}

	private boolean isExpired(Url url) {
		return url.getExpirationDate().isBefore(LocalDate.now());
	}
}
