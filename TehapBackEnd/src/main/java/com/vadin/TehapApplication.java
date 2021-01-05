package com.vadin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.vadin.entity.Users;
import com.vadin.entity.viewmodel.ActivitySubmitViewModel;
import com.vadin.repository.UserRepository;
import com.vadin.service.ActivityService;
import com.vadin.service.UserService;

@SpringBootApplication
public class TehapApplication {

	public static void main(String[] args) {
		SpringApplication.run(TehapApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;

	@Bean
	CommandLineRunner createInitialUsers(UserService userService, ActivityService activityService) {
		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {

				Users inDB = userRepository.findByUsername("velia");
				if (inDB == null) {
					Users user = new Users();
					user.setUsername("velia");
					user.setDisplayName("adin");
					user.setPassword("admin");
					userService.save(user);
					/*for (int i = 0; i < 5; i++) {
						Users usera = new Users();
						usera.setUsername("test" + i);
						usera.setDisplayName("test" + i);
						usera.setPassword("P4ssword");
						userService.save(usera);
						for (int j = 0; j < 11; j++) {
							ActivitySubmitViewModel activity = new ActivitySubmitViewModel();
							activity.setTitle("Activity" + j + " from user " + i);
							activityService.saveActivity(activity, usera);
						}
					}*/

				} else {
					return;
				}

			}
		};
	}

}
