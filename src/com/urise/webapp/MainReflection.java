package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume("fullName");
        System.out.println(resume);
        Method toString = resume.getClass().getMethod("toString");
        System.out.println("invoke toString: " + toString.invoke(resume));
    }
}
