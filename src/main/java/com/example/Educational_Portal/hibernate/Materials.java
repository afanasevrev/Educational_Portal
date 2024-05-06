package com.example.Educational_Portal.hibernate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
/**
 * Класс сущность для взаимодетйствия с таблице "materials" в БД
 */
@Setter
@Getter
@Entity
@Table(name = "materials")
public class Materials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "material_name")
    private String material_name;
    public Materials(){}
    public Materials(String material_name) {
        this.material_name = material_name;
    }
}
