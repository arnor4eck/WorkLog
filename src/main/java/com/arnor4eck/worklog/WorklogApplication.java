package com.arnor4eck.worklog;

import com.arnor4eck.worklog.construction_project.ConstructionProject;
import com.arnor4eck.worklog.construction_project.ConstructionProjectRepository;
import com.arnor4eck.worklog.construction_project.coordinates.Coordinates;
import com.arnor4eck.worklog.construction_project.post.Post;
import com.arnor4eck.worklog.construction_project.post.PostRepository;
import com.arnor4eck.worklog.construction_project.post.files.FilesService;
import com.arnor4eck.worklog.construction_project.utils.ObjectStatus;
import com.arnor4eck.worklog.cv.CVService;
import com.arnor4eck.worklog.user.Role;
import com.arnor4eck.worklog.user.RoleRepository;
import com.arnor4eck.worklog.user.User;
import com.arnor4eck.worklog.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class WorklogApplication {

	private final CVService tesseract;

	private final RoleRepository roleRepository;

	private final UserRepository userRepository;

	private final PostRepository postRepository;

	private final ConstructionProjectRepository constructionProjectRepository;

	private final PasswordEncoder passwordEncoder;

	private final FilesService filesService;


    public static void main(String[] args) {
		SpringApplication.run(WorklogApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(){
		return args -> {
			List<Role> roles = List.of(new Role("ROLE_CONTRACTOR"), new Role("ROLE_SUPERVISION"),
					new Role("ROLE_INSPECTOR"), new Role("ROLE_DEVELOPER"),
					new Role("ROLE_ADMIN"));

			roleRepository.saveAll(roles);

			List<User> users = List.of(
					new User("vladi52@ngg.com", "Прораб",
							passwordEncoder.encode("password"), "vladi",
							List.of(roles.getLast())),
					new User("bere@mail.ru", "застройщик",
							passwordEncoder.encode("password"), "berenby",
							List.of(roles.get(3))),
					new User("kura@jam.com", "Технический контроль",
							passwordEncoder.encode("password"), "vladi", List.of(roles.get(1))),
					new User("kika@mail.ru", "отец",
							passwordEncoder.encode("password"), "kika",
							roles));

			userRepository.saveAll(users);

			ConstructionProject project = new ConstructionProject("project_1", "test project");

			ConstructionProject project2 = new ConstructionProject("project_2", "test2 project");

			Coordinates coord1 = new Coordinates(24.5734563746, 56.5617325627);
			Coordinates coord2 = new Coordinates(434.5734563746, 436.5617325627);

			project.setCoordinates(List.of(coord1, coord2));

			project.getUsers().add(users.get(0));
			project.setStatus(ObjectStatus.ACTIVE);
			project.addResponsibleSupervision(users.get(1));
			project.addResponsibleContractor(users.get(2));

			constructionProjectRepository.saveAll(List.of(project, project2));

			try{
				Files.createDirectories(Path.of(filesService.createPathToObject(project.getId())));
				Files.createDirectories(Path.of(filesService.createPathToObject(project2.getId())));
			}catch (IOException e){
				throw new RuntimeException(e);
			}

			List<Post> posts = List.of(new Post("escape the backrooms", "biba"),
					new Post("escape the backrooms2", "biba2"));

			ConstructionProject project1 = constructionProjectRepository.findById(1L).orElseThrow();
			User user1 = userRepository.findById(1L).orElseThrow();

			posts.forEach(p -> {
				p.setObject(project1);
				p.setAuthor(user1);
			});

			postRepository.saveAll(posts);
		};
	}
}
