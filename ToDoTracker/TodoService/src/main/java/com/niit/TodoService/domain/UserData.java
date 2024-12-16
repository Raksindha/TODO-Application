package com.niit.TodoService.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class UserData {
    @Id
    private String email;
    private String password;
    private String name;
    private List<TodoList> todoLists;

    public UserData() {
    }

    public UserData(String email, String password, String name, List<TodoList> todoLists) {
        this.email = email;
        this.password = password;
        this.name = name;

        this.todoLists = todoLists;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public List<TodoList> getTodoLists() {
        return todoLists;
    }

    public void setTodoLists(List<TodoList> todoLists) {
        this.todoLists = todoLists;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", todoLists=" + todoLists +
                '}';
    }
}
