package org.alumnusb.easypay.service;

import lombok.extern.slf4j.Slf4j;
import org.alumnusb.easypay.model.Beneficiary;
import org.alumnusb.easypay.model.PaymentList;
import org.alumnusb.easypay.model.PaymentListBeneficiary;
import org.alumnusb.easypay.repository.BeneficiaryRepository;
import org.alumnusb.easypay.repository.PaymentListBeneficiaryRepository;
import org.alumnusb.easypay.repository.PaymentListRepository;
import org.alumnusb.easypay.request.UpdateBeneficiaryPaymentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class PaymentListBeneficiaryService {

    @Autowired
    private PaymentListRepository paymentListRepository;

    @Autowired
    private PaymentListBeneficiaryRepository paymentListBeneficiaryRepository;

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    public PaymentListBeneficiary create(UpdateBeneficiaryPaymentList request) {

        PaymentList paymentList = paymentListRepository.getById(request.getPaymentListId())
                .orElseThrow(() -> new NoSuchElementException("Payment List not found"));

        Beneficiary beneficiary = beneficiaryRepository.getById(request.getBeneficiaryId())
                .orElseThrow(() -> new NoSuchElementException("Beneficiary not found"));

        return paymentListBeneficiaryRepository.save(
                PaymentListBeneficiary.builder()
                        .beneficiary(beneficiary)
                        .paymentList(paymentList)
                        .amount(request.getAmount())
                        .build()
        );
    }

    public void deleteOnList(Long paymentListBeneficiaryId){
        PaymentListBeneficiary paymentListBeneficiary = paymentListBeneficiaryRepository.getById(paymentListBeneficiaryId)
                .orElseThrow(() -> new NoSuchElementException("Payment List not found"));

        paymentListBeneficiaryRepository.delete(paymentListBeneficiary);
    }

    public Page<PaymentListBeneficiary> getAllByPaymentList(Long paymentListId, Pageable pageable){
        PaymentList paymentList = paymentListRepository.getById(paymentListId)
                .orElseThrow(() -> new NoSuchElementException("Payment List not found"));

        return paymentListBeneficiaryRepository.getAllByPaymentList(paymentList, pageable);
    }
}
