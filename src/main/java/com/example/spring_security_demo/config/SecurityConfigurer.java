package com.example.spring_security_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfigurer {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorization -> {
            authorization.
                    requestMatchers("/custom/permitAll",
                            "/authUser/create",
                            "/authUser/getAll",
                            "/authUser/getWithRoles"
                            //"/authUser/delete"
                    ).permitAll()
                    .anyRequest()
                    .authenticated();
        }).formLogin(AbstractAuthenticationFilterConfigurer::permitAll);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.rememberMe(r->{
            r.tokenValiditySeconds(24*60*60)
                    .key("key1")
                    .rememberMeParameter("hi");
        });
        return http.build();
       /* http.logout(l -> {
            l.logoutUrl("/authUser/delete")
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID");
        });*/
        //http.cors(AbstractHttpConfigurer::disable);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

/*    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("user123")
                .roles("USER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin123")
                .roles("ADMIN")
                .build();

        UserDetails manager = User.withDefaultPasswordEncoder()
                .username("manager")
                .password("manager123")
                .roles("MANAGER")
                .build();

        return new InMemoryUserDetailsManager(user, admin, manager);

    }*/
}
