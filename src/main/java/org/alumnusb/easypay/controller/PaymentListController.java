package org.alumnusb.easypay.controller;

import lombok.RequiredArgsConstructor;
import org.alumnusb.easypay.model.PaymentList;
import org.alumnusb.easypay.request.CreatePaymentList;
import org.alumnusb.easypay.service.PaymentListService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/paymentList")
public class PaymentListController {

    private final PaymentListService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentList create(@Validated @RequestBody CreatePaymentList request) {
        return service.create(request);
    }

    @GetMapping
    public Page<PaymentList> fetchAllItems(Pageable pageable) {
        return service.getAll(pageable);
    }

}
