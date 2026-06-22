package br.edu.Infnet.MusicStreaming.payment.dto;

import java.math.BigDecimal;

public record PaymentTransactionRequestDTO(Long userId,
    Long paymentMethodId,
    Long planId,
    String merchant,
    BigDecimal amount) {
}