package com.example.demo;

import com.hexadevlabs.gpt4all.LLModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Path;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args)   {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String args[]) throws Exception
	{

		// Print statement when method is called
		String prompt = "### Human:\nWhat is the meaning of life\n### Assistant:";

		// Replace the hardcoded path with the actual path where your model file resides
		String modelFilePath = "/Users/fzaslavs/Library/Application Support/nomic.ai/GPT4All/ggml-gpt4all-j-v1.3-groovy.bin";

		try (LLModel model = new LLModel(Path.of(modelFilePath))) {

			// May generate up to 4096 tokens but generally stops early
			LLModel.GenerationConfig config = LLModel.config()
					.withNPredict(4096).build();

			// Will also stream to standard output
			String fullGeneration = model.generate(prompt, config, true);

		} catch (Exception e) {
			// Exceptions generally may happen if the model file fails to load
			// for a number of reasons such as a file not found.
			// It is possible that Java may not be able to dynamically load the native shared library or
			// the llmodel shared library may not be able to dynamically load the backend
			// implementation for the model file you provided.
			//
			// Once the LLModel class is successfully loaded into memory the text generation calls
			// generally should not throw exceptions.
			e.printStackTrace(); // Printing here but in a production system you may want to take some action.
		}

	}

}
