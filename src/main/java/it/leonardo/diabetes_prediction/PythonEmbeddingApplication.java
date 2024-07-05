package it.leonardo.diabetes_prediction;

import ai.djl.Application;
import it.leonardo.diabetes_prediction.ai.DefineModel;
import org.springframework.boot.DefaultApplicationArguments;
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
