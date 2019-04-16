package org.alumnusb.easypay.controller;

import lombok.RequiredArgsConstructor;
import org.alumnusb.easypay.model.Beneficiary;
import org.alumnusb.easypay.model.PaymentList;
import org.alumnusb.easypay.model.PaymentListBeneficiary;
import org.alumnusb.easypay.request.CreateBeneficiary;
import org.alumnusb.easypay.request.UpdateBeneficiaryPaymentList;
import org.alumnusb.easypay.service.PaymentListBeneficiaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/paymentList/beneficiary")
public class PaymentListBeneficiaryController {

    private final PaymentListBeneficiaryService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentListBeneficiary create(@Validated @RequestBody UpdateBeneficiaryPaymentList request) {
        return service.create(request);
    }

    @DeleteMapping(value = "/{paymentListBeneficiaryId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable(name = "paymentListBeneficiaryId") Long id) {
        service.deleteOnList(id);
    }

    @GetMapping(value = "/{paymentListId}")
    public Page<PaymentListBeneficiary> fetchAllByPaymentListId(
            @PathVariable(name = "paymentListId") Long id,
            @RequestParam Pageable pageable) {
        return service.getAllByPaymentList(id, pageable);
    }
}
