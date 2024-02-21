package com.springboot.elearningmanagementstructurebyfeature.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

       boolean existsByEmail(String email);

       boolean existsByUsername( String username);

       Optional<User> findByEmailAndIsDeletedAndIsVerified(String email, Boolean isDeleted, Boolean isVerified);

}
