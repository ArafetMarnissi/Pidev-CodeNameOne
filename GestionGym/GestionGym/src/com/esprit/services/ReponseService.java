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
import com.esprit.entites.Reponse;
import com.esprit.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ASUS
 */
public class ReponseService {
    public static ReponseService instance = null ;

    public static boolean resultOk;

    //initilisation connection request 
    private ConnectionRequest req;

    public static ReponseService getInstance() {
        if(instance == null )
            instance = new ReponseService();
        return instance ;
    }

    public ReponseService() {
        req = new ConnectionRequest();
        req.setCheckSSLCertificates(false);
        req.setInsecure(true);
    }
    
    
    
    //ajout 
    public void ajoutRec(Reponse rec) {
        
       String url =Statics.BASE_URL+"/add/reponse"+"?descriptionreponse="+rec.getDesc()+
                "&dateReponse="+rec.getDate();
       System.out.println(url);
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData()); // Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req); // execution ta3 request sinon yet3ada chy dima nal9awha
    }
    
     //affichage
    public ArrayList<Reponse>afficheRec() {
        ArrayList<Reponse> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/all/reponse";
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
                            Reponse c = new Reponse();
                        
                        //dima id fi codename one float 5outhouha
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        String date = obj.get("dateReponse").toString();
                        
                        String desc = obj.get("descriptionreponse").toString();
                        
                        c.setId((int)id);
                        c.setDate(date);
                        c.setDesc(desc);
                        
                        
                        
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
        String url = Statics.BASE_URL +"/delete/reponse/"+id;
        
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
    public boolean ModifierRec(Reponse r) {
        
         
             
             String url = Statics.BASE_URL+"/edit/reponse/"+r.getId()+"?descriptionreponse="+r.getDesc()+"&dateReponse="+r.getDate();
             
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
    public Reponse DetailRec( int id , Reponse c) {
        String url = Statics.BASE_URL+"/one/reponse/"+id;
        req.setUrl(url);
        
        String str  = new String(req.getResponseData());

        req.addResponseListener(((evt) -> {
        
            JSONParser jsonp = new JSONParser();
            try {
                Map<String,Object>obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));
                

                        String date = obj.get("date_Reclamtion").toString();
                        
                        String desc = obj.get("descriptionReponse").toString();
                        
                        
                        c.setId((int)id);
                        c.setDate(date);
                        c.setDesc(desc);
                        
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
