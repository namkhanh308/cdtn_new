package com.cdtn.kltn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableWebSecurity
@EnableJpaRepositories
public class CdtnApplication {

    public static void main(String[] args) {
        SpringApplication.run(CdtnApplication.class, args);
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
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
