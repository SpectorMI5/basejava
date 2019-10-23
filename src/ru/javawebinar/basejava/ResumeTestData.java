package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.YearMonth;

import static ru.javawebinar.basejava.model.ContactType.*;
import static ru.javawebinar.basejava.model.SectionType.*;

public class ResumeTestData {

    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        Contact phoneNumber = new Contact("+7(921)855-04-82");
        Contact skype = new Contact("grigory.kislin");
        Contact email = new Contact("gkislin@yandex.ru");

        AbstractSection objective = new TextSection("Ведущий стажировок и корпоративного " +
                "обучения по Java Web и Enterprise технологиям");

        AbstractSection personal = new TextSection("Аналитический склад ума, сильная " +
                "логика, креативность, инициативность. Пурист кода и архитектуры.");

        String text_1 = "С 2013 года: разработка проектов \"Разработка Web приложения\", \"Java Enterprise\", \"Многомодульный " +
                "maven. Многопоточность.";
        String text_2 = "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike.";
        AbstractSection achievement = new ListSection(text_1, text_2);

        String text_3 = "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2";
        String text_4 = "Version control: Subversion, Git, Mercury, ClearCase, Perforce";
        AbstractSection qualifications = new ListSection(text_3, text_4);

        String organizationName_1 = "Java Online Projects";
        String url_1 = "http://javaops.ru/";
        YearMonth startDate_1 = YearMonth.of(2013, 10);
        String title_1 = "Автор проекта.";
        String description_1 = "Создание, организация и проведение Java онлайн проектов и стажировок.";
        Organization.OrganizationPeriod period_1 = new Organization.OrganizationPeriod(startDate_1, title_1, description_1);
        Organization organization_1 = new Organization(organizationName_1, url_1, period_1);

        String organizationName_2 = "Wrike";
        String url_2 = "https://www.wrike.com/";
        YearMonth startDate_2 = YearMonth.of(2014, 10);
        YearMonth endDate_2 = YearMonth.of(2016, 1);
        String title_2 = "Старший разработчик (backend)";
        String description_2 = "Проектирование и разработка онлайн платформы управления проектами Wrike";
        Organization.OrganizationPeriod period_2 = new Organization.OrganizationPeriod(startDate_2, endDate_2, title_2, description_2);
        Organization organization_2 = new Organization(organizationName_2, url_2, period_2);

        AbstractSection experience = new OrganizationSection(organization_1, organization_2);

        String organizationName_3 = "Coursera";
        String url_3 = "https://www.coursera.org/learn/progfun1";
        YearMonth startDate_3 = YearMonth.of(2013, 3);
        YearMonth endDate_3 = YearMonth.of(2013, 5);
        String title_3 = "\"Functional Programming Principles in Scala\" by Martin Odersky";
        Organization.OrganizationPeriod period_3 = new Organization.OrganizationPeriod(startDate_3, endDate_3, title_3, "");
        Organization organization_3 = new Organization(organizationName_3, url_3, period_3);

        String organizationName_4 = "Luxoft";
        String url_4 = "https://www.luxoft-training.ru/kurs/obektno-orientirovannyy__analiz_is_kontseptualnoe_modelirovanie_na_uml" +
                "_dlya_sistemnyh_analitikov_.html";
        YearMonth startDate_4 = YearMonth.of(2011, 3);
        YearMonth endDate_4 = YearMonth.of(2011, 4);
        String title_4 = "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"";
        Organization.OrganizationPeriod period_4 = new Organization.OrganizationPeriod(startDate_4, endDate_4, title_4, "");
        Organization organization_4 = new Organization(organizationName_4, url_4, period_4);

        String organizationName_5 = "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики";
        String url_5 = "http://www.ifmo.ru/ru/";
        YearMonth startDate_5 = YearMonth.of(1993, 9);
        YearMonth endDate_5 = YearMonth.of(1996, 7);
        String title_5 = "Аспирантура (программист С, С++)";
        Organization.OrganizationPeriod period_5 = new Organization.OrganizationPeriod(startDate_5, endDate_5, title_5, "");
        YearMonth startDate_6 = YearMonth.of(1987, 9);
        YearMonth endDate_6 = YearMonth.of(1993, 7);
        String title_6 = "Инженер (программист Fortran, C)";
        Organization.OrganizationPeriod period_6 = new Organization.OrganizationPeriod(startDate_6, endDate_6, title_6, "");
        Organization organization_5 = new Organization(organizationName_5, url_5, period_5, period_6);

        AbstractSection education = new OrganizationSection(organization_3, organization_4, organization_5);

        resume.addContact(PHONE_NUMBER, phoneNumber);
        resume.addContact(SKYPE, skype);
        resume.addContact(EMAIL, email);

        /*resume.addSection(OBJECTIVE, objective);
        resume.addSection(PERSONAL, personal);
        resume.addSection(ACHIEVEMENT, achievement);
        resume.addSection(QUALIFICATIONS, qualifications);
        resume.addSection(EXPERIENCE, experience);
        resume.addSection(EDUCATION, education);*/

        return resume;
    }

    public static void main(String[] args) {
        Resume resume = createResume("uuid_1", "fullName");

        System.out.println("Skype: " + resume.getContact(SKYPE));
        System.out.println(resume.getSection(PERSONAL));
        System.out.println(resume.getSection(QUALIFICATIONS));
        System.out.println(resume.getSection(EDUCATION));

        resume.printResume();
    }
}