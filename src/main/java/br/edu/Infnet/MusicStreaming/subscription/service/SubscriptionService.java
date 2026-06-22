package br.edu.Infnet.MusicStreaming.subscription.service;

import java.time.LocalDate;

import br.edu.Infnet.MusicStreaming.payment.entity.PaymentMethod;
import br.edu.Infnet.MusicStreaming.payment.entity.PaymentTransaction;
import br.edu.Infnet.MusicStreaming.payment.entity.TransactionStatus;
import br.edu.Infnet.MusicStreaming.payment.repository.PaymentMethodRepository;
import br.edu.Infnet.MusicStreaming.payment.service.PaymentTransactionService;
import br.edu.Infnet.MusicStreaming.plan.repository.PlanRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import br.edu.Infnet.MusicStreaming.plan.entity.Plan;
import br.edu.Infnet.MusicStreaming.plan.entity.PlanType;
import br.edu.Infnet.MusicStreaming.subscription.dto.SubscriptionResponseDTO;
import br.edu.Infnet.MusicStreaming.subscription.dto.SubscriptionSummaryDTO;
import br.edu.Infnet.MusicStreaming.subscription.entity.Subscription;
import br.edu.Infnet.MusicStreaming.subscription.repository.SubscriptionRepository;
import br.edu.Infnet.MusicStreaming.user.entity.User;

@Service
public class SubscriptionService {
  private final SubscriptionRepository subscriptionRepository;
  private final PaymentTransactionService paymentTransactionService;
  private final PaymentMethodRepository paymentMethodRepository;
  private final PlanRepository planRepository;

  public SubscriptionService(SubscriptionRepository subscriptionRepository,PaymentTransactionService paymentTransactionService, PaymentMethodRepository paymentMethodRepository, PlanRepository planRepository) {
    this.subscriptionRepository = subscriptionRepository;
    this.paymentTransactionService = paymentTransactionService;
    this.paymentMethodRepository = paymentMethodRepository;
    this.planRepository = planRepository;
  }

  public SubscriptionResponseDTO toDTO(Subscription subscription) {
      return new SubscriptionResponseDTO(subscription.getId(),
              subscription.getPlan().getType(),
              subscription.getStartDate(),
              subscription.getEndDate(),
              subscription.isActive());
  }


  public SubscriptionSummaryDTO toSummaryDTO(Subscription subscription) {
    return new SubscriptionSummaryDTO(
        subscription.getPlan().getType(),
        subscription.getEndDate(),
        subscription.isActive());
  }

  private  Subscription deactivateSubscription(Subscription subscription) {
    subscription.setEndDate(LocalDate.now().minusDays(1));
    return subscriptionRepository.save(subscription);
  }

  public SubscriptionSummaryDTO getCurrentPlan(User user) {
    Subscription activeSubscription = getActiveSubscription(user);
    if (activeSubscription == null) {
      return new SubscriptionSummaryDTO(
          PlanType.FREE,
          null,
          true);
    }

    return toSummaryDTO(activeSubscription);
  }

  public Subscription getActiveSubscription(User user) {
    return subscriptionRepository
        .findAllByUserOrderByStartDateDesc(user)
        .stream()
        .filter(Subscription::isActive)
        .findFirst()
        .orElse(null);
  }

  private Subscription createSubscription(User user, Plan plan) {
      Subscription subscription = new Subscription();
      subscription.setUser(user);
      subscription.setPlan(plan);
      subscription.setStartDate(LocalDate.now());
      subscription.setEndDate(LocalDate.now().plusMonths(1));

      return subscriptionRepository.save(subscription);
  }
  private void authorizePayment(User user,Plan plan, PaymentMethod paymentMethod) {
      PaymentTransaction transaction = paymentTransactionService.processTransaction(user, plan, paymentMethod);

        if (transaction.getStatus() != TransactionStatus.AUTHORIZED) {
            throw new IllegalStateException("Transaction not authorized");
        }
  }

  @Transactional
  public Subscription subscribe(User user, Long planId,Long paymentMethodId) {
      if (getActiveSubscription(user) != null) {
          throw new IllegalStateException("User already has an active subscription");
      }

      Plan plan = planRepository.findById(planId).orElseThrow();
      PaymentMethod paymentMethod = paymentMethodRepository.findByIdAndUserId(paymentMethodId, user.getId()).orElseThrow(() -> new IllegalStateException("Payment method not found for user"));
      authorizePayment(user, plan, paymentMethod);
      return createSubscription(user, plan);
  }

  @Transactional
  public Subscription changePlan(User user, Long newPlanId,Long paymentMethodId) {
      Subscription current =  getActiveSubscription(user);
      if (current == null){
          throw new IllegalStateException("No active subscription");
      }
      if (current.getPlan().getId().equals(newPlanId)) {
          throw new IllegalStateException("User is already subscribed to this plan");
      }
      Plan newPlan = planRepository.findById(newPlanId).orElseThrow();
      PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId).orElseThrow();

      authorizePayment(user, newPlan, paymentMethod);
      deactivateSubscription(current);
      return createSubscription(user, newPlan);
  }

  @Transactional
  public void cancelSubscription(User user) {
      Subscription active = getActiveSubscription(user);
      if (active == null) {
          throw new IllegalStateException("User has no active subscription");
      }
      deactivateSubscription(active);
  }





}
