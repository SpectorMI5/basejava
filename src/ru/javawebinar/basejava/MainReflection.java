package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume("fullName");
        Field field = resume.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        Method method = resume.getClass().getDeclaredMethod("toString");
        method.setAccessible(true);

        System.out.println(field.getName());
        System.out.println(field.get(resume));
        field.set(resume, "new_uuid");
        System.out.println(resume);
        System.out.println("--------");
        System.out.println(method.invoke(resume));
    }
}