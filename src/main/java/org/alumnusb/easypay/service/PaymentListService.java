package org.alumnusb.easypay.service;

import lombok.extern.slf4j.Slf4j;
import org.alumnusb.easypay.model.PaymentList;
import org.alumnusb.easypay.model.PaymentListBeneficiary;
import org.alumnusb.easypay.repository.BeneficiaryRepository;
import org.alumnusb.easypay.repository.PaymentListBeneficiaryRepository;
import org.alumnusb.easypay.repository.PaymentListRepository;
import org.alumnusb.easypay.request.CreatePaymentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentListService {

    @Autowired
    private PaymentListRepository paymentListRepository;

    @Autowired
    private PaymentListBeneficiaryRepository paymentListBeneficiaryRepository;

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    public PaymentList create(CreatePaymentList request) {

        PaymentList paymentList = paymentListRepository.save(
                PaymentList.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build());

        request.getBeneficiaries().forEach(
                beneficiary -> paymentListBeneficiaryRepository.save(
                        PaymentListBeneficiary.builder()
                                .amount(request.getAmount())
                                .paymentList(paymentList)
                                .beneficiary(
                                        beneficiaryRepository.getById(beneficiary).get())
                                .build()
                )
        );

        return paymentList;
    }

    public Page<PaymentList> getAll(Pageable pageable) {
        return paymentListRepository.findAll(pageable);
    }
}

