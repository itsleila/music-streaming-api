package br.edu.Infnet.MusicStreaming.antifraud.transactionsRules;

import java.util.Optional;

import br.edu.Infnet.MusicStreaming.antifraud.entity.AbstractTransactionRule;
import br.edu.Infnet.MusicStreaming.payment.entity.PaymentTransaction;
import org.springframework.stereotype.Component;

@Component
public class ActiveCardRule extends AbstractTransactionRule {
  @Override
  public Optional<String> validate(PaymentTransaction transaction) {

    if (!transaction.getPaymentMethod().isActive()) {
      return Optional.of("cartao-nao-ativo");
    }

    return next(transaction);
  }
}
