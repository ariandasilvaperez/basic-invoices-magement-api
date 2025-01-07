package www.ariandasilvaperez.spend_sense.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/api/v1/**")
                .allowedOrigins("localhost:4000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("Origin", "Content-Type", "Authorization", "Accept")
                .allowCredentials(true)
                .maxAge(3600);

        registry.addMapping("/api/v1/auth/**")
                .allowedOrigins("localhost:4000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("Origin", "Content-Type", "Authorization", "Accept")
                .allowCredentials(false)
                .maxAge(3600);
    }
}
