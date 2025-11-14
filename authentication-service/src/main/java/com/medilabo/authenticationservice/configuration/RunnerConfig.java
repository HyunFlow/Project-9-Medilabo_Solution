package com.medilabo.authenticationservice.configuration;

import com.medilabo.authenticationservice.model.UserCredential;
import com.medilabo.authenticationservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class RunnerConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

//    /*
//    Setup in-memory users, hors scope of project applications
//    */
//
//    @Override
//    public void run(String... args) {
//        UserCredential user = new UserCredential("user", encoder.encode("user"), true);
//        UserCredential admin = new UserCredential("admin", encoder.encode("admin"), true);
//        UserCredential evicted = new UserCredential("evicted", encoder.encode("evicted"), false);
//        userRepository.saveAll(List.of(user, admin, evicted));
//        log.info("in-memory users initialized - OK");
//    }

    /*
    Setup postgresql default users, hors scope of project applications
    */
    @Override
    public void run(String... args) {

        if (userRepository.findByUsername("admin").isEmpty()) {
            UserCredential user = new UserCredential("user", encoder.encode("user"), true);
            UserCredential admin = new UserCredential("admin", encoder.encode("admin"), true);
            UserCredential evicted = new UserCredential("evicted", encoder.encode("evicted"), false);

            userRepository.saveAll(List.of(user, admin, evicted));
            log.info("Default users initialized - OK");
        } else {
            log.info("Default users already exist. Skipping initialization.");
        }
    }
}
