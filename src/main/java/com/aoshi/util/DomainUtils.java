package com.aoshi.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DomainUtils {

	@Value("${DOMAIN_MAIN_URL}")
	private String URL;


	private static String DOMAIN_URL;


	@PostConstruct
	public void init() {
		DOMAIN_URL = URL;
	}

	public static String getDOMAIN_URL() {
		return DOMAIN_URL;
	}

}
