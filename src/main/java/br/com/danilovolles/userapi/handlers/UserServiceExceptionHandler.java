package br.com.danilovolles.userapi.handlers;

import br.com.danilovolles.userapi.dto.ApiResponseDTO;
import br.com.danilovolles.userapi.dto.ApiResponseStatus;
import br.com.danilovolles.userapi.exception.UserAlreadyExistsException;
import br.com.danilovolles.userapi.exception.UserNotFoundException;
import br.com.danilovolles.userapi.exception.UserServiceLogicException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.View;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class UserServiceExceptionHandler {


    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<?>> UserNotFoundExceptionHandler(UserNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDTO<>(ApiResponseStatus.FAIL.name(), exception.getMessage()));
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponseDTO<?>> UserAlreadyExistsExceptionHandler(UserAlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiResponseDTO<>(ApiResponseStatus.FAIL.name(), exception.getMessage()));
    }

    @ExceptionHandler(value = UserServiceLogicException.class)
    public ResponseEntity<ApiResponseDTO<?>> UserServiceLogicExceptionHandler(UserServiceLogicException exception) {
        return ResponseEntity
                .badRequest()
                .body(new ApiResponseDTO<>(ApiResponseStatus.FAIL.name(), exception.getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<?>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        List<String> errorMessage = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorMessage.add(fieldError.getDefaultMessage());
        });
        return ResponseEntity
                .badRequest()
                .body(new ApiResponseDTO<>(ApiResponseStatus.FAIL.name(), errorMessage.toString()));
    }
}
