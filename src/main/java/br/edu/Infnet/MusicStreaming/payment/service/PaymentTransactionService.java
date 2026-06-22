package br.edu.Infnet.MusicStreaming.payment.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.Infnet.MusicStreaming.antifraud.service.TransactionRuleService;
import br.edu.Infnet.MusicStreaming.payment.dto.PaymentTransactionRequestDTO;
import br.edu.Infnet.MusicStreaming.payment.dto.PaymentTransactionResponseDTO;
import br.edu.Infnet.MusicStreaming.payment.entity.PaymentMethod;
import br.edu.Infnet.MusicStreaming.payment.entity.PaymentTransaction;
import br.edu.Infnet.MusicStreaming.payment.entity.TransactionStatus;
import br.edu.Infnet.MusicStreaming.payment.repository.PaymentMethodRepository;
import br.edu.Infnet.MusicStreaming.payment.repository.PaymentTransactionRepository;
import br.edu.Infnet.MusicStreaming.plan.entity.Plan;
import br.edu.Infnet.MusicStreaming.plan.repository.PlanRepository;
import br.edu.Infnet.MusicStreaming.user.entity.User;
import br.edu.Infnet.MusicStreaming.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentTransactionService {
    private final PaymentTransactionRepository transactionRepository;
    private final TransactionRuleService transactionRuleService;

    public PaymentTransaction processTransaction( User user, Plan plan, PaymentMethod paymentMethod) {

        PaymentTransaction transaction = PaymentTransaction.builder()
                .user(user)
                .plan(plan)
                .paymentMethod(paymentMethod)
                .merchant("MusicStreaming")
                .amount(plan.getMonthlyPrice())
                .transactionDate(LocalDateTime.now())
                .build();

        String violation = transactionRuleService.validate(transaction).orElse(null);

        if (violation != null) {
            transaction.setStatus(TransactionStatus.FAILED);
        } else {
            transaction.setStatus(TransactionStatus.AUTHORIZED);
        }

        return transactionRepository.save(transaction);
    }


}