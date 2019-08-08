package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.SimpleTextSection;
import ru.javawebinar.basejava.storage.ListStorage;
import ru.javawebinar.basejava.storage.Storage;

import static ru.javawebinar.basejava.model.SectionType.OBJECTIVE;
import static ru.javawebinar.basejava.model.SectionType.PERSONAL;

public class ResumeTestData {
    private Storage storage = new ListStorage();
    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1;
    private static final Section section_1 = new SimpleTextSection("Позиция",
            "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
    private static final Section section_2 = new SimpleTextSection("Личные качества",
            "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

    static {
        RESUME_1 = new Resume(UUID_1, "fullName3");
    }

    public static void main(String[] args) {
        RESUME_1.addContact("Тел.: +7(921)855-04-82");
        RESUME_1.addContact("Skype: grigory.kislin");
        RESUME_1.addContact("Почта: gkislin@yandex.ru");
        RESUME_1.addSection(OBJECTIVE, section_1);
        RESUME_1.addSection(PERSONAL, section_2);
        System.out.println(RESUME_1.getContact("Skype:"));
        System.out.println("---------------------");
        RESUME_1.printResume();
    }
}
