package com.springboot.elearningmanagementstructurebyfeature.auth;
import com.springboot.elearningmanagementstructurebyfeature.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query("""
    UPDATE User as u
    SET u.verifyCode = ?2 where u.email = ?1
    """)
    void updateVerifiedCode (String email, String code);

    Optional<User> findByEmailAndVerifyCode(String email, String verifyCode);

}
