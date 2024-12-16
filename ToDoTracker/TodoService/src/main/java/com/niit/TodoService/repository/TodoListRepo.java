package com.niit.TodoService.repository;

import com.niit.TodoService.domain.TodoList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListRepo extends MongoRepository<TodoList,String> {
}
