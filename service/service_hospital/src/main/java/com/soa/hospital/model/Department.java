package com.soa.hospital.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ program: demo
 * @ description: Department
 * @ author: ShenBo
 * @ date: 2021-11-18 14:40:40
 */
@Entity
@Table(name="department")
public class Department {
    @Id
    private String Id;
    private String name;
    private String introduction;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
