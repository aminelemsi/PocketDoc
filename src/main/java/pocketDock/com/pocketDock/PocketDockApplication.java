package pocketDock.com.pocketDock;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@SpringBootApplication
@EnableAspectJAutoProxy
@RequiredArgsConstructor
@EnableScheduling
@EnableJpaAuditing
public class PocketDockApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocketDockApplication.class, args);
	}

}
