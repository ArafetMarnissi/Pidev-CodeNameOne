/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;
import com.codename1.io.Preferences;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.


/**
 *
 * @author khali
 */
public class SessionManager {
    
    private static SessionManager instance;
    private static int id;
    private static String email;
    private static String password,nom,prenom;
    private static String private_key;
    private static String status;

    private SessionManager(int id,String email,String password,String nom,String prenom,String private_key,String status) {
    this.id=id;
    this.email=email;
    this.password =password;
    this.nom=nom;
    this.prenom =prenom;
    this.private_key = private_key;
    this.status=status;
 
    }

    
    
        public static synchronized SessionManager getInstance(int id,String email,String password,String nom,String prenom,String private_key,String status) {
        if (instance == null) {
            instance = new SessionManager( id, email, password, nom, prenom, private_key, status);
        }
        return instance;
    }
       /*     public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }*/
    public static SessionManager EndSession(){
        SessionManager.setId(0);
        SessionManager.setEmail(null);
        SessionManager.setNom(null);
        SessionManager.setPrenom(null);
        SessionManager.setPassword(null);
        SessionManager.setPrivate_key(null);
        SessionManager.setStatus(null);
        instance =null;
        return instance;
    }
    
    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        SessionManager.id = id;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        SessionManager.email = email;
    }


    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        SessionManager.password = password;
    }

    public static String getNom() {
        return nom;
    }

    public static void setNom(String nom) {
        SessionManager.nom = nom;
    }

    public static String getPrenom() {
        return prenom;
    }

    public static void setPrenom(String prenom) {
        SessionManager.prenom = prenom;
    }

    public static String getPrivate_key() {
        return private_key;
    }

    public static void setPrivate_key(String private_key) {
        SessionManager.private_key = private_key;
    }

    public static String isStatus() {
        return status;
    }

    public static void setStatus(String status) {
        SessionManager.status = status;
    }

    public static SessionManager getInstance() {
        return instance;
    }

    public static void setInstance(SessionManager instance) {
        SessionManager.instance = instance;
    }
    
    
    
    
    
    
}
