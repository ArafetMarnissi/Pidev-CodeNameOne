/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

import java.util.HashMap;


/**
 *
 * @author marni
 */
public class SessionPanier {
        
    private static SessionPanier instance;
    private static HashMap<Produit, Integer> panier = new HashMap<>();

    private SessionPanier(HashMap<Produit, Integer> panier) {
        this.panier = panier;
    }
    
    
    public static SessionPanier getInstance() {
        if (instance == null) {
            instance = new SessionPanier(new HashMap<>());
        }
        return instance;
    }

    public static void setInstance(SessionPanier instance) {
        SessionPanier.instance = instance;
    }
    public static SessionPanier EndSession(){
        instance =null;
        panier.clear();
        return instance;
    }
    public static HashMap<Produit, Integer> getPanier() {
        return panier;
    }

    public static void setPanier(HashMap<Produit, Integer> panier) {
        SessionPanier.panier = panier;
    }

    public static synchronized SessionPanier getInstance(HashMap<Produit, Integer> panier) {
        if (instance == null) {
            instance = new SessionPanier(panier);
        }
        return instance;
    }
    public void addProduct(Produit produit){
        SessionPanier.getInstance();
        if(panier.containsKey(produit))
        {
            panier.put(produit, panier.get(produit)+1);
        }
        else{
            panier.put(produit, 1);
        }
    }
        public void decreaseProduct(Produit produit){
        SessionPanier.getInstance();
        if(panier.containsKey(produit))
        {
            if(panier.get(produit)>1){
            panier.put(produit, panier.get(produit)-1);
            }
        }
    }
    public int getQuantity(Produit produit){
        SessionPanier.getInstance();
        if(panier.containsKey(produit))
        {
            return panier.get(produit);
        }
        else
            return 0;
    }    
    public float calculTotale(){
        SessionPanier.getInstance();
        float total =0;
        for (Produit prod : panier.keySet()){
            total +=prod.getPrix_produit()*panier.get(prod);
        }
        return total;
    } 
}
