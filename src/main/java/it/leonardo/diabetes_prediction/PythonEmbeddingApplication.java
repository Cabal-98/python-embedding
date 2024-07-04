package it.leonardo.diabetes_prediction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("it.leonardo.diabetes_prediction")
@SpringBootApplication
public class PythonEmbeddingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PythonEmbeddingApplication.class, args);
	}

}
