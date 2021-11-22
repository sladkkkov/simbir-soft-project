package ru.sladkkov.ChatSimbirSoft.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class BaseControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public Object userNotFoundException(UserNotFoundException ex){
        return response(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }
    @ExceptionHandler(UserBannedException.class)
    public Object userBannedException(UserBannedException ex){
        return response(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }
    @ExceptionHandler(UserAlreadyCreatedException.class)
    public Object userAlreadyCreatedException(UserAlreadyCreatedException ex){
        return response(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }
    @ExceptionHandler(RoomNotFoundException.class)
    public Object roomNotFoundException(RoomNotFoundException ex){
        return response(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }
    @ExceptionHandler(RoomListNotFoundException.class)
    public Object roomListNotFoundException(RoomListNotFoundException ex){
        return response(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }
    @ExceptionHandler(RoomAlreadyCreatedException.class)
    public Object roomAlreadyCreatedException(RoomAlreadyCreatedException ex){
        return response(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }
    @ExceptionHandler(NoAccessException.class)
    public Object noAccessException(NoAccessException ex){
        return response(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }
    @ExceptionHandler(MessageNotFoundException.class)
    public Object messageNotFoundException(MessageNotFoundException ex){
        return response(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }
    @ExceptionHandler(MessageAlreadyCreatedException.class)
    public Object messageAlreadyCreatedException(MessageAlreadyCreatedException ex){
        return response(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }
    @ExceptionHandler(LogicException.class)
    public Object logicException(LogicException ex){
        return response(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }


    private Object response(HttpStatus status, AbstractException ex){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        Map<String,String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("status", status.toString());
        return new ResponseEntity<>(body,headers,status);
    }
}
