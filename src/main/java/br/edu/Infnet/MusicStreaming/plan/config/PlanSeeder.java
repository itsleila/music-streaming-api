package br.edu.Infnet.MusicStreaming.plan.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.edu.Infnet.MusicStreaming.plan.entity.Plan;
import br.edu.Infnet.MusicStreaming.plan.entity.PlanType;
import br.edu.Infnet.MusicStreaming.plan.repository.PlanRepository;

@Component
public class PlanSeeder implements CommandLineRunner {
  public static final BigDecimal PREMIUM_PRICE = new BigDecimal("19.99");
  public static final BigDecimal STUDENT_PRICE = new BigDecimal("9.99");

  private final PlanRepository planRepository;

  public PlanSeeder(PlanRepository planRepository) {
    this.planRepository = planRepository;
  }

  @Override
  public void run(String... args) throws Exception {

    if (planRepository.findByType(PlanType.PREMIUM).isEmpty()) {
      Plan premiumPlan = new Plan();
      premiumPlan.setType(PlanType.PREMIUM);
      premiumPlan.setMonthlyPrice(PREMIUM_PRICE);
      premiumPlan.setDescription("Access to all features without ads");
      planRepository.save(premiumPlan);
    }

    if (planRepository.findByType(PlanType.STUDENT).isEmpty()) {
      Plan studentPlan = new Plan();
      studentPlan.setType(PlanType.STUDENT);
      studentPlan.setMonthlyPrice(STUDENT_PRICE);
      studentPlan.setDescription(
          "Includes all premium features at a discounted price");
      planRepository.save(studentPlan);
    }

  }

}
