package com.railway.application.config;

import com.railway.application.interceptors.CustomInterceptor;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ProjectConfig implements WebMvcConfigurer {


    private final CustomInterceptor customInterceptor;

    public ProjectConfig(CustomInterceptor customInterceptor) {
        this.customInterceptor = customInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(customInterceptor)
               .addPathPatterns("/trains/**")
               .excludePathPatterns();
    }

    @Bean
    public OpenAPI openAPI()
    {
     return new OpenAPI().info(
             new Info().title("Train Ticket Booking API")
                     .description("REST APIs for Train Ticket Booking System")
                     .version("1.0.0")
                     .contact(new Contact()
                             .name("Sourajit Sadhukhan")
                             .email("ssourajit00@gmail.com")
                             .url("https://github.com/SourajitS")
                     )
                     .license(new License()
                             .name("Apache 2.0")
                             .url("https://www.apache.org/licenses/LICENSE-2.0.html")
                     )
     );
    }
}
