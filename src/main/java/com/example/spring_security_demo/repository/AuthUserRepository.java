package com.example.spring_security_demo.repository;

import com.example.spring_security_demo.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {
    Optional<AuthUser> findByUsername(String username);

    @Query("""
            select a from AuthUser as a where a.id=:id
            """)
    List<AuthUser> getAuthUserById(@Param("id") Integer id);
}
