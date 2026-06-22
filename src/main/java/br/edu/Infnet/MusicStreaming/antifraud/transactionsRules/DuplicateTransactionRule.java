package br.edu.Infnet.MusicStreaming.antifraud.transactionsRules;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.edu.Infnet.MusicStreaming.antifraud.entity.AbstractTransactionRule;
import br.edu.Infnet.MusicStreaming.payment.entity.PaymentTransaction;
import br.edu.Infnet.MusicStreaming.payment.repository.PaymentTransactionRepository;

@Component
public class DuplicateTransactionRule extends AbstractTransactionRule {

  private final PaymentTransactionRepository repository;

  public DuplicateTransactionRule(PaymentTransactionRepository repository) {
    this.repository = repository;
  }

  @Override
  public Optional<String> validate(PaymentTransaction transaction) {

    LocalDateTime limit = transaction.getTransactionDate().minusMinutes(2);

    long count = repository.findByUserAndMerchantAndAmountAndTransactionDateAfter(transaction.getUser(),
        transaction.getMerchant(), transaction.getAmount(), limit).size();

    if (count >= 2) {
      return Optional.of("transacao-duplicada");
    }

    return next(transaction);
  }
}