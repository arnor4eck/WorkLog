package com.arnor4eck.worklog;

import com.arnor4eck.worklog.construction_project.ConstructionProject;
import com.arnor4eck.worklog.construction_project.ConstructionProjectRepository;
import com.arnor4eck.worklog.construction_project.coordinates.Coordinates;
import com.arnor4eck.worklog.construction_project.post.Post;
import com.arnor4eck.worklog.construction_project.post.PostRepository;
import com.arnor4eck.worklog.construction_project.post.files.FilesService;
import com.arnor4eck.worklog.construction_project.post.utils.PostType;
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

	private final RoleRepository roleRepository;

	private final UserRepository userRepository;

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
			userRepository.save(new User("iii@mail.ru", "Подрядчик", "password", "Попова Варвара Андреевна", List.of(roles.get(0))));

			ConstructionProject project = new ConstructionProject("Некрасовка", "Жилой комплекс класса «бизнес+» из трёх корпусов высотой от 3 до 28 этажей. Корпуса объединены гранд-лобби с мягкими зонами отдыха и кофейней, предусмотрена общественная терраса. На придомовой территории разместятся детская и спортивная площадки. Два подземных уровня займут паркинг на 257 машино-мест и кладовые.");

			ConstructionProject project2 = new ConstructionProject("Пропект Мира 194", "Широкие дороги для быстро движущегося транспорта. Обычно спроектированы с четырьмя полосами движения, по две полосы в каждом направлении. На автомагистралях нет пересечений железных дорог, светофоров или пешеходных переходов.");

			ConstructionProject project3 = new ConstructionProject("Каргопольская улица д. 18",
					"Современная и стильная конструкция закрытого типа с раздвижным панорамным остеклением, вальмовой крышей покрытой мягкой кровлей и системой водостока. Все деревянные элементы беседки обрабатываются специальными составами против процессов гниения и для повышения пожаробезопасности, что значительно увеличивает срок ее службы.");

			ConstructionProject project4 = new ConstructionProject("Флотская улица д. 54, д. 58к1",
					"Одноэтажный дом 12 на 8 метров, общей площадью 96 м2, из СИП панелей, с террасой и спальней на первом этаже.");


			List<Coordinates> coord1 = List.of(
					new Coordinates(37.5051681643041, 55.8554111603478),
					new Coordinates(37.5051839094663, 55.8554171325542),
					new Coordinates(37.5065189053775, 55.8546957632065),
					new Coordinates(37.5065805079280, 55.8546892396364),
					new Coordinates(37.5066376875542, 55.8546837581157),
					new Coordinates(37.5066423660015, 55.8546831112143),
					new Coordinates(37.5066383562679, 55.8546710401115),
					new Coordinates(37.5064796084454, 55.8546935286268),
					new Coordinates(37.5064325378433, 55.8547091229083),
					new Coordinates(37.5064179459915, 55.8547273562699),
					new Coordinates(37.5064100429371, 55.8547340838674),
					new Coordinates(37.5029119751658, 55.8542345482524),
					new Coordinates(37.5028374215273, 55.8542132451531),
					new Coordinates(37.5028087433880, 55.8542050544049),
					new Coordinates(37.5027640655535, 55.8541922922719),
					new Coordinates(37.5027511506080, 55.8542458768261),
					new Coordinates(37.5027440306133, 55.8542754713786),
					new Coordinates(37.5027366392151, 55.8543061527109),
					new Coordinates(37.5027365434356, 55.8543066556831),
					new Coordinates(37.5027364636177, 55.8543070418940),
					new Coordinates(37.5027341967650, 55.8543175773704),
					new Coordinates(37.5027276194836, 55.8543440822357),
					new Coordinates(37.5026842118273, 55.8545077100697));
			List<Coordinates> coord2 = List.of(
					new Coordinates(37.6597164421708, 55.8368057992660),
					new Coordinates(37.6596903157171, 55.8368177247358),
					new Coordinates(37.6596489456177, 55.8368332176435),
					new Coordinates(37.6595588116452, 55.8368710569844),
					new Coordinates(37.6595046403276, 55.8368941828122),
					new Coordinates(37.6594899397336, 55.8369096053232),
					new Coordinates(37.6594451195874, 55.8369284258267),
					new Coordinates(37.6594386028550, 55.8369233146876),
					new Coordinates(37.6594367454867, 55.8369218530731)
			);

			project.setCoordinates(coord1);
			project3.setCoordinates(coord1);
			project2.setCoordinates(coord2);
			project4.setCoordinates(coord2);

			IntStream.range(1, 5).forEach(i -> project.getUsers().add(userRepository.findById((long) i).get()));
			project.addResponsibleSupervision(userRepository.findById(2L).get());
			project.addResponsibleContractor(userRepository.findById(1L).get());

			IntStream.range(1, 5).forEach(i -> project4.getUsers().add(userRepository.findById((long) i).get()));
			project4.addResponsibleSupervision(userRepository.findById(2L).get());
			project4.addResponsibleContractor(userRepository.findById(6L).get());

			IntStream.range(1, 5).forEach(i -> project3.getUsers().add(userRepository.findById((long) i).get()));
			IntStream.range(1, 5).forEach(i -> project2.getUsers().add(userRepository.findById((long) i).get()));
			project3.addResponsibleSupervision(userRepository.findById(2L).get());
			project3.addResponsibleContractor(userRepository.findById(6L).get());
			project2.addResponsibleSupervision(userRepository.findById(2L).get());
			project2.addResponsibleContractor(userRepository.findById(1L).get());

			project.setStatus(ObjectStatus.ACTIVE);
			project2.setStatus(ObjectStatus.COMPLETED);
			project3.setStatus(ObjectStatus.PLANNED);
			project4.setStatus(ObjectStatus.ACTIVE);
			constructionProjectRepository.saveAll(List.of(project, project2, project3, project4));

			try{
				Files.createDirectories(Path.of(filesService.createPathToObject(project.getId())));
				Files.createDirectories(Path.of(filesService.createPathToObject(project2.getId())));
				Files.createDirectories(Path.of(filesService.createPathToObject(project3.getId())));
				Files.createDirectories(Path.of(filesService.createPathToObject(project4.getId())));
			}catch (IOException e){
				throw new RuntimeException(e);
			}

		};
	}
}
