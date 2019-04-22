package org.alumnusb.easypay.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.alumnusb.easypay.model.constant.PaymentStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Builder
@Entity
@Table(name = "payment_list_beneficiary")
@EqualsAndHashCode(callSuper = true)
public class PaymentListBeneficiary extends AuditModel {
    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "beneficiary_id", referencedColumnName = "id")
    private Beneficiary beneficiary;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_list_id", referencedColumnName = "id")
    private PaymentList paymentList;

    @Column(name = "amount_to_pay")
    private Float amount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}