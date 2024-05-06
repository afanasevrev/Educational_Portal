package com.example.Educational_Portal;

import com.example.Educational_Portal.hibernate.HibernateUtil;
import com.example.Educational_Portal.hibernate.Materials;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер - отвечает на запросы пользователей через браузер
 */
@Controller
public class ControllerHome {
    @GetMapping("/")
    private String getInfo() {
        return "home_page";
    }
    @GetMapping("/static/css/stylesheet.css")
    private String getCSS() {
        return "stylesheet";
    }
    @GetMapping("/materials")
    private String getMaterials(Model model) {
        List<Materials> materials = getMaterialsList();
        model.addAttribute("materials", materials);
        return "materials";
    }
    /**
     * Метод возвращает из БД список материалов
     * @return массив Materials
     */
    private synchronized List<Materials> getMaterialsList() {
        Transaction transaction = null;
        List<Materials> materials = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            materials = session.createQuery("from Materials", Materials.class).list();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return materials;
    }
}
