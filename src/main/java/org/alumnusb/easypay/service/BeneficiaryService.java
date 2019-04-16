package org.alumnusb.easypay.service;

import lombok.extern.slf4j.Slf4j;
import org.alumnusb.easypay.exception.ResourceConflictException;
import org.alumnusb.easypay.model.Beneficiary;
import org.alumnusb.easypay.repository.BeneficiaryRepository;
import org.alumnusb.easypay.repository.BeneficiaryTypeRepository;
import org.alumnusb.easypay.request.CreateBeneficiary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class BeneficiaryService {

    @Autowired
    private BeneficiaryRepository repository;

    @Autowired
    private BeneficiaryTypeRepository typeRepository;

    public Beneficiary create(CreateBeneficiary request) {
        Beneficiary beneficiary = Beneficiary.builder()
                .type(request.getType())
                .upholdEmail(request.getUpholdEmail())
                .upholdUsername(request.getUpholdUsername())
                .active(Boolean.TRUE)
                .build();

        try {
            return repository.save(beneficiary);
        } catch (DataIntegrityViolationException ex) {
            throw new ResourceConflictException("Beneficiary with username/email already exists");
        }
    }

    public Beneficiary getById(Long id) {
        return repository.getById(id).orElseThrow(() -> new NoSuchElementException("Beneficiary not found"));
    }

//    public List<BeneficiaryType> getAllTypes() {
//        return typeRepository.getAll();
//    }
}
