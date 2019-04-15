package org.alumnusb.easypay.uphold.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpholdCard {

    private String id;

    private String label;

    private Float balance;

    private Float available;

    private String currency;
}