package com.example.Educational_Portal.Temp;

import lombok.Getter;
import lombok.Setter;
/**
 * Вспомогательный класс для отправки данных клиенту
 */
@Setter
@Getter
public class MaterialsTemp {
    private int id;
    private String material_name;
    public MaterialsTemp(){};
    public MaterialsTemp(int id, String material_name) {
        this.id = id;
        this.material_name = material_name;
    }
}
