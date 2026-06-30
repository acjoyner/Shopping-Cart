package com.acjoyner.dream_shops.data;

import com.acjoyner.dream_shops.model.Role;
import com.acjoyner.dream_shops.model.User;
import com.acjoyner.dream_shops.repository.RoleRepository;
import com.acjoyner.dream_shops.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
       // createDefaultUserIfNotExists();
        createDefaultRoleIfNotExists(defaultRoles);
        //createDefaultAdminIfNotExists();
    }

    private void createDefaultUserIfNotExists() {
        Role userRole =  roleRepository.findByName("ROLE_USER").get();
        for (int i = 0; i <= 5; i++){
            String defaultEmail = "user" + i + "@email.com";
            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("User");
            user.setLastName("User"+i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("12345"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default user "+i+" created successfully.");
        }
    }

    private void createDefaultAdminIfNotExists() {
        Role adminRole =  roleRepository.findByName("ROLE_ADMIN").get();
        for (int i = 0; i <= 5; i++){
            String adminEmail = "admin" + i + "@email.com";
            if(userRepository.existsByEmail(adminEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Admin"+i);
            user.setEmail(adminEmail);
            user.setPassword(passwordEncoder.encode("12345"));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            System.out.println("Default admin user "+i+" created successfully.");
        }
    }


    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }

    private void createDefaultRoleIfNotExists(Set<String> roles){
        roles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role::new).forEach(roleRepository::save);
    }
}
