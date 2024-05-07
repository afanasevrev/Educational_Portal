package com.example.Educational_Portal.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
/**
 * Класс сущность для администраторов системы
 */
@Setter
@Getter
@Entity
@Table(name = "admins")
public class Admins {
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
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "position")
    private String position;
    public Admins(){};
    public Admins(String surname, String name, String patronymic, String login, String password, String position) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.login = login;
        this.password = password;
        this.position = position;
    }
}
