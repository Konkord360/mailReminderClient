package com.koncor.mailReminder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication
public class SpringTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringTestApplication.class, args);
    }

//    @Bean
//    ApplicationRunner applicationRunner(UserRepository userRepository) {
//        return args -> {
//            User u = new User();
//            u.setLogin("SpringTest");
//            u.setPassword("SpringTest".toCharArray());
//            u.setSalt("SpringTest".getBytes());
//
//			System.out.println(userRepository.save(u));
//			System.out.println(userRepository.findByLogin("SpringTest"));
//			System.out.println(userRepository.findByLogin("Konrad"));
//        };
//
//    }

//    @Bean
//    public ResourceBundleMessageSource messageSource() {
//        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
//        source.setBasename("messages");
//        source.setCacheSeconds(3600); // Refresh cache once per hour.
//        return source;
//    }
}
