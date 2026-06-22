package br.edu.Infnet.MusicStreaming.plan.dto;

import java.math.BigDecimal;

import br.edu.Infnet.MusicStreaming.plan.entity.PlanType;

public record PlanResponseDTO(
        Long id,
        PlanType type,
        BigDecimal monthlyPrice,
        String description) {
}
