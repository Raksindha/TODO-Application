package com.niit.UserAuthentication;

import com.niit.UserAuthentication.domain.UserData;
import com.niit.UserAuthentication.exception.InvalidCredentialsException;
import com.niit.UserAuthentication.exception.UserAlreadyExistsException;
import com.niit.UserAuthentication.repository.UserRepository;
import com.niit.UserAuthentication.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveUser_Success() throws UserAlreadyExistsException {
        UserData user = new UserData("test@example.com", "password");

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(java.util.Optional.empty());
        Mockito.when(userRepository.save(Mockito.any(UserData.class))).thenReturn(user);

        UserData savedUser = userService.saveUser(user);

        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals("test@example.com", savedUser.getEmail());
        Assertions.assertEquals("password", savedUser.getPassword());
    }

    @Test
    public void testSaveUser_UserAlreadyExistsException() {
        UserData user = new UserData("test@example.com", "password");

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(java.util.Optional.of(user));

        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.saveUser(user));
    }

    @Test
    public void testGetUserByUserIdAndPassword_Success() throws InvalidCredentialsException {
        UserData user = new UserData("test@example.com", "password");

        Mockito.when(userRepository.findByEmailAndPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(user);

        UserData loggedInUser = userService.getUserByUserIdAndPassword("test@example.com", "password");

        Assertions.assertNotNull(loggedInUser);
        Assertions.assertEquals("test@example.com", loggedInUser.getEmail());
        Assertions.assertEquals("password", loggedInUser.getPassword());
    }

    @Test
    public void testGetUserByUserIdAndPassword_InvalidCredentialsException() {
        Mockito.when(userRepository.findByEmailAndPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(null);

        Assertions.assertThrows(InvalidCredentialsException.class, () -> userService.getUserByUserIdAndPassword("test@example.com", "password"));
    }
}
