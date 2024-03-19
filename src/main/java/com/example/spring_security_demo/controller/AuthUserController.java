package com.example.spring_security_demo.controller;

import com.example.spring_security_demo.entity.AuthUser;
import com.example.spring_security_demo.entity.Role;
import com.example.spring_security_demo.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("authUser")
public class AuthUserController {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/create")
    public String createTest() {
        AuthUser user = AuthUser.builder()
                .username("user")
                .password(passwordEncoder.encode("123"))
                .role(Role.USER)
                .build();

        AuthUser admin = AuthUser.builder()
                .username("admin")
                .password(passwordEncoder.encode("123"))
                .role(Role.ADMIN)
                .build();

        this.authUserRepository.save(user);
        System.out.println(user);
        this.authUserRepository.save(admin);
        System.out.println(admin);


        return "Create successfully";
    }

    @GetMapping("/get")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AuthUser get(@RequestParam Integer id) {
        List<AuthUser> authUser = this.authUserRepository.getAuthUserById(id);
        if (authUser.isEmpty()) {
            return AuthUser.builder()
                    .username("AuthUser topilmadi")
                    .build();
        }
        return authUser.get(0);
    }

    @GetMapping("/getAll")
    public List<AuthUser> getAll() {
        return this.authUserRepository.findAll();
    }

    @GetMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@RequestParam Integer id) {
        List<AuthUser> authUser = this.authUserRepository.getAuthUserById(id);
        if (!authUser.isEmpty()) {
            this.authUserRepository.delete(authUser.get(0));
            return "Delete successfully";
        }
        return "Auth user is not found";
    }

    @GetMapping("/getWithRoles")
    public AuthUser getWithRoles(@RequestParam Integer id) {
        Optional<AuthUser> authUser = this.authUserRepository.findById(id);
        return authUser.orElseGet(() -> AuthUser.builder()
                .build());
    }
}
