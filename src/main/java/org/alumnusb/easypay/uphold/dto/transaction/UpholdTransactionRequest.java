package org.alumnusb.easypay.uphold.dto.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpholdTransactionRequest implements UpholdTransaction {
    @JsonIgnoreProperties("rate")
    private Denomination denomination;

    private String destination;
}
