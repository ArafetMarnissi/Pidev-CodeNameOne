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
public class LigneCommande {
    private int id;
   // private int idProduit;
    private String NomProduit;
    private int QuProduit;
    private float PrixUnitaire;

    public LigneCommande() {
    }

    public LigneCommande(int id, String NomProduit, int QuProduit, float PrixUnitaire) {
        this.id = id;
        this.NomProduit = NomProduit;
        this.QuProduit = QuProduit;
        this.PrixUnitaire = PrixUnitaire;
    }

    public LigneCommande(String NomProduit, int QuProduit, float PrixUnitaire) {
        this.NomProduit = NomProduit;
        this.QuProduit = QuProduit;
        this.PrixUnitaire = PrixUnitaire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomProduit() {
        return NomProduit;
    }

    public void setNomProduit(String NomProduit) {
        this.NomProduit = NomProduit;
    }

    public int getQuProduit() {
        return QuProduit;
    }

    public void setQuProduit(int QuProduit) {
        this.QuProduit = QuProduit;
    }

    public float getPrixUnitaire() {
        return PrixUnitaire;
    }

    public void setPrixUnitaire(float PrixUnitaire) {
        this.PrixUnitaire = PrixUnitaire;
    }

    @Override
    public String toString() {
        return "LigneCommande{" + "id=" + id + ", NomProduit=" + NomProduit + ", QuProduit=" + QuProduit + ", PrixUnitaire=" + PrixUnitaire + '}';
    }
    
    
    
    
    
}
