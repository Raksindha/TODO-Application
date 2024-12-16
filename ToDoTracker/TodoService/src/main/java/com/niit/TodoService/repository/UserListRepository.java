package com.niit.TodoService.repository;

import com.niit.TodoService.domain.UserData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserListRepository extends MongoRepository<UserData, String> {
    UserData findByEmail(String email) ;


}
