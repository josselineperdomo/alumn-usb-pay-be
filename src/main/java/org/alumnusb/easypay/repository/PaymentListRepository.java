package org.alumnusb.easypay.repository;

import org.alumnusb.easypay.model.PaymentList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentListRepository extends CrudRepository<PaymentList, Long> {
    Optional<PaymentList> getById(Long id);
    Page<PaymentList> findAll(Pageable pageable);
}