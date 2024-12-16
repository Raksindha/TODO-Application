package com.niit.UserAuthentication.repository;

import com.niit.UserAuthentication.domain.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserData, String> {
    UserData findByEmailAndPassword(String emailId, String password);
}
