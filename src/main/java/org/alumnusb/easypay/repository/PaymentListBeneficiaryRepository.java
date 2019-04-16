package org.alumnusb.easypay.repository;

import org.alumnusb.easypay.model.PaymentList;
import org.alumnusb.easypay.model.PaymentListBeneficiary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PaymentListBeneficiaryRepository extends CrudRepository<PaymentListBeneficiary, Long> {
    Optional<PaymentListBeneficiary> getById(Long id);
    Page<PaymentListBeneficiary> getAllByPaymentList(PaymentList paymentList, Pageable pageable);
}
