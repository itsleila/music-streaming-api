package br.edu.Infnet.MusicStreaming.antifraud.entity;

import java.util.Optional;

import br.edu.Infnet.MusicStreaming.payment.entity.PaymentTransaction;

public abstract class AbstractTransactionRule implements TransactionRule {
  private TransactionRule next;

  public void setNext(TransactionRule next) {
    this.next = next;
  }

  protected Optional<String> next(PaymentTransaction transaction) {
    if (next == null) {
      return Optional.empty();
    }

    return next.validate(transaction);
  }
}
