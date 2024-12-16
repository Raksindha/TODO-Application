package com.niit.UserAuthentication.controller;

import com.niit.UserAuthentication.domain.UserData;
import com.niit.UserAuthentication.exception.InvalidCredentialsException;
import com.niit.UserAuthentication.exception.UserAlreadyExistsException;
import com.niit.UserAuthentication.security.SecurityTokenGenerator;
import com.niit.UserAuthentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private UserService userService;
    private SecurityTokenGenerator securityTokenGenerator;
    @Autowired
    public UserController(UserService userService, SecurityTokenGenerator securityTokenGenerator) {
        this.userService = userService;
        this.securityTokenGenerator = securityTokenGenerator;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody UserData user) throws UserAlreadyExistsException {
        // Write the logic to save a user,
        // return 201 status if user is saved else 500 status
        //return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
        try {
            // Attempt to save the user
            UserData savedUser = userService.saveUser(user);

            // Return HTTP status 201 (CREATED) and the saved user in the response body
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (UserAlreadyExistsException e) {
            // If the user already exists, return HTTP status 500 (INTERNAL SERVER ERROR)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("User already exists"); // Customize the error message as needed
        }

    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserData user) throws InvalidCredentialsException
    {
        // Generate the token on login,
        // return 200 status if user is saved else 500 status

        UserData retrievedUser = userService.getUserByUserIdAndPassword(user.getEmail(),user.getPassword());
        if(retrievedUser==null)
        {
            throw new InvalidCredentialsException();
        }
        Map<String,String> map = securityTokenGenerator.generateToken(user);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
}
