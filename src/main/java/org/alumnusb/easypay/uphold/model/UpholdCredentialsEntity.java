package org.alumnusb.easypay.uphold.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.alumnusb.easypay.model.AuditModel;
import org.alumnusb.easypay.model.converter.HiddenAttributeConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "uphold_credentials")
@EqualsAndHashCode(callSuper = true)
public class UpholdCredentialsEntity extends AuditModel {
    @Id
    @Column(name = "credentials_id")
    private Short credentialsId;

    @Column(name = "access_token")
    @Convert(converter = HiddenAttributeConverter.class)
    private String accessToken;

    @Column(name = "refresh_token")
    @Convert(converter = HiddenAttributeConverter.class)
    private String refreshToken;
}
