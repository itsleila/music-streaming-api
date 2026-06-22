package br.edu.Infnet.MusicStreaming.plan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.Infnet.MusicStreaming.plan.entity.Plan;
import br.edu.Infnet.MusicStreaming.plan.entity.PlanType;

public interface PlanRepository extends JpaRepository<Plan, Long> {
  Optional<Plan> findByType(PlanType type);
}