package org.alumnusb.easypay.uphold.repository;

import org.alumnusb.easypay.uphold.model.UpholdCredentialsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpholdCredentialsRepository extends CrudRepository<UpholdCredentialsEntity, Short> {

}