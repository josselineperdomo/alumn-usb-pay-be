package org.alumnusb.easypay.uphold.dto.transaction;

//For more details please check
// https://uphold.com/en/developer/api/documentation/#transaction-object

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Details {

    @JsonProperty("CardId")
    private String cardId;

    private Float amount;

    private Float commission;

    private Float fee;
}
