package br.com.danilovolles.userapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public record UserInputDTO (

        @NotBlank(message = "Username is required")
        @Size(min = 3, message = "Username must be 3 characters at minimum")
        @Size(max = 20, message = "Username must be 20 characters or less")
        String username,

        @Email(message = "Invalid e-mail format")
        @NotBlank(message = "Email id required")
        String email,

        @NotBlank(message = "Phone number is required")
        @Size(min = 5, max = 5, message = "Phone must have exactly 5 numbers")
        @Pattern(regexp = "^[0-9]*$", message = "Phone number must contain only numerical digits")
        String phoneNumber
) {}
