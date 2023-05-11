/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.codename1.util.regex.RE;
import com.mycompany.entities.User;
import com.mycompany.myapp.ProfileForm;
import com.mycompany.myapp.ConfrimAccountForm;
import com.mycompany.myapp.ListcategoryForm;
import com.mycompany.myapp.NewsfeedForm;
import com.mycompany.myapp.SessionManager;
import com.mycompany.myapp.SignInForm;
import com.mycompany.utils.Statics;
import java.util.Map;

/**
 *
 * @author khali
 */
public class ServiceUser {

    public static ServiceUser instance = null;

    public static boolean resultOk = true;
    String json;

    //initilisation connection request 
    private ConnectionRequest req;

    public static ServiceUser getInstance() {
        if (instance == null) {
            instance = new ServiceUser();
        }
        return instance;
    }

    public ServiceUser() {
        req = new ConnectionRequest();

    }
    //chck email 
   public boolean validateEmailAddress(String emailAddress) {
            RE pattern = new RE("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
return pattern.match(emailAddress);
}
    
    //
    //sign Up
    public void signup(TextField email, TextField nom, TextField prenom, TextField password, TextField confirmPassword, Resources res) {
        String url = Statics.BASE_URL + "/AddUserJson/new?email=" + email.getText().toString() + "&password=" + password.getText().toString()
                + "&nom=" + nom.getText().toString() + "&prenom=" + prenom.getText().toString();

        req.setUrl(url);

        if (email.getText().equals("") || password.getText().equals("") || nom.getText().equals("") || prenom.getText().equals("")) {
            Dialog.show("Erreur", "Tous les champs sont obligtoires", "OK", null);
            return;

        }

        if (!confirmPassword.getText().equals(password.getText())) {
            Dialog.show("Erreur", "Les mots de passes doivent être identiques", "OK", null);
            return;
        }
        if(!validateEmailAddress(email.getText()))
        {
           Dialog.show("Erreur", "L'adresse mail n'est pas valide", "OK", null);
           return;

        }
        req.addResponseListener((e) -> {
            byte[] data = (byte[]) e.getMetaData();
            String responseData = new String(data);
            System.out.println("data = " + responseData);
            new SignInForm(res).show();

        }
        );

        NetworkManager.getInstance().addToQueueAndWait(req);

    }

    public void signin(TextField username, TextField password, Resources rs) {
            
       
        String url = Statics.BASE_URL + "/user/signin?email=" + username.getText().toString() + "&password=" + password.getText().toString();
        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server
        req.setUrl(url);

        req.addResponseListener((e) -> {

            JSONParser j = new JSONParser();

            String json = new String(req.getResponseData()) + "";
          
            try {

                if (json.equals("failed")) {
                    Dialog.show("Echec d'authentification", "Username ou mot de passe éronné", "OK", null);
                } else {
                    System.out.println("data ==" + json);

                    Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));

                    //Session 
                    
                    float id = Float.parseFloat(user.get("id").toString());
//                    SessionManager.setId((int) id);
//                    SessionManager.setNom(user.get("nom").toString());
//                    SessionManager.setPrenom(user.get("prenom").toString());
//                    SessionManager.setPassword(user.get("password").toString());
//                    SessionManager.setEmail(user.get("email").toString());
//                    SessionManager.setPrivate_key(user.get("PrivateKey").toString());
//                    SessionManager.setStatus(user.get("Status").toString());
                    SessionManager.getInstance((int) id, user.get("email").toString(), user.get("password").toString(), user.get("nom").toString(),user.get("prenom").toString(), user.get("PrivateKey").toString(), user.get("Status").toString());
                    System.out.println("////////"+SessionManager.getEmail());
                    

                    if (user.size() > 0 && SessionManager.isStatus().equals("false")) {
                        new ConfrimAccountForm(rs).show();

                    }
                    if (user.size() > 0 && SessionManager.isStatus().equals("true")) {
                        new ListcategoryForm(rs).show();

                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

        //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public void confirmAccount(TextField code, Resources rs) {
        String email = SessionManager.getEmail().toString();

        String textfield = code.getText().toString();
        String hello = SessionManager.getPrivate_key().toString();
        hello = hello.substring(0, 6);
        System.out.println("Session code : " + hello);

        String url = Statics.BASE_URL + "/VerifyAccount?email=" + email + "&code=" + hello;

        if (textfield.equals(hello)) {
            Dialog.show("Succès", "Votre compte est vérifié", "OK", null);
            new NewsfeedForm(rs).show();
            req = new ConnectionRequest(url, false);
            req.setUrl(url);

        } else {
            Dialog.show("Erreur", "Le code est incorrect", "OK", null);

        }

        NetworkManager.getInstance().addToQueueAndWait(req);

    }
    
    
    
    
    
    public boolean modifierUser(SessionManager user) {
        
        String url =Statics.BASE_URL+"/user/editUser?id="+user.getId()+"&nom="+user.getNom()+"&prenom="+user.getPrenom();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return resultOk;

    }
    
    

}
