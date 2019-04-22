package org.alumnusb.easypay.model;

import lombok.*;

import javax.persistence.CascadeType;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment_list")
@EqualsAndHashCode(callSuper = true)
public class PaymentList extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PaymentListBeneficiary> paymentListBeneficiaries;
}