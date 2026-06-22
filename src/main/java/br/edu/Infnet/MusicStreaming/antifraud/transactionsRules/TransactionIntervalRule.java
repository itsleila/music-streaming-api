package br.edu.Infnet.MusicStreaming.antifraud.transactionsRules;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.edu.Infnet.MusicStreaming.antifraud.entity.AbstractTransactionRule;
import br.edu.Infnet.MusicStreaming.payment.entity.PaymentTransaction;
import br.edu.Infnet.MusicStreaming.payment.repository.PaymentTransactionRepository;

@Component
public class TransactionIntervalRule extends AbstractTransactionRule {

  private final PaymentTransactionRepository repository;

  public TransactionIntervalRule(PaymentTransactionRepository repository) {
    this.repository = repository;
  }

  @Override
  public Optional<String> validate(PaymentTransaction transaction) {

    LocalDateTime limit = transaction.getTransactionDate().minusMinutes(2);

    long count = repository.findByUserAndTransactionDateAfter(transaction.getUser(), limit).size();

    if (count >= 3) {
      return Optional.of("alta-frequencia-pequeno-intervalo");
    }

    return next(transaction);
  }
}