package com.abdur.urlShortener.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Url {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String originalUrl;
	private String shortUrl;
	private LocalDateTime expirationDate;
}
