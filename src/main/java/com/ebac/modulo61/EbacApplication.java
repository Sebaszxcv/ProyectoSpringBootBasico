package com.ebac.modulo61;

import com.ebac.modulo61.context.Contexto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EbacApplication implements CommandLineRunner {

	@Autowired
	private Contexto contexto;

	public static void main(String[] args) {
		SpringApplication.run(EbacApplication.class, args);
	}

	@Override
	public void run(String... args) {
		contexto.ejecutarServicios();
	}
}
