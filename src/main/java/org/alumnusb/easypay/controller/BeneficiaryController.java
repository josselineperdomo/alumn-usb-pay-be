package org.alumnusb.easypay.controller;

import lombok.RequiredArgsConstructor;
import org.alumnusb.easypay.model.Beneficiary;
import org.alumnusb.easypay.request.CreateBeneficiary;
import org.alumnusb.easypay.service.BeneficiaryService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/beneficiaries")
public class BeneficiaryController {

    private final BeneficiaryService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Beneficiary create(@Validated @RequestBody CreateBeneficiary request) {
        return service.create(request);
    }

    @GetMapping(value = "/{beneficiaryId}")
    public Beneficiary getById(@PathVariable(name = "beneficiaryId") Long beneficiaryId) {
        return service.getById(beneficiaryId);
    }

//    @GetMapping(value = "/types")
//    public List<BeneficiaryType> getAllTypes() {
//        return service.getAllTypes();
//    }

}


