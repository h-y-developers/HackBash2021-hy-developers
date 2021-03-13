package com.example.the_shield.Model;

public class MyContacts {

    String number,name;

    public MyContacts(String name,String number){
        this.name=name;
        this.number=number;
    }

    public String getName(){
        return name;
    }

    public String getNumber(){
        return number;
    }
}
