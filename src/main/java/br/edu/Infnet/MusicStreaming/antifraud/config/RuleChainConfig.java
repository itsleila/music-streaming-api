package br.edu.Infnet.MusicStreaming.antifraud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.edu.Infnet.MusicStreaming.antifraud.entity.TransactionRule;
import br.edu.Infnet.MusicStreaming.antifraud.transactionsRules.ActiveCardRule;
import br.edu.Infnet.MusicStreaming.antifraud.transactionsRules.DuplicateTransactionRule;
import br.edu.Infnet.MusicStreaming.antifraud.transactionsRules.TransactionIntervalRule;
import org.springframework.context.annotation.Primary;

@Configuration
public class RuleChainConfig {

    @Bean
    @Primary
  public TransactionRule transactionRuleChain(ActiveCardRule activeCardRule, TransactionIntervalRule intervalRule,
      DuplicateTransactionRule duplicateRule) {

    activeCardRule.setNext(intervalRule);
    intervalRule.setNext(duplicateRule);
    return activeCardRule;
  }
}
