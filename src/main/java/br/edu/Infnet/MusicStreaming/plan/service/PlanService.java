package br.edu.Infnet.MusicStreaming.plan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.Infnet.MusicStreaming.plan.dto.PlanResponseDTO;
import br.edu.Infnet.MusicStreaming.plan.repository.PlanRepository;

@Service
public class PlanService {

  private final PlanRepository planRepository;

  public PlanService(PlanRepository planRepository) {
    this.planRepository = planRepository;
  }

  public List<PlanResponseDTO> getAllPlans() {
    return planRepository.findAll().stream().map(plan -> new PlanResponseDTO(
        plan.getId(),
        plan.getType(),
        plan.getMonthlyPrice(),
        plan.getDescription())).toList();
  }
}
