package com.soa.hospital.views;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
public class PatientIds implements Serializable {

    private String patientId;

    private Integer type;

    private String cardId;
}
