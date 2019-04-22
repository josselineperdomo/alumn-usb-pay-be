package org.alumnusb.easypay.uphold.dto.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//For more details please check
// https://uphold.com/en/developer/api/documentation/#transaction-object

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = "rate", allowSetters = true)
class Denomination {
    private Float amount;
    private String currency;
    private Float rate;
}
