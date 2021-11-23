package com.soa.hospital.model;/**
 * Created By ShaoCHi
 * Date 2021/11/20 4:54 下午
 * Tongji University
 */

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.soa.hospital.views.PatientIds;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

/**
 * author ShaoCHi
 * Date 2021/11/20 4:54 下午
 * Tongji University
 */

@Entity
@IdClass(PatientIds.class)
@Table(name = "patient_card")
@Getter
@Setter
public class PatientCard {
  @Id
  @Column(name = "patient_id")
  private String patientId;

  @Id
  @Column(name = "card_id")
  private String cardId;

  @Id
  @Column(name = "type")
  private Integer type;

  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hospital")
  private Hospital hospital;
}
