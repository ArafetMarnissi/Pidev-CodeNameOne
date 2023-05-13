/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.esprit.entites.Reponse;
import com.esprit.services.ReponseService;

        

/**
 *
 * @author ASUS
 */
public class AjoutReponse extends BaseForm{
    Form current;
    public AjoutReponse(Resources res) {
        super("Ajouter Reponse",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        //setTitle("Ajout Vehicule");
        getContentPane().setScrollVisible(false);
        
        //addSideMenu(res);

        
        
        
        TextField desc = new TextField("", "desc");
        desc.setUIID("TextFieldBlack");
        addStringValue("Description :",desc);
        
        Picker date = new Picker();
        addStringValue("Date Reponse :",date);

        
        /*
        TextField date = new TextField("", "Date Reponse
                ");
        date.setUIID("TextFieldBlack");
        addStringValue("Date Reponse
                :",date);
        
        
        TextField idresponse = new TextField("", "id reponse");
        idresponse.setUIID("TextFieldBlack");
        addStringValue("Id Reponse :",idresponse);
        */
        
       
        
        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(e -> new ListReponse
        (res).show());
        
        Button btnAjouter = new Button("Ajouter");
        
        //onclick button event 
        btnAjouter.addActionListener((e) -> {
            try {
                
                if(desc.getText().equals("") || date == null  ) {
                    Dialog.show("Veuillez vérifier les données","","Annuler", "OK");
                }else{
                    System.out.println("Je commence !");
                    InfiniteProgress ip = new InfiniteProgress(); //Loading  after insert data
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    
                    //njibo iduser men session (current user)
                    Reponse
                            c = new Reponse();
                    c.setDesc(desc.getText());
                   
                    c.setDate(date.getText());
                    
                    System.out.println("data  Reponse : "+c);

                    //appelle methode ajouter mt3 service bch nzido données ta3na fi base 
                    ReponseService.getInstance().ajoutRec(c);
                    
                    iDialog.dispose(); //na7io loading ba3d ma3mlna ajout
                    //ba3d l ajout nemchiw lel affichage
                    new ListReponse(res).show();

                    refreshTheme();//Actualisation
                }
            }catch(Exception ex ) {
                ex.printStackTrace();
            }
        });
        
        add(BoxLayout.encloseY(
        btnAnnuler,
        btnAjouter
));

    }


    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
        .add(BorderLayout.CENTER,v));
        add(createLineSeparator(0xeeeeee));
    }
}
