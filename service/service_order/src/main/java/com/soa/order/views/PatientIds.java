package com.soa.order.views;


import lombok.Data;

import java.io.Serializable;

@Data
public class PatientIds implements Serializable {

    private String patientId;

    private Integer type;

    private String cardId;
}
