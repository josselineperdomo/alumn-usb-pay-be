package org.alumnusb.easypay.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "scheduled_payment")
@EqualsAndHashCode(callSuper = true)
public class Schedule extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_list_id")
    private PaymentList paymentList;

    @Column(name = "periodicity")
    private String periodicity;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "next_payment_at")
    private Instant nextPaymentAt;

    @Column(name = "last_executed_at")
    private Instant lastExecutedAt;

    @Column(columnDefinition = "text")
    private String description;
}