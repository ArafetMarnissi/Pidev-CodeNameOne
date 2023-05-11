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
import com.mycompany.entities.Category;
import com.mycompany.utils.Statics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author damma
 */
public class servicecategory {

    public static servicecategory instance = null;
    
    public static boolean resultatok=true;

    private ConnectionRequest req;

    public static servicecategory getInstance(){
        if(instance == null)
            instance =new servicecategory();
        return instance;
    }

    public servicecategory(){
        req = new ConnectionRequest();
    }
    
    public ArrayList<Category>affichagecategory(){
        ArrayList<Category> result = new ArrayList<>();


        String url = Statics.BASE_URL+"/allcat";
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    JSONParser jsonp;
                    jsonp = new JSONParser();

                    try {
                        Map<String,Object>mapcategory = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                        
                        List<Map<String,Object>> listofmap = (List<Map<String,Object>>) mapcategory.get("root");
                        
                        for(Map<String,Object>obj:listofmap){
                            Category cat =new Category();
                            float id=Float.parseFloat(obj.get("id").toString());
                            String nomcat=obj.get("nomCategory").toString();
                            String imagecat=obj.get("imageCategorie").toString();
                            
                            cat.setId((int)id);
                            cat.setNomCategory(nomcat);
                            cat.setImageCategorie(imagecat);
                            
                            result.add(cat);
                        }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
        }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }
    
    public void ajoutercategorie(Category cat){
        String url=Statics.BASE_URL+"/addcatj?nomCategory="+cat.getNomCategory()+"&imageCategorie="+cat.getImageCategorie();
        req.setUrl(url);
        req.addResponseListener((e)->{
            
            String str=new String(req.getResponseData());
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    public boolean  deletecategorie(int id){
        String Url=Statics.BASE_URL+"/deletej?id="+id;
        
        req.setUrl(Url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultatok;
    }
    
    public boolean modifiercategorie(Category c){
        String url=Statics.BASE_URL+"/modifj?id="+c.getId()+"&nomCategory="+c.getNomCategory()+"&imageCategorie="+c.getImageCategorie();
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultatok= req.getResponseCode()==200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultatok;
        
    }

}
