/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

/**
 *
 * @author damma
 */
public class Category {
    
   private int id;
   private String nomCategory;
   private String imageCategorie;

    public Category(int id, String nomCategory, String imageCategorie) {
        this.id = id;
        this.nomCategory = nomCategory;
        this.imageCategorie = imageCategorie;
    }

    public Category() {
    }

    public Category(String nomCategory) {
        this.nomCategory = nomCategory;
    }

    public Category(String nomCategory, String imageCategorie) {
        this.nomCategory = nomCategory;
        this.imageCategorie = imageCategorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomCategory() {
        return nomCategory;
    }

    public void setNomCategory(String nomCategory) {
        this.nomCategory = nomCategory;
    }

    public String getImageCategorie() {
        return imageCategorie;
    }

    public void setImageCategorie(String imageCategorie) {
        this.imageCategorie = imageCategorie;
    }
   
   
}
