/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

/**
 *
 * @author khali
 */
public class User {
    private int id;
    private String email;
    private String nom;
    private String prenom;
    private String password;
    private Boolean status;
    private int PrivateKey;

    public User(String email, String nom, String prenom, String password, Boolean status, int PrivateKey) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.status = status;
        this.PrivateKey = PrivateKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getPrivateKey() {
        return PrivateKey;
    }

    public void setPrivateKey(int PrivateKey) {
        this.PrivateKey = PrivateKey;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email=" + email + ", nom=" + nom + ", prenom=" + prenom + ", password=" + password + ", status=" + status + ", PrivateKey=" + PrivateKey + '}';
    }

    public void setNomAbonnement(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
   
}

