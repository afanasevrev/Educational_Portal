package com.example.Educational_Portal.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
/**
 * Класс сущность для взаимодетйствия с таблицей "materials" в БД
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
    @Lob
    @Column(name = "pdf_file")
    private byte[] pdf_id;
    public Materials(){}
    public Materials(String material_name) {
        this.material_name = material_name;
    }
    public Materials(String material_name, byte[] pdf_id) {
        this.material_name = material_name;
        this.pdf_id = pdf_id;
    }
}
