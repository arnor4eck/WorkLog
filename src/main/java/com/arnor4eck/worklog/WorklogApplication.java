package com.arnor4eck.worklog;

import com.arnor4eck.worklog.construction_project.ConstructionProject;
import com.arnor4eck.worklog.construction_project.ConstructionProjectRepository;
import com.arnor4eck.worklog.construction_project.coordinates.Coordinates;
import com.arnor4eck.worklog.construction_project.post.Post;
import com.arnor4eck.worklog.construction_project.post.PostRepository;
import com.arnor4eck.worklog.construction_project.post.files.FilesService;
import com.arnor4eck.worklog.construction_project.post.utils.PostStatus;
import com.arnor4eck.worklog.construction_project.utils.ObjectStatus;
import com.arnor4eck.worklog.cv.CVService;
import com.arnor4eck.worklog.user.role.Role;
import com.arnor4eck.worklog.user.role.RoleRepository;
import com.arnor4eck.worklog.user.User;
import com.arnor4eck.worklog.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;

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
					new User("contractor@mail.ru", "Подрядчик",
							passwordEncoder.encode("contractor_password"), "Подрядчиков Подрядчик Подрядчикович",
							List.of(roles.getFirst())),
					new User("supervision@mail.ru", "Технический Надзор",
							passwordEncoder.encode("supervision_password"), "Надзоров Надзор Надзорович",
							List.of(roles.get(1))),
					new User("inspector@mail.ru", "Государственная инспекция",
							passwordEncoder.encode("inspector_password"), "Инспекторов Инспектор Инспекторович",
							List.of(roles.get(2))),
					new User("developer@mail.ru", "Застройщик",
							passwordEncoder.encode("developer_password"), "Застройщиков Застройщик Застройщикович",
							List.of(roles.get(3))),
					new User("admin@mail.ru", "Админ",
							passwordEncoder.encode("admin_password"), "Админов Админ Админщикович",
							List.of(roles.getLast())));

			userRepository.saveAll(users);

			ConstructionProject project = new ConstructionProject("Жилой комплекс \"Ромашка\"", "Жилой комплекс класса «бизнес+» из трёх корпусов высотой от 3 до 28 этажей. Корпуса объединены гранд-лобби с мягкими зонами отдыха и кофейней, предусмотрена общественная терраса. На придомовой территории разместятся детская и спортивная площадки. Два подземных уровня займут паркинг на 257 машино-мест и кладовые.");

			ConstructionProject project2 = new ConstructionProject("Шоссе M-2", "Широкие дороги для быстро движущегося транспорта. Обычно спроектированы с четырьмя полосами движения, по две полосы в каждом направлении. На автомагистралях нет пересечений железных дорог, светофоров или пешеходных переходов.");

			Coordinates coord1 = new Coordinates(24.5734563746, 56.5617325627);
			Coordinates coord2 = new Coordinates(434.5734563746, 436.5617325627);

			project.setCoordinates(List.of(coord1, coord2));
			project2.setCoordinates(List.of(new Coordinates(284.6412647, 12.34673)));

			IntStream.range(1, 4).forEach(i -> project.getUsers().add(userRepository.findById((long) i).get()));
			project.addResponsibleSupervision(userRepository.findById(2L).get());
			project.addResponsibleContractor(userRepository.findById(1L).get());

			IntStream.range(1, 4).forEach(i -> project2.getUsers().add(userRepository.findById((long) i).get()));
			project2.addResponsibleSupervision(userRepository.findById(2L).get());
			project2.addResponsibleContractor(userRepository.findById(1L).get());

			project.setStatus(ObjectStatus.ACTIVE);
			project2.setStatus(ObjectStatus.COMPLETED);
			constructionProjectRepository.saveAll(List.of(project, project2));

			try{
				Files.createDirectories(Path.of(filesService.createPathToObject(project.getId())));
				Files.createDirectories(Path.of(filesService.createPathToObject(project2.getId())));
			}catch (IOException e){
				throw new RuntimeException(e);
			}

			List<Post> posts = List.of(new Post("Нарушение разметки", "Поменять белый цвет на желтый."),
					new Post("Исправление разметки", "Изменено."), new Post("Завершение проекта", "роект - В С Е"));
			posts.get(0).setStatus(PostStatus.ADDING_VIOLATIONS);
			posts.get(0).setAuthor(userRepository.findById(2L).get());
			posts.get(1).setStatus(PostStatus.CONFIRMING_CORRECTIONS);
			posts.get(1).setAuthor(userRepository.findById(1L).get());
			posts.get(2).setAuthor(userRepository.findById(1L).get());
			posts.get(2).setStatus(PostStatus.PERFORMING_WORK);

			posts.get(0).setObject(constructionProjectRepository.findById(1L).orElseThrow());
			posts.get(1).setObject(constructionProjectRepository.findById(1L).orElseThrow());
			posts.get(2).setObject(constructionProjectRepository.findById(2L).orElseThrow());

			postRepository.saveAll(posts);
		};
	}
}
