package com.example.Educational_Portal;

import com.example.Educational_Portal.Temp.MaterialsTemp;
import com.example.Educational_Portal.db.Admins;
import com.example.Educational_Portal.db.Students;
import com.example.Educational_Portal.hibernate.HibernateUtil;
import com.example.Educational_Portal.db.Materials;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Контроллер - отвечает на запросы пользователей через браузер
 */
@Controller
public class ControllerHome {
    /**
     * Главная страница сайта
     * @return home_page.html
     */
    @GetMapping("/")
    private String getInfo() {
        return "home_page";
    }
    /**
     * Страница со списком учебных материалов
     * @param model список материалов
     * @return materials.html
     */
    @GetMapping("/materials")
    private String getMaterials(Model model) {
        List<Materials> materials = getMaterialsList();
        List<MaterialsTemp> materialsTemp = new ArrayList<>();
        for (Materials material: materials) {
            materialsTemp.add(new MaterialsTemp(material.getId(), material.getMaterial_name()));
        }
        model.addAttribute("materialsTemp", materialsTemp);
        return "materials";
    }
    /**
     * Страница со списком материалов для администраторов
     * @param model список материалов
     * @return materials_for_admins.html
     */
    @GetMapping("/materials_for_admins")
    private String getMaterialsForAdmins(Model model) {
        List<Materials> materials = getMaterialsList();
        List<MaterialsTemp> materialsTemp = new ArrayList<>();
        for (Materials material: materials) {
            materialsTemp.add(new MaterialsTemp(material.getId(), material.getMaterial_name()));
        }
        model.addAttribute("materialsTemp", materialsTemp);
        return "materials_for_admins";
    }
    @GetMapping("/materials_for_admins/delete/{id}")
    private String deleteMaterial(@PathVariable String id, Model model) {
        int material_id = Integer.parseInt(id);
        deleteMaterial(material_id);
        return "material_is_delete";
    }
    /**
     * Страница для администраторов системы
     * @return admins_page.html
     */
    @GetMapping("/admins_page")
    private String getAdminsPage() {
        return "admins_page";
    }
    /**
     * Откроется форма для добавления администратора
     * @return add_admin.html
     */
    @GetMapping("/admins_page/add")
    private String addAdminForm(Model model) {
        model.addAttribute("admins", new Admins());
        return "add_admin";
    }
    /**
     * Post - запрос от клиента на добавление администратора в БД
     * @param admins
     * @param model
     * @return home_page.html
     */
    @PostMapping("/admins_page/add")
    private String addAdmin(@ModelAttribute Admins admins, Model model) {
        writeAdmin(admins);
        return "add_admin";
    }
    /**
     * Страница список студентов для
     * администраторов системы
     * @param model список студентов
     * @return students.html
     */
    @GetMapping("/students")
    private String getStudents(Model model) {
        List<Students> students = getStudentsList();
        model.addAttribute("students", students);
        return "students";
    }
    /**
     * Страница материалов для администраторов
     * @param model список материалов
     * @return materials_for_admins.html
     */
    @GetMapping("/materials_for_admins/add")
    private String addMaterialForm(Model model) {
        model.addAttribute("materials", new Materials());
        return "addd_material";
    }
    /**
     * POST - запрос от администратора системы на добавление материала в БД
     * @param file
     * @return add_material.html
     */
    @PostMapping("/materials_for_admins/add")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        // Здесь добавьте логику для обработки файла
        try {
            byte[] filePDF = file.getBytes();
            writeMaterial(new Materials(file.getOriginalFilename(), filePDF));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "addd_material"; // Перенаправление или возврат ответа после обработки файла
    }
    /**
     * Страница форма для администратора на добавление студента в БД
     * @param model
     * @return add_student.html
     */
    @GetMapping("/admins_page/student/add")
    private String addStudentForm(Model model) {
        model.addAttribute("students", new Students());
        return "add_student";
    }
    /**
     * POST - запрос от администратора на добавление студента в БД
     * @param students
     * @param model
     * @return add_student.html
     */
    @PostMapping("/admins_page/student/add")
    private String addStudent(@ModelAttribute Students students, Model model) {
        writeStudent(students);
        return "add_student";
    }
    /**
     * Метод удаляет из БД студента
     * @param id
     * @return students.html
     */
    @GetMapping("/students/delete/{id}")
    private String deleteStudent(@PathVariable String id) {
        int studentId = Integer.parseInt(id);
        deleteStudent(studentId);
        return "student_dismiss";
    }
    @GetMapping("/materials/download/{id}")
    private ResponseEntity<byte[]> downloadDocument(@PathVariable String id) {
        int documentId = Integer.parseInt(id);
        byte[] resource = getMaterialId(documentId).getPdf_file();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=material.pdf").body(resource);
    }
    /**
     * Метод возвращает из БД список материалов
     * @return массив Materials
     */
     public synchronized List<Materials> getMaterialsList() {
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
    /**
     * Метод возвращает из БД список студентов
     * @return массив Students
     */
    public synchronized List<Students> getStudentsList() {
        Transaction transaction = null;
        List<Students> students = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            students = session.createQuery("from Students", Students.class).list();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return students;
    }
    /**
     * Метод возвращает из БД список администраторов
     * @return массив Admins
     */
    public synchronized List<Admins> getAdminsList() {
        Transaction transaction = null;
        List<Admins> admins = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            admins = session.createQuery("from Admins", Admins.class).list();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return admins;
    }
    /**
     * Метод записывает в БД администратора системы
     * @param admin
     */
    public synchronized void writeAdmin(Admins admin) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            // Добавим в БД клиента
            session.persist(admin);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    /**
     * Метод записывает в БД нового студента
     * @param student
     */
    public synchronized void writeStudent(Students student) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            // Добавим в БД клиента
            session.persist(student);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    /**
     * Метод записывает в БД новый материал
     * @param material
     */
    public synchronized void writeMaterial(Materials material) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            // Добавим в БД клиента
            session.persist(material);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    /**
     * Метод удаляет администратора из БД
     * @param id
     */
    public synchronized void deleteAdmin(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            Admins admin = session.get(Admins.class, id);
            //Удаляем строку
            session.delete(admin);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    /**
     * Метод удаляет из БД студента
     * @param id
     */
    public synchronized void deleteStudent(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            Students student = session.get(Students.class, id);
            //Удаляем строку
            session.delete(student);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    /**
     * Метод удаляет из БД материал
     * @param id
     */
    public synchronized void deleteMaterial(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            Materials material = session.get(Materials.class, id);
            //Удаляем строку
            session.delete(material);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    /**
     * Возвращает учебный материал по ID
     * @param id
     * @return material
     */
    private synchronized Materials getMaterialId(int id) {
        Transaction transaction = null;
        Materials material = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Старт транзакции
            transaction = session.beginTransaction();
            material = session.get(Materials.class, id);
            // Коммит транзакции
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return material;
    }
}
