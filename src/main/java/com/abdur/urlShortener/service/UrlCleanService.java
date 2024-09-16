package com.abdur.urlShortener.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.abdur.urlShortener.repository.UrlRepository;

@Service
public class UrlCleanService {

	@Autowired
	private UrlRepository urlRepository;

	@Scheduled
	public void deleteExpiredUrls() {
		LocalDateTime now = LocalDateTime.now();
		urlRepository.deleteAllByExpirationDateBefore(now);
	}
}
