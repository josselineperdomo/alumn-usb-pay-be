package org.alumnusb.easypay.repository;

import org.alumnusb.easypay.model.Beneficiary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BeneficiaryRepository extends CrudRepository<Beneficiary, Long> {

    boolean existsByUpholdEmailOrUpholdUsername(String upholdEmail, String upholdUsername);

    Optional<Beneficiary> getById(Long id);
}