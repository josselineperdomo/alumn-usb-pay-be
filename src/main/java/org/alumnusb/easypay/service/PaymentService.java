package org.alumnusb.easypay.service;

import lombok.extern.slf4j.Slf4j;
import org.alumnusb.easypay.model.Beneficiary;
import org.alumnusb.easypay.model.Payment;
import org.alumnusb.easypay.repository.PaymentRepository;
import org.alumnusb.easypay.request.CreatePayment;
import org.alumnusb.easypay.uphold.UpholdClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UpholdClient upholdClient;

    public Payment create(CreatePayment request) {
        String beneficiary = Optional.ofNullable(request.getBeneficiaryUsername())
                .orElse(request.getBeneficiaryEmail());

        if(beneficiary == null){
            throw new IllegalArgumentException("Username or email of the beneficiary should be provided");
        }
        return paymentRepository.save(Payment.builder()
                .beneficiary(
                        Beneficiary.builder()
                                .upholdEmail(request.getBeneficiaryEmail())
                                .upholdUsername(request.getBeneficiaryUsername())
                        .build()
                ).build());
    }

    public void callbackFromOAuth(String code, String state){
        upholdClient.saveCredentials(code, state);
    }
}
