package br.com.danilovolles.userapi.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Username must be 3 characters at minimum")
    @Size(max = 20, message = "Username must be 20 characters or less")
    private String username;

    @Email(message = "Invalid e-mail format")
    @NotBlank(message = "Email id required")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(min = 5, max = 5, message = "Phone must have exactly 5 numbers")
    @Pattern(regexp = "^[0-9]*$", message = "Phone number must contain only numerical digits")
    private String phoneNumber;
}
