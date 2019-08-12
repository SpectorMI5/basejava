package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;

import static ru.javawebinar.basejava.model.ContactType.*;
import static ru.javawebinar.basejava.model.SectionType.*;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume = new Resume("uuid1", "fullName1");

        Contact phoneNumber = new Contact("+7(921)855-04-82");
        Contact skype = new Contact("grigory.kislin");
        Contact email = new Contact("gkislin@yandex.ru");

        AbstractSection objective = new SimpleTextSection("Позиция", "Ведущий стажировок и корпоративного " +
                "обучения по Java Web и Enterprise технологиям");

        AbstractSection personal = new SimpleTextSection("Личные качества", "Аналитический склад ума, сильная " +
                "логика, креативность, инициативность. Пурист кода и архитектуры.");

        String text_1 = "С 2013 года: разработка проектов \"Разработка Web приложения\", \"Java Enterprise\", \"Многомодульный " +
                "maven. Многопоточность.";
        String text_2 = "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike.";
        ArrayList<String> achievementList = new ArrayList<>(Arrays.asList(text_1, text_2));
        AbstractSection achievement = new MarkedListSection("Достижения", achievementList);

        String text_3 = "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2";
        String text_4 = "Version control: Subversion, Git, Mercury, ClearCase, Perforce";
        ArrayList<String> qualificationsList = new ArrayList<>(Arrays.asList(text_3, text_4));
        AbstractSection qualifications = new MarkedListSection("Квалификация",
                qualificationsList);

        String organizationName_1 = "Java Online Projects";
        String url_1 = "http://javaops.ru/";
        YearMonth startDate_1 = YearMonth.of(2013, 10);
        YearMonth endDate_1 = YearMonth.of(2019, 8);
        String title_1 = "Автор проекта.";
        String description_1 = "Создание, организация и проведение Java онлайн проектов и стажировок.";
        Organization organization_1 = new Organization(organizationName_1, url_1, startDate_1, endDate_1, title_1, description_1);

        String organizationName_2 = "Wrike";
        String url_2 = "https://www.wrike.com/";
        YearMonth startDate_2 = YearMonth.of(2014, 10);
        YearMonth endDate_2 = YearMonth.of(2016, 1);
        String title_2 = "Старший разработчик (backend)";
        String description_2 = "Проектирование и разработка онлайн платформы управления проектами Wrike";
        Organization organization_2 = new Organization(organizationName_2, url_2, startDate_2, endDate_2, title_2, description_2);

        ArrayList<Organization> organizationList_1 = new ArrayList<>(Arrays.asList(organization_1,
                organization_2));
        AbstractSection experience = new OrganizationSection("Опыт работы",
                organizationList_1);

        String organizationName_3 = "Coursera";
        String url_3 = "https://www.coursera.org/learn/progfun1";
        YearMonth startDate_3 = YearMonth.of(2013, 3);
        YearMonth endDate_3 = YearMonth.of(2013, 5);
        String title_3 = "\"Functional Programming Principles in Scala\" by Martin Odersky";
        Organization organization_3 = new Organization(organizationName_3, url_3, startDate_3,
                endDate_3, title_3, "");

        String organizationName_4 = "Luxoft";
        String url_4 = "https://www.luxoft-training.ru/kurs/obektno-orientirovannyy__analiz_is_kontseptualnoe_modelirovanie_na_uml" +
                "_dlya_sistemnyh_analitikov_.html";
        YearMonth startDate_4 = YearMonth.of(2011, 3);
        YearMonth endDate_4 = YearMonth.of(2011, 4);
        String title_4 = "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"";
        Organization organization_4 = new Organization(organizationName_4, url_4, startDate_4,
                endDate_4, title_4, "");

        ArrayList<Organization> organizationList_2 = new ArrayList<>(Arrays.asList(organization_3,
                organization_4));
        AbstractSection education = new OrganizationSection("Образование",
                organizationList_2);

        resume.addContact(PHONE_NUMBER, phoneNumber);
        resume.addContact(SKYPE, skype);
        resume.addContact(EMAIL, email);

        resume.addSection(OBJECTIVE, objective);
        resume.addSection(PERSONAL, personal);
        resume.addSection(ACHIEVEMENT, achievement);
        resume.addSection(QUALIFICATIONS, qualifications);
        resume.addSection(EXPERIENCE, experience);
        resume.addSection(EDUCATION, education);

        System.out.println("Skype: " + resume.getContact(SKYPE));
        System.out.println(resume.getSection(PERSONAL));
        System.out.println(resume.getSection(QUALIFICATIONS));
        System.out.println(resume.getSection(EDUCATION));

        resume.printResume();
    }
}