package com.example.renthouseweb_be;

import com.example.renthouseweb_be.model.account.Role;
import com.example.renthouseweb_be.service.RoleService;
import com.example.renthouseweb_be.service.UserService;
import com.example.renthouseweb_be.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DatabaseLoader implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(DatabaseLoader.class);
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public DatabaseLoader(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... strings) throws Exception {
        try {
            Iterable<Role> roleList = roleService.findAll();
            if (!roleList.iterator().hasNext()) {
                roleService.save(new Role(Constants.ROLE_ADMIN));
                roleService.save(new Role(Constants.ROLE_USER));
            }
            if (roleService.findByName(Constants.ROLE_ADMIN) == null) {
                roleService.save(new Role(Constants.ROLE_ADMIN));
                logger.info("INSERT ROLE_ADMIN");
            }
            if (roleService.findByName(Constants.ROLE_USER) == null) {
                roleService.save(new Role(Constants.ROLE_USER));
                logger.info("INSERT ROLE_USER");
            }
            logger.info("-------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
