package com.algamoney.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@SpringBootApplication
public class AlgamoneyApiApplication {

	private static List<String> beans;

	@GetMapping("/beans")
	public ResponseEntity<String[]> beans() {
		String[] beansArr = beans.toArray(new String[beans.size()]);
		return ResponseEntity.ok(beansArr);
	}

	public static void main(String[] args) {
		ApplicationContext context =  SpringApplication.run(AlgamoneyApiApplication.class, args);
		AlgamoneyApiApplication.beans = new ArrayList<>(Arrays.asList(context.getBeanDefinitionNames()));
	}

}
