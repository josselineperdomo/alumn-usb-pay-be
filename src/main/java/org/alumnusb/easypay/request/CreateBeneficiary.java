package org.alumnusb.easypay.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alumnusb.easypay.model.BeneficiaryType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBeneficiary {

    @NotEmpty
    private String upholdUsername;

    @NotEmpty
    @Email
    private String upholdEmail;

    @NotNull
    @JsonIgnoreProperties({"id", "description", "defaultAmount" })
    private BeneficiaryType type;
}