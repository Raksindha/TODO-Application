package com.niit.TodoService.service;

import com.niit.TodoService.domain.TodoList;
import com.niit.TodoService.domain.UserData;
import com.niit.TodoService.exception.ListNotFoundException;
import com.niit.TodoService.exception.UserAlreadyExistsException;
import com.niit.TodoService.exception.UserNotFoundException;
import com.niit.TodoService.exception.listAlreadyExistsException;

import java.util.List;


public interface UserListService {
    UserData registerUser(UserData userData) throws UserAlreadyExistsException;
    UserData saveTodoList(TodoList todoList, String email) throws listAlreadyExistsException,UserNotFoundException ;
    UserData deleteTodoList( String todoListId,String email) throws ListNotFoundException,UserNotFoundException;
    UserData updateTodoList(String todoListId, TodoList updatedTask, String email) throws UserNotFoundException, ListNotFoundException, listAlreadyExistsException ;
    List<TodoList> getAllTodoList(String todoListId) throws UserNotFoundException;
    List<UserData> getAllUserDetails()throws UserNotFoundException;
    UserData findByEmail(String email) throws UserNotFoundException;

}