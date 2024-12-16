package com.niit.UserAuthentication.service;

import com.niit.UserAuthentication.domain.UserData;
import com.niit.UserAuthentication.exception.InvalidCredentialsException;
import com.niit.UserAuthentication.exception.UserAlreadyExistsException;

public interface UserService {
    UserData saveUser(UserData user) throws UserAlreadyExistsException;
    UserData getUserByUserIdAndPassword(String email, String password) throws InvalidCredentialsException;
}
