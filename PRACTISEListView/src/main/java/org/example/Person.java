package org.example;

public class Person {

    private String name;
    private int age;
    private String address;

    private String personalInfo;


    public Person(String name, int age, String address, String personalInfo) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.personalInfo = personalInfo;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(String personalInfo) {
        this.personalInfo = personalInfo;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", personalInfo='" + personalInfo + '\'' +
                '}';
    }
}
