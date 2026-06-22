package br.edu.Infnet.MusicStreaming.subscription.dto;

import java.time.LocalDate;

import br.edu.Infnet.MusicStreaming.plan.entity.PlanType;

public record SubscriptionSummaryDTO(
    PlanType planType,
    LocalDate endDate,
    boolean active) {
}
