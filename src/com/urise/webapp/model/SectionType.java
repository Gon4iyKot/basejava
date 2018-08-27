package com.urise.webapp.model;

public enum SectionType {
    PERSONAL("Личностные качества"), //text
    OBJECTIVE("Позиция"),            //text
    ACHIEVEMENT("Достижения"),       //list
    QUALIFICATIONS("Квалификация"),  //list
    EXPERIENCE("Опыт работы"),       //contentBlock
    EDUCATION("Образование");        //contentBlock

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
