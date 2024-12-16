package com.niit.TodoService.service;

import com.niit.TodoService.domain.TodoList;
import com.niit.TodoService.domain.UserData;
import com.niit.TodoService.exception.ListNotFoundException;
import com.niit.TodoService.exception.UserAlreadyExistsException;
import com.niit.TodoService.exception.UserNotFoundException;
import com.niit.TodoService.exception.listAlreadyExistsException;
import com.niit.TodoService.proxy.UserProxy;
import com.niit.TodoService.repository.TodoListRepo;
import com.niit.TodoService.repository.UserListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class UserListServiceImpl implements UserListService {

    private UserListRepository userListRepository;

    private UserProxy userProxy;

    @Autowired
    public UserListServiceImpl(UserListRepository userListRepository, UserProxy userProxy) {
        this.userListRepository = userListRepository;
        this.userProxy = userProxy;
    }
    @Override
    public UserData registerUser(UserData userData) throws UserAlreadyExistsException {
            // Register a new user
            if(userListRepository.findById(userData.getEmail()).isPresent()){
                throw new UserAlreadyExistsException();
            }
            UserData savedUser=userListRepository.save(userData);
        System.out.println(savedUser);
            if(!(savedUser.getEmail().isEmpty())){
                ResponseEntity<?> r=userProxy.addUser(userData);
                System.out.println(r.getBody());
            }
            return savedUser;
        }

    @Override
    public UserData saveTodoList(TodoList todoList, String email) throws listAlreadyExistsException, UserNotFoundException {
        // Save the tracks to the play list of user.
        if (userListRepository.findById(email).isEmpty()) {
            throw new UserNotFoundException();
        }
        UserData user = userListRepository.findById(email).get();
        if (user.getTodoLists() == null) {
            user.setTodoLists(Arrays.asList(todoList));
        } else {
            List<TodoList> todoLists = user.getTodoLists();
            for (TodoList todolist1 : todoLists) {
                // Check if getTodoListId() is not null
                if (todolist1.getTodoListId() != null && todolist1.getTodoListId().equals(todoList.getTodoListId())) {
                    throw new listAlreadyExistsException();
                }
            }
            todoLists.add(todoList);
            user.setTodoLists(todoLists);
        }
        return userListRepository.save(user);
    }

    @Override
    public UserData deleteTodoList(String todoListId, String email) throws ListNotFoundException, UserNotFoundException {
        Optional<UserData> optionalUser = userListRepository.findById(email);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        UserData user = optionalUser.get();
        List<TodoList> todoLists = user.getTodoLists();

        boolean removed = todoLists.removeIf(todoList -> {
            String id = todoList.getTodoListId();
            return id != null && id.equals(todoListId);
        });

        if (!removed) {
            throw new ListNotFoundException();
        }

        user.setTodoLists(todoLists);
        return userListRepository.save(user);
    }




    @Override
    public UserData updateTodoList(String todoListId, TodoList updatedTask, String email) throws UserNotFoundException, ListNotFoundException, listAlreadyExistsException {
        Optional<UserData> user = userListRepository.findById(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        UserData userData = user.get();
        List<TodoList> todoLists = userData.getTodoLists();
        TodoList existingTodoList = todoLists.stream()
                .filter(todo -> todo.getTodoListId().equals(todoListId))
                .findFirst()
                .orElseThrow(ListNotFoundException::new);
        todoLists.remove(existingTodoList);
        updatedTask.setTodoListId(todoListId);
        todoLists.add(updatedTask);
        userData.setTodoLists(todoLists);
        return userListRepository.save(userData);
    }


    @Override
    public List<TodoList> getAllTodoList(String userId)throws UserNotFoundException{
        // Get all the tracks for a specific user
        UserData user = userListRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());

        UserData anotherUser = userListRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());
        return user.getTodoLists();

    }


    @Override
    public UserData findByEmail(String email) throws UserNotFoundException {
        return userListRepository.findByEmail(email);
    }

    @Override
    public List<UserData> getAllUserDetails()throws UserNotFoundException{
        return userListRepository.findAll();
    }

}