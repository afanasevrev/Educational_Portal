package com.example.Educational_Portal.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
/**
 * Класс сущность для взаимодействия с таблицей в БД "Students"
 */
@Setter
@Getter
@Entity
@Table(name = "students")
public class Students {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "surname")
    private String surname;
    @Column(name = "name")
    private String name;
    @Column(name = "patronymic")
    private String patronymic;
    @Column(name = "faculty")
    private String faculty;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    public Students(){}
    public Students(String surname, String name, String patronymic, String faculty, String login, String password) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.faculty = faculty;
        this.login = login;
        this.password = password;
    }
}
