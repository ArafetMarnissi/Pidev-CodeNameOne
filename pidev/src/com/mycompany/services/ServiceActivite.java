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
import com.mycompany.entities.Activite;
import com.mycompany.entities.Coach;
import com.mycompany.myapp.SessionManager;
import com.mycompany.utils.fich;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author mmarr
 */
public class ServiceActivite {
    
    public static ServiceActivite instance = null;
    public static boolean resultOk = true;
    private ConnectionRequest req;
    
    public static ServiceActivite getInstance(){
        if(instance == null)
            instance = new ServiceActivite();
        return instance;
    }
    
    public ServiceActivite(){
        req = new ConnectionRequest();
    }
    
    public void ajoutActivite(Activite ac){
        String url = fich.BASE_URL+"/addActiviteMobile?nomAcitivite="+ac.getNomActivite()+"&descriptionActivite="+ac.getDescriptionActivite()+"&nbrePlace="+ac.getNbrePlace()+"&DateActivite="+ac.getDateActivite();
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("data == "+str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
        public void ajoutPart(int id){
        String url = fich.BASE_URL+"/addParticipationMobile/get/"+id+"/"+SessionManager.getId();
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("data == "+str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
        
             public boolean deletePart(int id ) {
        String url = fich.BASE_URL +"/deleteParticipationUMobile/"+id;
        
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
    
    public ArrayList<Activite>affichageActivite(){
        ArrayList<Activite> result = new ArrayList<>();
        
        String url = fich.BASE_URL+"/affichageActiviteMobile";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                
                try{
                    Map<String,Object>mapActivite = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                   List<Map<String,Object>> listM = (List<Map<String,Object>>) mapActivite.get("root");
                   
                   for(Map<String, Object> obj : listM){
                       Activite a = new Activite();
                       float id = Float.parseFloat(obj.get("id").toString());
                       String nomActivite = obj.get("nomAcitivite").toString();
                       String descriptionActivite = obj.get("descriptionActivite").toString();
                       float nbrePlace = Float.parseFloat(obj.get("nbrePlace").toString());
                       String Image = obj.get("Image").toString();
                       
                       a.setId((int)id);
                       a.setNomActivite(nomActivite);
                       a.setDescriptionActivite(descriptionActivite);
                       a.setnbrePlace((int)nbrePlace);
                       a.setImage(Image);
                       
                       result.add(a);
                   }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            
        });
            NetworkManager.getInstance().addToQueueAndWait(req);
            return result;
    }
    
        public ArrayList<Coach>affichageCoach(){
        ArrayList<Coach> result = new ArrayList<>();
        
        String url = fich.BASE_URL+"/affichageCoachMobile";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                
                try{
                    Map<String,Object>mapActivite = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                   List<Map<String,Object>> listM = (List<Map<String,Object>>) mapActivite.get("root");
                   
                   for(Map<String, Object> obj : listM){
                       Coach a = new Coach();
                       float id = Float.parseFloat(obj.get("id").toString());
                       String nomActivite = obj.get("nomCoach").toString();
                       //String descriptionActivite = obj.get("descriptionActivite").toString();
                       float nbrePlace = Float.parseFloat(obj.get("ageCoach").toString());
                       String Image = obj.get("Image").toString();
                       
                       a.setId((int)id);
                       a.setNom_coach(nomActivite);
                       //a.setDescriptionActivite(descriptionActivite);
                       a.setAge_coach((int)nbrePlace);
                       a.setImage(Image);
                       
                       result.add(a);
                   }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            
        });
            NetworkManager.getInstance().addToQueueAndWait(req);
            return result;
    }
        
        public ArrayList<Activite>affichageactsbycoach(int id){
        ArrayList<Activite> result = new ArrayList<>();


        String url = fich.BASE_URL+"/ActsByCoach/"+id;
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    JSONParser jsonp;
                    jsonp = new JSONParser();

                    try {
                        Map<String,Object>mapcategory = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                        //System.out.println(mapcategory);
                        List<Map<String,Object>> listofmap = (List<Map<String,Object>>) mapcategory.get("root");
                        
                        for(Map<String,Object>obj:listofmap){
                       Activite a = new Activite();
                       float id = Float.parseFloat(obj.get("id").toString());
                       String nomActivite = obj.get("nomAcitivite").toString();
                       String descriptionActivite = obj.get("descriptionActivite").toString();
                       float nbrePlace = Float.parseFloat(obj.get("nbrePlace").toString());
                       String Image = obj.get("Image").toString();
                       
                       a.setId((int)id);
                       a.setNomActivite(nomActivite);
                       a.setDescriptionActivite(descriptionActivite);
                       a.setnbrePlace((int)nbrePlace);
                       a.setImage(Image);
                       
                       result.add(a);
                        }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
        }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }
        
      public ArrayList<Activite> findpartbyid(int id){
         ArrayList<Activite> result = new ArrayList<>();


        String url = fich.BASE_URL+"/enable/"+id+"/"+SessionManager.getId();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    JSONParser jsonp;
                    jsonp = new JSONParser();
                   

                    try {
                        Map<String,Object>mapcategory = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                        System.out.println(mapcategory);
                        List<Map<String,Object>> listofmap = (List<Map<String,Object>>) mapcategory.get("root");
                        //System.out.println("????????????????????????????????????");
                        //System.out.println(mapcategory.get("id").toString());
                        //System.out.println("????????????????????????????????????");
                        //for(Map<String,Object>obj:listofmap){
                       Activite a = new Activite();
                       /*float id = Float.parseFloat(obj.get("id").toString());
                       String nomActivite = obj.get("nomAcitivite").toString();
                       String descriptionActivite = obj.get("descriptionActivite").toString();
                       float nbrePlace = Float.parseFloat(obj.get("nbrePlace").toString());
                       String Image = obj.get("Image").toString();*/
                       boolean ena = Boolean.parseBoolean(mapcategory.get("en").toString());
                      float id1 = Float.parseFloat(mapcategory.get("id").toString());
                       
                      /* a.setId((int)id);
                       a.setNomActivite(nomActivite);
                       a.setDescriptionActivite(descriptionActivite);
                       a.setnbrePlace((int)nbrePlace);
                       a.setImage(Image);*/
                       a.setEn(ena);
                       a.setId_p((int)id1);
                       
                       result.add(a);
                       //}

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
        }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }    
      
       public ArrayList<Activite> getQrCode(int id){
         ArrayList<Activite> result = new ArrayList<>();


        String url = fich.BASE_URL+"/qrcodeMobile/"+id;
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    JSONParser jsonp;
                    jsonp = new JSONParser();
                   

                    try {
                        Map<String,Object>mapcategory = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                        System.out.println(mapcategory);
                        List<Map<String,Object>> listofmap = (List<Map<String,Object>>) mapcategory.get("root");
                        System.out.println("????????????????????????????????????");
                       System.out.println(mapcategory.get("Image").toString());
                        System.out.println("????????????????????????????????????");
                        //for(Map<String,Object>obj:listofmap){
                       Activite a = new Activite();
                       /*float id = Float.parseFloat(obj.get("id").toString());
                       String nomActivite = obj.get("nomAcitivite").toString();
                       String descriptionActivite = obj.get("descriptionActivite").toString();
                       float nbrePlace = Float.parseFloat(obj.get("nbrePlace").toString());
                       String Image = obj.get("Image").toString();*/
                       float id = Float.parseFloat(mapcategory.get("id").toString());
                      String qr = mapcategory.get("qr").toString();
                      String Image = mapcategory.get("Image").toString();
                       
                      /* a.setId((int)id);
                       a.setNomActivite(nomActivite);
                       a.setDescriptionActivite(descriptionActivite);
                       a.setnbrePlace((int)nbrePlace);
                       a.setImage(Image);*/
                      a.setId((int)id);
                       a.setQrcode(qr);
                       a.setImage(Image);
                       //a.setId_p((int)id1);
                       
                       result.add(a);
                       //}

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
        }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }    
    
    public Activite detailActivite(int id, Activite ac){
        String url = fich.BASE_URL+"/detailActivite?"+id;
        req.setUrl(url);
        
        String str = new String(req.getResponseData());
        req.addResponseListener(((evt) -> {
            JSONParser jsonp = new JSONParser();
            try{
                Map<String,Object>obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));
                ac.setNomActivite(obj.get("nomAcitivite").toString());
                ac.setDescriptionActivite(obj.get("descriptionActivite").toString());
                ac.setnbrePlace((int)obj.get("nbrePlace"));
            }catch(IOException ex){
                System.out.println("erreur "+ex.getMessage());
            }
            System.out.println("data === "+str);
        }));
         NetworkManager.getInstance().addToQueueAndWait(req);
         return ac;
    }
    
     public boolean deleteActivite(int id ) {
        String url = fich.BASE_URL +"/deleteactiviteMobile?id="+id;
        
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
     
         public boolean modifierActivite(Activite a) {
        String url = fich.BASE_URL +"/modifieractiviteMobile?id="+a.getId()+"&nomAcitivite="+a.getNomActivite()+"&descriptionActivite="+a.getDescriptionActivite()+"&nbrePlace="+a.getNbrePlace();
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
