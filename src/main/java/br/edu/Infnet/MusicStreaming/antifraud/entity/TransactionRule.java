package br.edu.Infnet.MusicStreaming.antifraud.entity;

import java.util.Optional;

import br.edu.Infnet.MusicStreaming.payment.entity.PaymentTransaction;

public interface TransactionRule {
  Optional<String> validate(PaymentTransaction transaction);
}
