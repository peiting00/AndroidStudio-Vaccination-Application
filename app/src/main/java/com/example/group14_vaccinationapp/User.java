package com.example.group14_vaccinationapp;

public class User {
    String ic,name,age,phone,address,notes,status,vaccineID;

    public User(String ic,String name, String age, String phone, String address, String notes,String status,String vaccineID){
        this.ic=ic;
        this.name=name;
        this.age=age;
        this.phone=phone;
        this.address=address;
        this.notes=notes;
        this.status=status;
        this.vaccineID=vaccineID;
    }

    public String getIc(){
        return ic;
    }

    public String getName(){
        return name;
    }

    public String getAge(){
        return age;
    }

    public String getPhone(){
        return phone;
    }

    public String getAddress(){
        return address;
    }

    public String getNotes(){
        return notes;
    }

    public String getStatus(){
        return status;
    }

    public String getVaccineID(){
        return vaccineID;
    }

}