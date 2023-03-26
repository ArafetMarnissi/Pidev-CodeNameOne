/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

/**
 *
 * @author marni
 */
public class Commande {
    int id;
    int user_id;
    String date_Commande,adresse_livraison;
    float prix_commande;
    String methode_paiement;
    int telephone;

    public Commande() {
        
    }

    public Commande(String date_Commande, String adresse_livraison, float prix_commande, String methode_paiement, int telephone) {
        
        this.date_Commande = date_Commande;
        this.adresse_livraison = adresse_livraison;
        this.prix_commande = prix_commande;
        this.methode_paiement = methode_paiement;
        this.telephone = telephone;
    }

    public Commande(int id, int user_id, String date_Commande, String adresse_livraison, float prix_commande, String methode_paiement, int telephone) {
        this.id = id;
        this.user_id = user_id;
        this.date_Commande = date_Commande;
        this.adresse_livraison = adresse_livraison;
        this.prix_commande = prix_commande;
        this.methode_paiement = methode_paiement;
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDate_Commande() {
        return date_Commande;
    }

    public void setDate_Commande(String date_Commande) {
        this.date_Commande = date_Commande;
    }

    public String getAdresse_livraison() {
        return adresse_livraison;
    }

    public void setAdresse_livraison(String adresse_livraison) {
        this.adresse_livraison = adresse_livraison;
    }

    public float getPrix_commande() {
        return prix_commande;
    }

    public void setPrix_commande(float prix_commande) {
        this.prix_commande = prix_commande;
    }

    public String getMethode_paiement() {
        return methode_paiement;
    }

    public void setMethode_paiement(String methode_paiement) {
        this.methode_paiement = methode_paiement;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    
    
    
    
}
