package org.alumnusb.easypay.repository;

import org.alumnusb.easypay.model.PaymentList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentListRepository extends CrudRepository<PaymentList, Long> {

}