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
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Category;
import com.mycompany.entities.Produit;
import com.mycompany.utils.Statics;
import java.io.IOException;
//import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author damma
 */
public class ProduitService {
    public static ProduitService instance = null;
    
    public static boolean resultatok=true;

    private ConnectionRequest req;

    public static ProduitService getInstance(){
        if(instance == null)
            instance =new ProduitService();
        return instance;
    }
    public ProduitService(){
        req = new ConnectionRequest();
    }
    
    public ArrayList<Produit>affichageproduit(int id){
        ArrayList<Produit> result = new ArrayList<>();


        String url = Statics.BASE_URL+"/prodcat/"+id;
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
                            Produit cat =new Produit();
                            float id=Float.parseFloat(obj.get("id").toString());
                            String nomcat=obj.get("nom").toString();
                            String imagecat=obj.get("imageProduit").toString();
                            String desc=obj.get("description").toString();
                            float pp=Float.parseFloat(obj.get("prixProduit").toString());
                            float ff=Float.parseFloat(obj.get("quantiteProduit").toString());
                            float nn=Float.parseFloat(obj.get("note").toString());
//                            String dd=obj.get("dateExpiration").toString();
//                            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
//                            java.util.Date utilDate = format.parse(dd);
                            
                            
                            
                            cat.setId((int)id);
                            cat.setNom(nomcat);
                            cat.setImage_produit(imagecat);
                            cat.setDescription(desc);
                            cat.setPrix_produit(pp);
                            cat.setQuantite_produit((int)ff);
                            cat.setNote(nn);
                            //cat.setDate_expiration(utilDate);
                            
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
    
   public Produit DetailRecalamation( int id , Produit reclamation) {
        
        String url = Statics.BASE_URL+"/detailprod/"+id;
        req.setUrl(url);
        
        String str  = new String(req.getResponseData());
        req.addResponseListener(((evt) -> {
        
            JSONParser jsonp = new JSONParser();
            try {
                
                Map<String,Object>obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));
                
                            float id1=Float.parseFloat(obj.get("id").toString());
                            String nomcat=obj.get("nom").toString();
                            String imagecat=obj.get("imageProduit").toString();
                            String desc=obj.get("description").toString();
                            float pp=Float.parseFloat(obj.get("prixProduit").toString());
                            float ff=Float.parseFloat(obj.get("quantiteProduit").toString());
                            float nn=Float.parseFloat(obj.get("note").toString());
                            Float cc=Float.parseFloat(obj.get("category").toString());
                
            }catch(IOException ex) {
                System.out.println("error related to sql :( "+ex.getMessage());
            }
            
            
            System.out.println("data === "+str);
            
            
            
        }));
        
              NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

              return reclamation;
        
        
    }

    
    
    
}
