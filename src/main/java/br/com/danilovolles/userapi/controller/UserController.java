package br.com.danilovolles.userapi.controller;

import br.com.danilovolles.userapi.dto.ApiResponseDTO;
import br.com.danilovolles.userapi.dto.UserInputDTO;
import br.com.danilovolles.userapi.exception.UserAlreadyExistsException;
import br.com.danilovolles.userapi.exception.UserNotFoundException;
import br.com.danilovolles.userapi.exception.UserServiceLogicException;
import br.com.danilovolles.userapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    public UserService userService;

    @PostMapping("/new")
    public ResponseEntity<ApiResponseDTO<?>> registerUser(
            @Valid @RequestBody UserInputDTO userData
    ) throws UserAlreadyExistsException, UserServiceLogicException {
        return userService.registerUser(userData);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponseDTO<?>> getAllUsers() throws UserServiceLogicException {
        return userService.getAllUsers();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponseDTO<?>> getUserById(
            @PathVariable Long id
    ) throws UserServiceLogicException, UserNotFoundException {
        return userService.getUserById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponseDTO<?>> updateUser(
            @Valid @RequestBody UserInputDTO userData,
            @PathVariable Long id
    ) throws UserNotFoundException, UserServiceLogicException {
        return userService.updateUser(userData, id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDTO<?>> deleteUser(
            @PathVariable Long id
    ) throws UserNotFoundException, UserServiceLogicException {
        return userService.deleteUser(id);
    }
}
