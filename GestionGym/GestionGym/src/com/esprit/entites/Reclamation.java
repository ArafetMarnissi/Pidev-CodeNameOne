/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.entites;

/**
 *
 * @author ASUS
 */
public class Reclamation {
    private int id,id_reponse;
    private String  desc,type,date;
    
    public Reclamation(int id, int id_reponse, String desc, String type, String date) {
        this.id = id;
        this.id_reponse = id_reponse;
        this.desc = desc;
        this.type = type;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "id=" + id + ", id_reponse=" + id_reponse + ", desc=" + desc + ", type=" + type + ", date=" + date + '}';
    }

    public Reclamation() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_reponse(int id_reponse) {
        this.id_reponse = id_reponse;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getId_reponse() {
        return id_reponse;
    }

    public String getDesc() {
        return desc;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }
    
    
}
