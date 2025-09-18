package com.arnor4eck.worklog;

import com.arnor4eck.worklog.user.Role;
import com.arnor4eck.worklog.user.RoleRepository;
import com.arnor4eck.worklog.user.User;
import com.arnor4eck.worklog.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class WorklogApplication {

	private final RoleRepository roleRepository;

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(WorklogApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(){
		return args -> {
			List<Role> roles = List.of(new Role("ROLE_CONTRACTOR"), new Role("ROLE_SUPERVISION"),
					new Role("ROLE_INSPECTOR"), new Role("ROLE_DEVELOPER"),
					new Role("ROLE_ADMIN"), new Role("ROLE_WORKER"));

			List<User> users = List.of(new User("vladi52@ngg.com", "Прораб",
					passwordEncoder.encode("password"), "vladi",
					List.of(roles.getLast())));

			roleRepository.saveAll(roles);
			userRepository.saveAll(users);
		};
	}
}
