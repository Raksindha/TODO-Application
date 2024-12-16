package com.niit.TodoService.proxy;



import com.niit.TodoService.domain.UserData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="UserAuthentication",url="localhost:8083")
public interface UserProxy {

    @PostMapping("/api/auth/save")
    public ResponseEntity<?> addUser(@RequestBody UserData user);
}