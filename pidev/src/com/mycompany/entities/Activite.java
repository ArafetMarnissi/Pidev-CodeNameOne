/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

import java.util.Date;

/**
 *
 * @author mmarr
 */
public class Activite {
    
    private int id;
    private String nomAcitivite,descriptionActivite,Image;
    private int nbrePlace;
    private String DateActivite,TimeActivite;
    private boolean en;
    private int id_p;
    private String qrcode;

    public Activite() {
    }

    public Activite(int id, String nomActivite, String descriptionActivite, int nbrePlace, String Image, String DateActivite) {
        this.id = id;
        this.nomAcitivite = nomActivite;
        this.descriptionActivite = descriptionActivite;
        this.nbrePlace = nbrePlace;
        this.Image = Image;
        this.DateActivite = DateActivite;
    }

    public Activite(String nomActivite, String descriptionActivite, int nbrePlace, String DateActivite, String TimeActivite) {
        this.nomAcitivite = nomActivite;
        this.descriptionActivite = descriptionActivite;
        this.nbrePlace = nbrePlace;
        this.DateActivite = DateActivite;
        this.TimeActivite = TimeActivite;
    }

    public Activite(String nomActivite, String descriptionActivite, int nbrePlace) {
        this.nomAcitivite = nomActivite;
        this.descriptionActivite = descriptionActivite;
        this.nbrePlace = nbrePlace;
    }

    public Activite(String nomAcitivite, String descriptionActivite, int nbrePlace, String DateActivite) {
        this.nomAcitivite = nomAcitivite;
        this.descriptionActivite = descriptionActivite;
        this.nbrePlace = nbrePlace;
        this.DateActivite = DateActivite;
    }

    public Activite(String nomActivite, String descriptionActivite) {
        this.nomAcitivite = nomActivite;
        this.descriptionActivite = descriptionActivite;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public int getId_p() {
        return id_p;
    }

    public void setId_p(int id_p) {
        this.id_p = id_p;
    }

    public String getTimeActivite() {
        return TimeActivite;
    }

    public void setTimeActivite(String TimeActivite) {
        this.TimeActivite = TimeActivite;
    }

    public String getNomAcitivite() {
        return nomAcitivite;
    }

    public void setNomAcitivite(String nomAcitivite) {
        this.nomAcitivite = nomAcitivite;
    }

    public boolean isEn() {
        return en;
    }

    public void setEn(boolean en) {
        this.en = en;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomActivite() {
        return nomAcitivite;
    }

    public void setNomActivite(String nomActivite) {
        this.nomAcitivite = nomActivite;
    }

    public String getDescriptionActivite() {
        return descriptionActivite;
    }

    public void setDescriptionActivite(String descriptionActivite) {
        this.descriptionActivite = descriptionActivite;
    }

    public int getNbrePlace() {
        return nbrePlace;
    }

    public void setnbrePlace(int nbrePlace) {
        this.nbrePlace = nbrePlace;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getDateActivite() {
        return DateActivite;
    }

    public void setDateActivite(String DateActivite) {
        this.DateActivite = DateActivite;
    }
    
    
}
