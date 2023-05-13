/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.entites;

/**
 *
 * @author ASUS
 */
public class Reponse {
    private int id;
    private String date,desc;

    @Override
    public String toString() {
        return "Reponse{" + "id=" + id + ", date=" + date + ", desc=" + desc + '}';
    }

    public Reponse(String date, String desc) {
        this.date = date;
        this.desc = desc;
    }

    public Reponse() {
    }

    public Reponse(int id, String date, String desc) {
        this.id = id;
        this.date = date;
        this.desc = desc;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getDesc() {
        return desc;
    }
    
    
}
