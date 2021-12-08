package com.soa.order.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @ program: demo
 * @ description: card
 * @ author: ShenBo
 * @ date: 2021-11-19 19:34:05
 */
@Entity
@Data
@Table(name="patient_card")
@IdClass(CardPrimary.class)
@DynamicUpdate
public class Card {
    @Id
    @Column(name = "patient_id")
    private String patientId;
    @Id
    private Integer type;
    @Id
    @Column(name = "card_id")
    private String cardId;
}
