/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.esprit.entites.Reclamation;
import com.esprit.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author ASUS
 */
public class ReclamationService {
    
    public static ReclamationService instance = null ;

    public static boolean resultOk;

    //initilisation connection request 
    private ConnectionRequest req;

    public static ReclamationService getInstance() {
        if(instance == null )
            instance = new ReclamationService();
        return instance ;
    }

    public ReclamationService() {
        req = new ConnectionRequest();
    }
    
    
    
    //ajout 
    public void ajoutRec(Reclamation rec) {
        
       String url =Statics.BASE_URL+"/add/reclamation"+"?description_reclamation="+rec.getDesc()+
                "&date_reclamtion="+rec.getDate()+"&type_reclamation="+rec.getType();
       System.out.println(url);
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData()); // Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req); // execution ta3 request sinon yet3ada chy dima nal9awha
    }
    
     //affichage
    public ArrayList<Reclamation>afficheRec() {
        ArrayList<Reclamation> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/all/reclamation";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object> mapVehicule = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    if (mapVehicule.containsKey("root")) {
                        List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapVehicule.get("root");
                        for(Map<String, Object> obj : listOfMaps) {
                            Reclamation c = new Reclamation();
                        
                        //dima id fi codename one float 5outhouha
                        float id = Float.parseFloat(obj.get("id").toString());
                        //float constat_id = Float.parseFloat(obj.get("constat_id").toString());

                        //String dateaccident = obj.get("dateaccident").toString();
                        String date = obj.get("date_Reclamtion").toString();
                        String type = obj.get("type_reclamation").toString();
                        String desc = obj.get("descriptionReclamation").toString();
                        
                        //String numerodetelephone = obj.get("numerodetelephone") != null ? obj.get("immatriculation").toString() : "";


                        //String adresse = obj.get("adresse").toString();
                        //String immatriculation = obj.get("immatriculation").toString();
                                                //Date 
                        //String accident = "";
                        //String dateModification = "";
                        //String con ="";
                        //String accidentTime = obj.get("datereponse").toString().substring(0  , obj.get("datereponse").toString().indexOf("T"));
                        //String conTime = obj.get("dateconstat").toString().substring(0  , obj.get("dateconstat").toString().indexOf("T"));

//String modificationTime = obj.get("date_modification").toString().substring(obj.get("date_modification").toString().indexOf("timestamp") + 10 , obj.get("date_modification").toString().lastIndexOf("}"));
                        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                        //Date parsedDate = formatter.parse(accidentTime);
                        //Date parsedcon = formatter.parse(conTime);
                        //Date modificationDate = new Date(Long.parseLong(modificationTime) * 1000);
                        //long unixTimestamp = parsedDate.getTime() / 1000;
                        // creationDate = new Date(unixTimestamp * 1000);
                        //accident = formatter.format(parsedDate);
                        //con = formatter.format(parsedcon);
                        //System.out.println("accident = "+accident);
                        //System.out.println("obj = "+obj.get("dateaccident").toString());

                        //dateModification = formatter.format(modificationDate);

                        //bg.setDateCreation(dateCreation);
                        //bg.setDateModification(dateModification);
                        
                        c.setId((int)id);
                        c.setDate(date);
                        c.setDesc(desc);
                        c.setType(type);
                        
                        
                        System.out.println(c.toString());
                        result.add(c);
                        }
                    } else {
                        System.out.println("Error: 'root' key not found in JSON response");
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
    public boolean deleteRec(int id ) {
        String url = Statics.BASE_URL +"/delete/reclamation/"+id;
        
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
    public boolean ModifierRec(Reclamation r) {
        
         
             
             String url = Statics.BASE_URL+"/edit/reclamation/"+r.getId()+"?description_reclamation="+r.getDesc()+"&date_reclamtion="+r.getDate()+"&type_reclamation="+r.getType();
             
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
    
     //Detail blog bensba l detail n5alihoa lel5r ba3d delete+update
    public Reclamation DetailRec( int id , Reclamation c) {
        String url = Statics.BASE_URL+"/one/reclamation/"+id;
        req.setUrl(url);
        
        String str  = new String(req.getResponseData());

        req.addResponseListener(((evt) -> {
        
            JSONParser jsonp = new JSONParser();
            try {
                Map<String,Object>obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));
                

                        String date = obj.get("date_Reclamtion").toString();
                        String type = obj.get("type_reclamation").toString();
                        String desc = obj.get("descriptionReclamation").toString();
                        
                        
                        c.setId((int)id);
                        c.setDate(date);
                        c.setDesc(desc);
                        c.setType(type);
                        System.out.println(c.toString());
                
                


            }catch(IOException ex) {
                System.out.println("error related to sql :( "+ex.getMessage());
            }

            System.out.println("data === "+str);
        }));
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        return c;
    }
    
}
