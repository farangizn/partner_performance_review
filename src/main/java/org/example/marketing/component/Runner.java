package org.example.marketing.component;

import lombok.RequiredArgsConstructor;
import org.example.marketing.entity.*;
import org.example.marketing.entity.enums.RoleName;
import org.example.marketing.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final BloggerRepository bloggerRepository;
    private final LinkRepository linkRepository;
    private final BloggerClientRepository bloggerClientRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        Blogger blogger1 = Blogger.builder().clientNumber(0).email("mittivine@icloud.com").phone("+998940440404").name("mittivine").build();
        Blogger blogger2 = Blogger.builder().clientNumber(0).email("subyektiv@gmail.com").phone("+998934334343").name("subyektiv").build();

        bloggerRepository.save(blogger1);
        bloggerRepository.save(blogger2);

        Link link1 = Link.builder().blogger(blogger1).title("http://localhost:8080/register/mittivine").build();
        Link link2 = Link.builder().blogger(blogger2).title("http://localhost:8080/register/subyektiv").build();

        linkRepository.save(link1);
        linkRepository.save(link2);

        Role roleAdmin = new Role(RoleName.ROLE_ADMIN);
        roleRepository.save(roleAdmin);

        User user = new User(
                "a",
                passwordEncoder.encode("1")
        );
        user.setRoles(List.of(roleAdmin));

        userRepository.save(user);

    }
}
