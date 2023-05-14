package com.backend.oembed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class OembedApplication {

	public static void main(String[] args) {
		SpringApplication.run(OembedApplication.class, args);
	}

	// html, css, js 참고 https://d-life93.tistory.com/341
}
