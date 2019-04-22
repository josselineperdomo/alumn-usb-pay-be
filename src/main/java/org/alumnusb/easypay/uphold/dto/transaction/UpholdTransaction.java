package org.alumnusb.easypay.uphold.dto.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpholdTransaction {

    private Denomination denomination;

    @Getter(onMethod = @__(@JsonIgnore))
    private String destination;

    // It always returns transaction details in USD, no matter what other currency are involves
    @Setter(onMethod = @__(@JsonProperty))
    private List<Details> normalized;

    @Setter(onMethod = @__(@JsonProperty))
    private String status;

    @Setter(onMethod = @__(@JsonProperty))
    private String id;
}
