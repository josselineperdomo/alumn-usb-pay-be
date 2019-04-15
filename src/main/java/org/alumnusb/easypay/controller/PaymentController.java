package org.alumnusb.easypay.controller;

import lombok.RequiredArgsConstructor;
import org.alumnusb.easypay.model.Payment;
import org.alumnusb.easypay.request.CreatePayment;
import org.alumnusb.easypay.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/payments")
public class PaymentController {

    private final PaymentService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Payment create(@Validated @RequestBody CreatePayment request) {
        return service.create(request);
    }

    @GetMapping("/callback")
    @ResponseBody
    public void callbackFromOAuth(@RequestParam(name = "code") String authCode, @RequestParam String state) {
        service.callbackFromOAuth(authCode, state);
    }
}
