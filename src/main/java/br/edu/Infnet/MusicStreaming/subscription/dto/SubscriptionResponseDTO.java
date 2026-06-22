package br.edu.Infnet.MusicStreaming.subscription.dto;

import java.time.LocalDate;

import br.edu.Infnet.MusicStreaming.plan.entity.PlanType;

public record SubscriptionResponseDTO(Long id,
    PlanType planType,
    LocalDate startDate,
    LocalDate endDate,
    boolean active) {

}
