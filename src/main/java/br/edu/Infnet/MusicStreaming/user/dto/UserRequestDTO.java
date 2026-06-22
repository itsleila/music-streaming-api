package br.edu.Infnet.MusicStreaming.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "Username is required") @Size(min = 3, max = 20) String username,

        @NotBlank(message = "Email is required") @Email(message = "Invalid email") String email,

        @NotBlank(message = "Password is required") @Size(min = 8, max = 60) String password

) {
}
