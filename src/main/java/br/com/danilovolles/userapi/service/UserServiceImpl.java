package br.com.danilovolles.userapi.service;

import br.com.danilovolles.userapi.dto.ApiResponseDTO;
import br.com.danilovolles.userapi.dto.ApiResponseStatus;
import br.com.danilovolles.userapi.dto.UserOutputDTO;
import br.com.danilovolles.userapi.dto.UserRegistrationDTO;
import br.com.danilovolles.userapi.exception.UserAlreadyExistsException;
import br.com.danilovolles.userapi.exception.UserNotFoundException;
import br.com.danilovolles.userapi.exception.UserServiceLogicException;
import br.com.danilovolles.userapi.model.User;
import br.com.danilovolles.userapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<ApiResponseDTO<?>> registerUser(UserRegistrationDTO newUserData)
            throws UserAlreadyExistsException, UserServiceLogicException {
        try {
            if (userRepository.findByEmail(newUserData.getEmail()) != null) {
                throw new UserAlreadyExistsException("User already exists with email: " + newUserData.getEmail());
            }
            if (userRepository.findByUsername(newUserData.getUsername()) != null) {
                throw new UserAlreadyExistsException("User already exists with username: " + newUserData.getUsername());
            }

            User newUser = new User(
                    newUserData.getUsername(),
                    newUserData.getEmail(),
                    newUserData.getPhoneNumber(),
                    LocalDateTime.now()
            );

            userRepository.save(newUser);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ApiResponseDTO<>(ApiResponseStatus.SUCCESS.name(), "New user registered successfully"));
        } catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsException(e.getMessage());
        } catch (Exception e){
            log.error("Failed to create new user account: " + e.getMessage());
            throw new UserServiceLogicException();
        }
    }

    @Override
    public ResponseEntity<ApiResponseDTO<?>> getAllUsers()
            throws UserServiceLogicException {
        try {
            List<User> users = userRepository.findAllByOrderByRegistrationDateDesc();

            List<UserOutputDTO> userList = new ArrayList<>();

            for (User user : users) {
                UserOutputDTO userOutput = convertUserToUserOutputDTO(user);
                userList.add(userOutput);
            }

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDTO<>(ApiResponseStatus.SUCCESS.name(), userList));
        } catch (Exception e) {
            log.error("Failed to get all users: " + e.getMessage());
            throw new UserServiceLogicException();
        }
    }

    @Override
    public ResponseEntity<ApiResponseDTO<?>> getUserById(Long id) throws UserServiceLogicException, UserNotFoundException {
        try {
            User user = userRepository
                    .findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

            UserOutputDTO userOutput = convertUserToUserOutputDTO(user);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDTO<>(ApiResponseStatus.SUCCESS.name(), userOutput));
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        } catch (Exception e) {
            log.error("Failed to get User: {}", e.getMessage());
            throw new UserServiceLogicException();
        }
    }

    @Override
    public ResponseEntity<ApiResponseDTO<?>> updateUser(UserRegistrationDTO updateUserData, Long id)
            throws UserNotFoundException, UserServiceLogicException {
        try {
            User user = userRepository
                    .findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

            user.setUsername(updateUserData.getUsername());
            user.setEmail(updateUserData.getEmail());
            user.setPhoneNumber(updateUserData.getPhoneNumber());

            userRepository.save(user);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDTO<>(ApiResponseStatus.SUCCESS.name(), "User updated successfully"));

        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        } catch (Exception e){
            log.error("Failed to update user: " + e.getMessage());
            throw new UserServiceLogicException();
        }
    }

    @Override
    public ResponseEntity<ApiResponseDTO<?>> deleteUser(Long id)
            throws UserServiceLogicException, UserNotFoundException {
        try {
            User user = userRepository
                    .findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

            userRepository.delete(user);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDTO<>(ApiResponseStatus.SUCCESS.name(), "User deleted successfully"));

        } catch (UserNotFoundException e){
            throw new UserNotFoundException(e.getMessage());
        } catch (Exception e){
            log.error("Failed to delete user: " + e.getMessage());
            throw new UserServiceLogicException();
        }
    }

    private UserOutputDTO convertUserToUserOutputDTO(User user) {
        UserOutputDTO userOutput = new UserOutputDTO();
        userOutput.setId(user.getId());
        userOutput.setUsername(user.getUsername());
        return userOutput;
    }
}
