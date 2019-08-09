package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.ListStorage;
import ru.javawebinar.basejava.storage.Storage;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;

import static ru.javawebinar.basejava.model.SectionType.*;

public class ResumeTestData {
    private Storage storage = new ListStorage();
    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1;

    private static final Contact phoneNumber = new Contact("Тел.: +7(921)855-04-82");
    private static final Contact skype = new Contact("Skype: grigory.kislin");
    private static final Contact email = new Contact("Почта: gkislin@yandex.ru");

    private static final AbstractSection objective = new SimpleTextSection("Позиция",
            "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");

    private static final AbstractSection personal = new SimpleTextSection("Личные качества",
            "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

    private static final String text_1 = "С 2013 года: разработка проектов \"Разработка Web приложения\"," +
            "\"Java Enterprise\", \"Многомодульный maven. Многопоточность.";
    private static final String text_2 = "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike.";
    private static final ArrayList<String> achievementList = new ArrayList<>(Arrays.asList(text_1, text_2));
    private static final AbstractSection achievement = new MarkedListSection("Достижения", achievementList);

    private static final String text_3 = "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2";
    private static final String text_4 = "Version control: Subversion, Git, Mercury, ClearCase, Perforce";
    private static final ArrayList<String> qualificationsList = new ArrayList<>(Arrays.asList(text_3, text_4));
    private static final AbstractSection qualifications = new MarkedListSection("Квалификация",
            qualificationsList);

    private static final String organizationName_1 = "Java Online Projects";
    private static final YearMonth startDate_1 = YearMonth.of(2013, 10);
    private static final YearMonth endDate_1 = YearMonth.of(2019, 8);
    private static final String text_5 = "Автор проекта.\nСоздание, организация и проведение Java онлайн проектов и стажировок.";
    private static final Organization organization_1 = new Organization(organizationName_1, startDate_1,
            endDate_1, text_5);

    private static final String organizationName_2 = "Wrike";
    private static final YearMonth startDate_2 = YearMonth.of(2014, 10);
    private static final YearMonth endDate_2 = YearMonth.of(2016, 1);
    private static final String text_6 = "Старший разработчик (backend)\nПроектирование и разработка онлайн " +
            "платформы управления проектами Wrike";
    private static final Organization organization_2 = new Organization(organizationName_2, startDate_2,
            endDate_2, text_6);

    private static final ArrayList<Organization> organizationList_1 = new ArrayList<>(Arrays.asList(organization_1,
            organization_2));
    private static final AbstractSection experience = new OrganizationSection("Опыт работы",
            organizationList_1);

    private static final String organizationName_3 = "Coursera";
    private static final YearMonth startDate_3 = YearMonth.of(2013, 3);
    private static final YearMonth endDate_3 = YearMonth.of(2013, 5);
    private static final String text_7 = "\"Functional Programming Principles in Scala\" by Martin Odersky";
    private static final Organization organization_3 = new Organization(organizationName_3, startDate_3,
            endDate_3, text_7);

    private static final String organizationName_4 = "Luxoft";
    private static final YearMonth startDate_4 = YearMonth.of(2011, 3);
    private static final YearMonth endDate_4 = YearMonth.of(2011, 4);
    private static final String text_8 = "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"";
    private static final Organization organization_4 = new Organization(organizationName_4, startDate_4,
            endDate_4, text_8);

    private static final ArrayList<Organization> organizationList_2 = new ArrayList<>(Arrays.asList(organization_3,
            organization_4));
    private static final AbstractSection education = new OrganizationSection("Образование",
            organizationList_2);

    static {
        RESUME_1 = new Resume(UUID_1, "fullName3");
    }

    public static void main(String[] args) {
        RESUME_1.addContact(phoneNumber);
        RESUME_1.addContact(skype);
        RESUME_1.addContact(email);
        RESUME_1.addSection(OBJECTIVE, objective);
        RESUME_1.addSection(PERSONAL, personal);
        RESUME_1.addSection(ACHIEVEMENT, achievement);
        RESUME_1.addSection(QUALIFICATIONS, qualifications);
        RESUME_1.addSection(EXPERIENCE, experience);
        RESUME_1.addSection(EDUCATION, education);
        System.out.println(RESUME_1.getContact("Skype:"));
        System.out.println("---------------------");
        System.out.println(RESUME_1.getSection(PERSONAL));
        System.out.println("---------------------");
        System.out.println(RESUME_1.getSection(QUALIFICATIONS));
        System.out.println("---------------------");
        System.out.println(RESUME_1.getSection(EDUCATION));
        System.out.println("---------------------");
        RESUME_1.printResume();
    }
}