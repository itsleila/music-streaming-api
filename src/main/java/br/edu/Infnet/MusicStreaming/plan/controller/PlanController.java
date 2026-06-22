package br.edu.Infnet.MusicStreaming.plan.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.Infnet.MusicStreaming.plan.dto.PlanResponseDTO;
import br.edu.Infnet.MusicStreaming.plan.service.PlanService;

@RestController
@RequestMapping("/plans")
public class PlanController {

  private final PlanService planService;

  public PlanController(PlanService planService) {
    this.planService = planService;
  }

  @GetMapping
  public ResponseEntity<List<PlanResponseDTO>> getPlans() {
    return ResponseEntity.ok(planService.getAllPlans());
  }
}
