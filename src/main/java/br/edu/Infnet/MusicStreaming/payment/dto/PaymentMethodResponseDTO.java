package br.edu.Infnet.MusicStreaming.payment.dto;

public record PaymentMethodResponseDTO(
    Long id, String holderName, String lastFourDigits, Integer expirationMonth, Integer expirationYear, boolean active,
    boolean expired) {
}