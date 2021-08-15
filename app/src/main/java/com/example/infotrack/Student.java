package com.example.infotrack;

public class Student {

    private String name;
    private String gender;
    private String section;
    private  String age;

    public Student() {
        this.name = null;
        this.age = null;
        this.gender = null;
        this.section = null;
    }

    public Student(String name, String age, String gender, String section) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.section = section;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }


}
