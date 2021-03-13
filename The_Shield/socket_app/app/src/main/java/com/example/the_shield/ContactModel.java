package com.example.the_shield;

import java.util.List;

public class ContactModel {
    private String name;

    private List<String> number;

    public List<String> getNumber() {
        return number;
    }

    public void setNumber(List<String> number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
