package br.com.oficina;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OficinaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(OficinaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hello, world!");
	}
}


/*
*      /\ /\
*	\ /  V  \ /
*    x       x
*   /  \   /  \
*        V
 */