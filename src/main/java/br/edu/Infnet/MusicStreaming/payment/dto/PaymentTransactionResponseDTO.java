package br.edu.Infnet.MusicStreaming.payment.dto;

import java.util.List;

public record PaymentTransactionResponseDTO(Long id,
    String status,
    List<String> violations) {
}
