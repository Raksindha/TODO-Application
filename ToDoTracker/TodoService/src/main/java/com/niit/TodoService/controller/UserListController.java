package com.niit.TodoService.controller;

import com.niit.TodoService.domain.TodoList;
import com.niit.TodoService.domain.UserData;
import com.niit.TodoService.exception.ListNotFoundException;
import com.niit.TodoService.exception.UserAlreadyExistsException;
import com.niit.TodoService.exception.UserNotFoundException;
import com.niit.TodoService.exception.listAlreadyExistsException;
import com.niit.TodoService.service.UserListService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@ImportAutoConfiguration({FeignAutoConfiguration.class})
@RestController
@RequestMapping("api/user/")
public class UserListController {

    private UserListService userListService;

    private ResponseEntity responseEntity;

    @Autowired
    public UserListController(UserListService userListService) {
        this.userListService = userListService;
    }


     @PostMapping("register")
     public ResponseEntity<?> registerUser(@RequestBody UserData user) throws UserAlreadyExistsException
     {
         try
         {
             responseEntity =  new ResponseEntity<>(userListService.registerUser(user), HttpStatus.OK);
             System.out.println(responseEntity);
         }
         catch(UserAlreadyExistsException e)
         {
             responseEntity =new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
             throw new UserAlreadyExistsException();
         }
         return responseEntity;
     }


    @PostMapping("userdata/todolist")
    public ResponseEntity<?> saveTodoList(@RequestBody TodoList todoList, HttpServletRequest request) {
        try {
            String email = extractUserIdFromToken(request);
            return new ResponseEntity<>(userListService.saveTodoList(todoList, email), HttpStatus.CREATED);
        } catch (listAlreadyExistsException e) {
            return new ResponseEntity<>("TodoList with the provided Id already exists", HttpStatus.CONFLICT);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("userdata/{todoListId}")
    public ResponseEntity<?> updateTodoList(@PathVariable String todoListId, @RequestBody TodoList updatedTask, HttpServletRequest request) {
        try {
            String email = extractUserIdFromToken(request);
            UserData updatedUserData = userListService.updateTodoList(todoListId, updatedTask, email);
            return new ResponseEntity<>(updatedUserData, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } catch (ListNotFoundException e) {
            return new ResponseEntity<>("TodoList not found", HttpStatus.NOT_FOUND);
        } catch (listAlreadyExistsException e) {
            return new ResponseEntity<>("TodoList with the provided Id already exists", HttpStatus.CONFLICT);
        }
    }


    @DeleteMapping("userdata/{todoListId}")
    public ResponseEntity<?> deleteTodoList(@PathVariable String todoListId, HttpServletRequest request) {
        try {
            String email = extractUserIdFromToken(request);
            userListService.deleteTodoList(todoListId, email);
            // Return a JSON response with a success message
            return ResponseEntity.ok().body(Map.of("message", "TodoList removed successfully"));
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } catch (ListNotFoundException e) {
            return new ResponseEntity<>("TodoList not found", HttpStatus.NOT_FOUND);
        }
    }



    @GetMapping("userdata/getAllTodoList")
    public ResponseEntity<?> getAllTodoList(HttpServletRequest request) {
        try
        {
            String userId = extractUserIdFromToken(request);
            List<TodoList> users = userListService.getAllTodoList(userId);
            return ResponseEntity.ok(users);
        }
        catch (UserNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authorized");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }



    @GetMapping("userdata/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) throws UserNotFoundException {
        try {
            UserData user = userListService.findByEmail(email);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                throw new UserNotFoundException();
            }
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        }
    }
    @GetMapping("userdata/getAllUser")
    public ResponseEntity<?>getAllUsers()throws UserNotFoundException{
        try{
            responseEntity = new ResponseEntity(userListService.getAllUserDetails(),HttpStatus.OK);
        }
        catch(UserNotFoundException e){
            responseEntity = new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
            throw new UserNotFoundException();
        }
        return responseEntity;
    }
    private String extractUserIdFromToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        Claims claims = Jwts.parser().setSigningKey("mysecret").parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
    }















