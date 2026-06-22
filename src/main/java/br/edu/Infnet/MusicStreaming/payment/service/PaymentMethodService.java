package br.edu.Infnet.MusicStreaming.payment.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.Infnet.MusicStreaming.payment.dto.PaymentMethodRequestDTO;
import br.edu.Infnet.MusicStreaming.payment.dto.PaymentMethodResponseDTO;
import br.edu.Infnet.MusicStreaming.payment.entity.PaymentMethod;
import br.edu.Infnet.MusicStreaming.payment.repository.PaymentMethodRepository;
import br.edu.Infnet.MusicStreaming.user.entity.User;

@Service
public class PaymentMethodService {

  private final PaymentMethodRepository paymentMethodRepository;

  public PaymentMethodService(PaymentMethodRepository paymentMethodRepository) {
    this.paymentMethodRepository = paymentMethodRepository;
  }

  public PaymentMethodResponseDTO toDTO(PaymentMethod paymentMethod) {

    return new PaymentMethodResponseDTO(
        paymentMethod.getId(),
        paymentMethod.getHolderName(),
        paymentMethod.getLastFourDigits(),
        paymentMethod.getExpirationMonth(),
        paymentMethod.getExpirationYear(),
        paymentMethod.isActive(),
        paymentMethod.isExpired());
  }

  public PaymentMethod create(User user, PaymentMethodRequestDTO dto) {

    if (paymentMethodRepository.existsByUserAndCardNumber(user, dto.cardNumber())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Card already registered");
    }

    PaymentMethod paymentMethod = PaymentMethod.builder()
        .cardNumber(dto.cardNumber())
        .holderName(dto.holderName())
        .expirationMonth(dto.expirationMonth())
        .expirationYear(dto.expirationYear())
        .active(true)
        .user(user)
        .build();

    return paymentMethodRepository.save(paymentMethod);
  }

  public List<PaymentMethodResponseDTO> getAllByUser(User user) {
    return paymentMethodRepository.findAllByUser(user).stream().map(this::toDTO).toList();
  }

  public PaymentMethod getById(Long paymentMethodId, User user) {
    return paymentMethodRepository.findByIdAndUser(paymentMethodId, user)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment method not found"));
  }

  public PaymentMethodResponseDTO getDTOById(Long paymentMethodId, User user) {
    return toDTO(getById(paymentMethodId, user));
  }

  public PaymentMethodResponseDTO deactivate(Long paymentMethodId, User user) {
    PaymentMethod paymentMethod = getById(paymentMethodId, user);
    paymentMethod.setActive(false);

    return toDTO(paymentMethodRepository.save(paymentMethod));
  }

  public PaymentMethodResponseDTO activate(Long paymentMethodId, User user) {
    PaymentMethod paymentMethod = getById(paymentMethodId, user);
    paymentMethod.setActive(true);
    return toDTO(paymentMethodRepository.save(paymentMethod));
  }
}
