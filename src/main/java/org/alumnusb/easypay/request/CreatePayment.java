package org.alumnusb.easypay.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePayment {

    @NotEmpty
    private String beneficiaryUsername;

    @NotEmpty
    @Email
    private String beneficiaryEmail;

    @Positive
    private Float amount;
}
