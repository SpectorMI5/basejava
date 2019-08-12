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
        YearMonth startDate_1 = YearMonth.of(2013, 10);
        YearMonth endDate_1 = YearMonth.of(2019, 8);
        String text_5 = "Автор проекта.\nСоздание, организация и проведение Java онлайн проектов и стажировок.";
        Organization organization_1 = new Organization(organizationName_1, startDate_1,
                endDate_1, text_5);

        String organizationName_2 = "Wrike";
        YearMonth startDate_2 = YearMonth.of(2014, 10);
        YearMonth endDate_2 = YearMonth.of(2016, 1);
        String text_6 = "Старший разработчик (backend)\nПроектирование и разработка онлайн платформы управления проектами Wrike";
        Organization organization_2 = new Organization(organizationName_2, startDate_2,
                endDate_2, text_6);

        ArrayList<Organization> organizationList_1 = new ArrayList<>(Arrays.asList(organization_1,
                organization_2));
        AbstractSection experience = new OrganizationSection("Опыт работы",
                organizationList_1);

        String organizationName_3 = "Coursera";
        YearMonth startDate_3 = YearMonth.of(2013, 3);
        YearMonth endDate_3 = YearMonth.of(2013, 5);
        String text_7 = "\"Functional Programming Principles in Scala\" by Martin Odersky";
        Organization organization_3 = new Organization(organizationName_3, startDate_3,
                endDate_3, text_7);

        String organizationName_4 = "Luxoft";
        YearMonth startDate_4 = YearMonth.of(2011, 3);
        YearMonth endDate_4 = YearMonth.of(2011, 4);
        String text_8 = "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"";
        Organization organization_4 = new Organization(organizationName_4, startDate_4,
                endDate_4, text_8);

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