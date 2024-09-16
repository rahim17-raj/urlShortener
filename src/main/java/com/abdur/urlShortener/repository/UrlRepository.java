package com.abdur.urlShortener.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abdur.urlShortener.model.Url;

public interface UrlRepository extends JpaRepository<Url, Integer> {

	Optional<Url> findByShortUrl(String shortUrl);

	Optional<Url> findByOriginalUrlAndExpirationDateAfter(String originalUrl, LocalDateTime now);

	void deleteAllByExpirationDateBefore(LocalDateTime now);
}
