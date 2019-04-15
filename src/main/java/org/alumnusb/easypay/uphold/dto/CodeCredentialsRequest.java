package org.alumnusb.easypay.uphold.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeCredentialsRequest {
    private String code;

    private String grantType;
}