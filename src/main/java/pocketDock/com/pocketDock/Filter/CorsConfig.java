package pocketDock.com.pocketDock.Filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Mapping pour les endpoints de publication
        registry.addMapping("/publication/**")
                .allowedOrigins("http://localhost:4200") // Autoriser les requêtes depuis Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(360000);

        // Mapping pour les endpoints de commentaire
        registry.addMapping("/commentaire/**")
                .allowedOrigins("http://localhost:4200") // Autoriser les requêtes depuis Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(360000);

        // Ajoutez d'autres mappings pour les autres endpoints ici
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200") // Autoriser les requêtes depuis Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(360000);

        // Ajoutez d'autres mappings au besoin pour les autres endpoints de votre groupe
    }
}