package com.mystore.shopstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;

@SpringBootApplication
public class ShopstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopstoreApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
				registry
						.addResourceHandler("/uploads/**")
						.addResourceLocations("file:" + System.getProperty("user.dir") + "/uploads/");
			}
		};
	}

}
