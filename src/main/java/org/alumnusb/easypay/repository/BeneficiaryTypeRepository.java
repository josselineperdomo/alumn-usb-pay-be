package org.alumnusb.easypay.repository;

import org.alumnusb.easypay.model.BeneficiaryType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeneficiaryTypeRepository extends CrudRepository<BeneficiaryType, Short> {
//    List<BeneficiaryType> getAll();
}