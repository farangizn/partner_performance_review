package org.example.marketing.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.userDetailsService(customUserDetailsService);

        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(m -> {
            m
                    .requestMatchers("/login", "/register", "/register/*","/css/**", "/")
                    .permitAll()
                    .requestMatchers("/report",  "/save", "/blogger/add", "/info")
                    .hasRole("ADMIN")
                    .anyRequest().authenticated();
        });

        http.formLogin(m -> {
            m.loginPage("/login")
                    .successForwardUrl("/admin");
        });

//        http.logout(m -> {
//            m.logoutUrl("/logout").logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"));
//        });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
