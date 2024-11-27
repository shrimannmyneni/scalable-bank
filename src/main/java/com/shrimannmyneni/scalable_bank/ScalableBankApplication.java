package com.shrimannmyneni.scalable_bank;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "Shrimann Bank App",
		description = "Backend REST APIs for Shrimann Bank",
		version = "v1.0",
		contact = @Contact(
				name = "Shrimann Myneni",
				email = "shrimann@umich.edu",
				url = "https://www.linkedin.com/in/shrimann-myneni-17286b214/"
		),
		license = @License(
				name = "Shrimann Myneni",
				url = "https://github.com/shrimannmyneni/scalable-bank"
		)
),
externalDocs = @ExternalDocumentation(
		description = "The Java Academy Bank App Documentation",
		url = "https://github.com/musdon/tja_bank_app"
)
)

public class ScalableBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScalableBankApplication.class, args);
	}

}
