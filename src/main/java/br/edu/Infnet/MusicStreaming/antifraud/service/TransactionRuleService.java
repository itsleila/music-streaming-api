package br.edu.Infnet.MusicStreaming.antifraud.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.Infnet.MusicStreaming.antifraud.entity.TransactionRule;
import br.edu.Infnet.MusicStreaming.payment.entity.PaymentTransaction;

@Service
public class TransactionRuleService {

  private final TransactionRule ruleChain;

  public TransactionRuleService(TransactionRule ruleChain) {
    this.ruleChain = ruleChain;
  }

  public Optional<String> validate(PaymentTransaction transaction) {

    return ruleChain.validate(transaction);
  }
}
