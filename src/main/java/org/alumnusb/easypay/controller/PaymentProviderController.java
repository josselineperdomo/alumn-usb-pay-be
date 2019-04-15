package org.alumnusb.easypay.controller;

import lombok.RequiredArgsConstructor;
import org.alumnusb.easypay.uphold.UpholdClient;
import org.alumnusb.easypay.uphold.dto.UpholdCard;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/provider")
public class PaymentProviderController {

    private final UpholdClient upholdClient;

    @GetMapping("/cards")
    @ResponseBody
    public Set<UpholdCard> getAllCards() {
        return upholdClient.getAllCards();
    }
}