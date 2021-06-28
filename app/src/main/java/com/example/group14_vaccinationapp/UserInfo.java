package com.example.group14_vaccinationapp;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private String name;
    private int age;
    private int IC;
    private int phone;
    private String address;
    private String vaccinePrefer;
    private String notes;
    private String regisStatus;
    private String vaccinationStatus;

    public UserInfo(){
        name = "";
        age = 0;
        IC = 0;
        phone = 0;
        address = "";
        vaccinePrefer = "";
        notes = "";
        regisStatus = "You have not registered for your vaccination yet";
        vaccinationStatus = "";
    }

    public void setNotes(int age, String notes){
        this.age = age;
        this.notes = notes;
    }

    public void setBasicInfo(String name, int IC, int phone, String address, String vaccinePrefer, String regisStatus, String vaccinationStatus){
        this.name = name;
        this.IC = IC;
        this.phone = phone;
        this.address = address;
        this.vaccinePrefer = vaccinePrefer;
        this.regisStatus = regisStatus;
        this.vaccinationStatus = vaccinationStatus;
    }

    public String getName() {
        return name;
    }

    public int getAge(){
        return age;
    }

    public int getIC(){
        return IC;
    }

    public int getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getNotes() {
        return notes;
    }

    public String getRegisStatus() {
        return regisStatus;
    }

    public String getVaccinationStatus() {
        return vaccinationStatus;
    }

    public String getVaccinePrefer() {
        return vaccinePrefer;
    }
}
