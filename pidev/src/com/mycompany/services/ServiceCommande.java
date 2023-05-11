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
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Commande;
import com.mycompany.entities.LigneCommande;
import com.mycompany.entities.Produit;
import com.mycompany.entities.SessionPanier;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author marni
 */
public class ServiceCommande {
        
    
    public static ServiceCommande instance = null ;
    
    public static boolean resultOk = true;

    
    private ConnectionRequest req;
    
    
    public static ServiceCommande getInstance() {
        if(instance == null )
            instance = new ServiceCommande();
        return instance ;
    }
    
    
    
    public ServiceCommande() {
        req = new ConnectionRequest();
        
    }
    
    
    //ajout 
    public void ajoutCommande(Commande commande) {
        String Products ="";
        for(Produit prod :SessionPanier.getPanier().keySet()){
        Products +="&produit_"+prod.getId()+"="+SessionPanier.getPanier().get(prod);
        }
        System.out.println(Products);
        String url =Statics.BASE_URL+"/apiAjouterCommande?adresse_livraison="+commande.getAdresse_livraison()+"&methode_paiement="+commande.getMethode_paiement()+"&telephone="+commande.getTelephone()+"&user_id="+5+Products; 
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        
    }
    
    
    //affichage
    
    public ArrayList<Commande>affichageCommandes() {
        ArrayList<Commande> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/apiAffichageCommandeClient/5";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapCommandes = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapCommandes.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        Commande commande = new Commande();
                        
                        //dima id fi codename one float 5outhouha
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        String DateString = obj.get("dateCommande").toString();
                        
                        String AdresseLivraison = obj.get("AdresseLivraison").toString();
                        String methodePaiement = obj.get("methodePaiement").toString();
                        float prixCommande = Float.parseFloat(obj.get("prixCommande").toString());
                        float telephone = Float.parseFloat(obj.get("telephone").toString());
                        
                        commande.setId((int)id);
                        commande.setAdresse_livraison(AdresseLivraison);
                        commande.setMethode_paiement(methodePaiement);
                        commande.setPrix_commande((float)prixCommande);
                        commande.setTelephone((int)telephone);
                        ///formater la date
                        
                        SimpleDateFormat formatIn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
                        SimpleDateFormat formatOut = new SimpleDateFormat("dd/MM/yyyy");

                        try {
                            Date date = formatIn.parse(DateString);
                            String formattedDate = formatOut.format(date);
                            commande.setDate_Commande(formattedDate); // ajouter la date à l'entité Commande
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        
                        
                        //insert data into ArrayList result
                        result.add(commande);
                        System.out.println("les commandes : "+result.toString());
                       
                
                    }
                    
                }catch(Exception ex) {
                    
                    ex.printStackTrace();
                }
            
            }
        });
        
      NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
       
        return result;
        
        
    }
    
    
    
    
    //Delete 
    public boolean deleteCommande(int id ) {
        String url = Statics.BASE_URL +"/apiDeletecommande/"+id;
        
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                    req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  resultOk;
    }
    
    
    
    //Update 
    public boolean modifierCommande(Commande commande) {
        String url = Statics.BASE_URL +"/apiUpdatecommandeClient/"+commande.getId()+"?adresse_livraison="+commande.getAdresse_livraison()+"&methode_paiement="+commande.getMethode_paiement()+"&telephone="+commande.getTelephone();
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
    
    /////détail de la commande
    
       
    public ArrayList<LigneCommande>affichageLigneCommande(int id) {
        ArrayList<LigneCommande> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/Apicommande/detais/"+id;
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapCommandes = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapCommandes.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        LigneCommande ligneCommande = new LigneCommande();
                        
                        //dima id fi codename one float 5outhouha
                        float idL = Float.parseFloat(obj.get("id").toString());
                        
                        String NomProduit = obj.get("nomProduit").toString();
                        
                        float QuProduit = Float.parseFloat(obj.get("QuProduit").toString()) ;
                        
                        float prixUnitaire = Float.parseFloat(obj.get("PrixUnitaire").toString());
                        
                        
                        ligneCommande.setId((int)idL);
                        ligneCommande.setNomProduit(NomProduit);
                        ligneCommande.setQuProduit((int)QuProduit);
                        ligneCommande.setPrixUnitaire(prixUnitaire);

                        
                        
                        //insert data into ArrayList result
                        result.add(ligneCommande);
                        System.out.println("les commandes : "+result.toString());
                       
                
                    }
                    
                }catch(Exception ex) {
                    
                    ex.printStackTrace();
                }
            
            }
        });
        
      NetworkManager.getInstance().addToQueueAndWait(req);
       
        return result;
        
        
    }
    
}
