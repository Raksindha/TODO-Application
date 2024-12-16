package com.niit.UserAuthentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.UserAuthentication.controller.UserController;
import com.niit.UserAuthentication.domain.UserData;
import com.niit.UserAuthentication.exception.InvalidCredentialsException;
import com.niit.UserAuthentication.exception.UserAlreadyExistsException;
import com.niit.UserAuthentication.security.SecurityTokenGenerator;
import com.niit.UserAuthentication.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private SecurityTokenGenerator securityTokenGenerator;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testSaveUser_Success() throws Exception {
        UserData user = new UserData("test@example.com", "password");

        when(userService.saveUser(any(UserData.class))).thenReturn(user);

        ResponseEntity<?> responseEntity = userController.saveUser(user);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    public void testSaveUser_UserAlreadyExists() throws Exception {
        UserData user = new UserData("test@example.com", "password");

        when(userService.saveUser(any(UserData.class))).thenThrow(new UserAlreadyExistsException());

        ResponseEntity<?> responseEntity = userController.saveUser(user);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("User already exists", responseEntity.getBody());
    }

    @Test
    public void testLogin_Success() throws Exception {
        UserData user = new UserData("test@example.com", "password");
        String token = "sample.jwt.token"; // Sample token value for testing

        when(userService.getUserByUserIdAndPassword(anyString(), anyString())).thenReturn(user);
        when(securityTokenGenerator.generateToken(any(UserData.class))).thenReturn(Collections.singletonMap("token", token));

        ResponseEntity<?> responseEntity = userController.login(user);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof Map);
        Map<?, ?> responseBody = (Map<?, ?>) responseEntity.getBody();
        assertEquals(token, responseBody.get("token"));
        assertEquals("Authentication Successful", responseBody.get("message"));
    }

    @Test
    public void testLogin_InvalidCredentials() throws Exception {
        UserData user = new UserData("test@example.com", "password");

        when(userService.getUserByUserIdAndPassword(anyString(), anyString())).thenThrow(new InvalidCredentialsException());

        assertThrows(InvalidCredentialsException.class, () -> userController.login(user));
    }
}
