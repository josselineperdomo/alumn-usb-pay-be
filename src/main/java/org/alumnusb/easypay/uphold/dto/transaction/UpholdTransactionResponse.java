package org.alumnusb.easypay.uphold.dto.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpholdTransactionResponse implements UpholdTransaction {
    private Denomination denomination;

    // It always returns transaction details in USD, no matter what other currency are involves
    private List<Details> normalized;

}
