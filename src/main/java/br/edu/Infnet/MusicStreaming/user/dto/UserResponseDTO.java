package br.edu.Infnet.MusicStreaming.user.dto;

import br.edu.Infnet.MusicStreaming.subscription.dto.SubscriptionSummaryDTO;

public record UserResponseDTO(
        Long id,
        String username,
        String email,
        SubscriptionSummaryDTO subscription) {
}
