package org.alumnusb.easypay.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePaymentList {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotNull
    private List<Long> beneficiaries;

    @NotNull
    private Float amount;

}