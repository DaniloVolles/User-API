package br.com.danilovolles.userapi.service;

import br.com.danilovolles.userapi.dto.ApiResponseDTO;
import br.com.danilovolles.userapi.dto.UserInputDTO;
import br.com.danilovolles.userapi.exception.UserAlreadyExistsException;
import br.com.danilovolles.userapi.exception.UserNotFoundException;
import br.com.danilovolles.userapi.exception.UserServiceLogicException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    ResponseEntity<ApiResponseDTO<?>> registerUser(UserInputDTO newUserData)
        throws UserAlreadyExistsException, UserServiceLogicException;

    ResponseEntity<ApiResponseDTO<?>> getAllUsers()
        throws UserServiceLogicException;

    ResponseEntity<ApiResponseDTO<?>> getUserById(Long id)
        throws UserServiceLogicException, UserNotFoundException;

    ResponseEntity<ApiResponseDTO<?>> updateUser(UserInputDTO updateUserData, Long id)
        throws UserNotFoundException, UserServiceLogicException;

    ResponseEntity<ApiResponseDTO<?>> deleteUser(Long id)
        throws UserServiceLogicException, UserNotFoundException;

}
