package com.FarmProduce.FarmApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class rolesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }
    //constuctors
    public rolesModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }
//to strings
    @Override
    public String toString() {
        return "rolesModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
//non parameter constructors
    public rolesModel() {
    }

    //getters and setters
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
