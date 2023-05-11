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
import com.mycompany.entities.Abonnement;
import com.mycompany.utils.Statics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.IOException;

/**
 *
 * @author saifz
 */
public class ServiceAbonnement {
    
    //singleton
    public static ServiceAbonnement instance= null;
    public static boolean resultOk = true;
    
    //initialisation connection request
    private ConnectionRequest req;
    
    
    
    public static ServiceAbonnement getInstance(){
        if(instance==null)
            instance = new ServiceAbonnement();
        return instance ; 
    }
    
    
    public ServiceAbonnement(){
        req= new ConnectionRequest();
    }
    
    //ajout abonnement
    public void ajoutAbonnement(Abonnement abonnement){
        String url =Statics.BASE_URL+"/apiAddAbonnement?nomAbonnement="+abonnement.getNomAbonnement()+"&prixAbonnement="+abonnement.getPrixAbonnement()+"&dureeAbonnement="+abonnement.getDureeAbonnement();
        req.setUrl(url);
        req.addResponseListener((e)->{
            String str = new String(req.getResponseData());//reponse json
            System.out.println("data=="+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution request          
        
    
    }
    
    //affichage
    
    public ArrayList<Abonnement>affichageAbonnements(){
        ArrayList<Abonnement> result = new ArrayList<>();
        String url =Statics.BASE_URL+"/apiAbonnementlist";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapAbonnements = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapAbonnements.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        Abonnement re = new Abonnement();
                        
                        //dima id fi codename one float 5outhouha
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        String nomAbonnement = obj.get("nomAbonnement").toString();
                        
                        float prixAbonnement = Float.parseFloat(obj.get("prixAbonnement").toString());
                        String dureeAbonnement = obj.get("dureeAbonnement").toString();
                        
                        
                        re.setId((int)id);
                        re.setNomAbonnement(nomAbonnement);                     
                        re.setPrixAbonnement((float)prixAbonnement);
                        re.setDureeAbonnement(dureeAbonnement);
                        

                        
                        //insert data into ArrayList result
                        result.add(re);
                       
                    
                    }
                    
                }catch(Exception ex) {
                    
                    ex.printStackTrace();
                }
            
            }
        });
        
      NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

        return result;
        
        
    }
    
    
    public boolean deletAbonnement(int id ) {
        String url = Statics.BASE_URL +"/apiRemoveAbonnement/"+id;
        
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
    
    public boolean modifierAbonnement(Abonnement abonnement) {
        String url =Statics.BASE_URL+"/apiUpdateAbonnement/"+abonnement.getId()+"?nom_abonnement="+abonnement.getNomAbonnement()+"&prix_abonnement="+abonnement.getPrixAbonnement()+"&duree_abonnement="+abonnement.getDureeAbonnement();
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



