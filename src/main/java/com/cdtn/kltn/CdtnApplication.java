package com.cdtn.kltn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@SpringBootApplication
@EnableWebSecurity
@EnableJpaRepositories
@EnableScheduling

public class CdtnApplication {

    public static void main(String[] args) {
        SpringApplication.run(CdtnApplication.class, args);
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3001","http://localhost:3000"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }


//    @Bean
//    CommandLineRunner runner(UserService userService) {
//        return args -> {
//            userService.saveUser(User.builder()
//                    .fullName("Nguyen Dinh Loc")
//                    .userName("ngdinhloc2000@gmail.com")
//                    .password("12345")
//                    .role(null)
//                    .build());
//
//            userService.addRoleToUser("ngdinhloc2000@gmail.com", "ROLE_COMPANY");

//            userService.saveUser(User.builder()
//                    .fullName("Do Nam Khanh")
//                    .userName("khanhdn@gmail.com")
//                    .password("12345")
//                    .role(null)
//                    .build());
//
//            userService.addRoleToUser("khanhdn@gmail.com", "ROLE_CLIENT");
//        };
//    }

}
