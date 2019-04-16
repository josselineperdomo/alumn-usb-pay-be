package org.alumnusb.easypay.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSchedule {

    @NotNull
    private Long paymentListId;

    @NotEmpty
    private String periodicity;

    @NotNull
    private Boolean active;

    @NotEmpty
    private String description;
}