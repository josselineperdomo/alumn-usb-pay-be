package org.alumnusb.easypay.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBeneficiaryPaymentList {

    @NotNull
    private Long beneficiaryId;

    @NotNull
    private Long paymentListId;

    @NotNull
    private Float amount;
}