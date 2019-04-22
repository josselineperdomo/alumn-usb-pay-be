package org.alumnusb.easypay.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;


@Data
@Builder
@Entity
@Table(name = "beneficiary")
@EqualsAndHashCode(callSuper = true)
public class Beneficiary extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column(unique = true)
    private String upholdEmail;

    @Column(unique = true)
    private String upholdUsername;

    @Column
    private Boolean active;

    @Column(name = "type")
    private BeneficiaryType type;

    @OneToMany
    private Set<PaymentListBeneficiary> paymentListBeneficiaries;
}