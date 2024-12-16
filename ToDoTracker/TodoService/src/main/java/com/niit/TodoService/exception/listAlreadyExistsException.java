package com.niit.TodoService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT,reason = "list Already Exists")
public class listAlreadyExistsException extends Exception {
}
