package br.edu.Infnet.MusicStreaming.payment.dto;

import jakarta.validation.constraints.*;

public record PaymentMethodRequestDTO(

    @NotBlank @Size(min = 13, max = 19) String cardNumber,
    @NotBlank @Size(max = 100) String holderName,
    @NotNull @Min(1) @Max(12) Integer expirationMonth,
    @NotNull @Min(2025) Integer expirationYear) {
}
