package com.example.spring_security_demo.config;

import com.example.spring_security_demo.entity.AuthUser;
import com.example.spring_security_demo.entity.Role;
import com.example.spring_security_demo.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = this.authUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username:%s".formatted(username)));
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+authUser.getRole().name()));
        return new User(authUser.getUsername(), authUser.getPassword(), authorities);
    }
}
