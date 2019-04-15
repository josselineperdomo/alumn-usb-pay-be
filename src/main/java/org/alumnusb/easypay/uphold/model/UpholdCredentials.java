package org.alumnusb.easypay.uphold.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alumnusb.easypay.model.AuditModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "uphold_credentials")
public class UpholdCredentials extends AuditModel {
    @Id
    @Column
    private Short credentialsId;

    @Column
    private String accessToken;

    @Column
    private String refreshToken;
}
