/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

//import java.sql.Date;

/**
 *
 * @author mmarr
 */
public class Participation {
    
     private int id,id_u;
    private String dateParticipation;
    private Activite activite;

    public Participation() {
    }

    public Participation(int id) {
        this.id = id;
    }
    

    public Participation(int id, int id_u, Activite activite) {
        this.id = id;
        this.id_u = id_u;
        //this.dateParticipation = dateParticipation;
        this.activite = activite;
    }

    public Participation(int id_u, Activite activite) {
        this.id_u = id_u;
        //this.dateParticipation = dateParticipation;
        this.activite = activite;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_u() {
        return id_u;
    }

    public void setId_u(int id_u) {
        this.id_u = id_u;
    }


    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public String getDateParticipation() {
        return dateParticipation;
    }

    public void setDateParticipation(String dateParticipation) {
        this.dateParticipation = dateParticipation;
    }
    
    
}
