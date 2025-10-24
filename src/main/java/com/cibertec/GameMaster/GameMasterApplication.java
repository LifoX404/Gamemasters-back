package com.cibertec.GameMaster;

import com.cibertec.GameMaster.infraestructure.database.entity.RoleType;
import com.cibertec.GameMaster.infraestructure.database.entity.UserEntity;
import com.cibertec.GameMaster.infraestructure.database.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GameMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameMasterApplication.class, args);
		System.out.println("Is running...");
	}

	@Bean
	CommandLineRunner initDatabase(UserRepository repository) {
		return args -> {

			// Evita crear duplicados
			if (!repository.existsByUsername("lifox404")) {
				UserEntity adminUser = UserEntity.builder()
						.username("lifox404")
						.email("carlifoxy@gmail.com")
						.password("$2a$12$eMSYrH2WIoYhtzVS7oN8suSMwqdmPJZqdzzRcW09TUSK0d4SOhXrK")
						.role(RoleType.ADMIN)
						.build();

				repository.save(adminUser);
				System.out.println("✅ Usuario administrador creado correctamente.");
			} else {
				System.out.println("ℹ️ Usuario administrador ya existe, no se creó otro.");
			}
		};
	}

}
