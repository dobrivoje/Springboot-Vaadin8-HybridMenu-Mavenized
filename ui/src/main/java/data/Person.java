package data;

import java.io.Serializable;

public class Person implements Serializable {

    private String name;
    private Integer birthYear;

    public Person() {
    }

    public Person(String name, Integer birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

}
