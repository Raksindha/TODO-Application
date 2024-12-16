package com.niit.TodoService.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TodoList {
    @Id
    private String todoListId;
    private String task;
    private String category;
    private String dueDate;
    private String priority;

    public TodoList() {
    }

    public TodoList(String task, String category, String dueDate, String priority) {
        this.task = task;
        this.category = category;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public String getTodoListId() {
        return todoListId;
    }

    public void setTodoListId(String todoListId) {
        this.todoListId = todoListId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "TodoList{" +
                "todoListId='" + todoListId + '\'' +
                ", task='" + task + '\'' +
                ", category='" + category + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}
