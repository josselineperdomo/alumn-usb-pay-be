package org.alumnusb.easypay.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
}