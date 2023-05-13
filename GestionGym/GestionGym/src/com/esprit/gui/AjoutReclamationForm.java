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
import com.esprit.entites.Reclamation;
import com.esprit.services.ReclamationService;
import java.util.Date;

/**
 *
 * @author ASUS
 */
public class AjoutReclamationForm extends BaseForm{
    Form current;
    public AjoutReclamationForm(Resources res) {
        super("Ajouter Reclamation",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        //setTitle("Ajout Vehicule");
        getContentPane().setScrollVisible(false);
        
        addSideMenu(res);

        
        TextField type = new TextField("", "type de reclamation");
        type.setUIID("TextFieldBlack");
        addStringValue("Type :",type);
        
        TextField desc = new TextField("", "desc");
        desc.setUIID("TextFieldBlack");
        addStringValue("Description :",desc);
        
        Picker date = new Picker();
        addStringValue("Date Reclamation :",date);

        
        /*
        TextField date = new TextField("", "Date reclamation");
        date.setUIID("TextFieldBlack");
        addStringValue("Date Reclamation :",date);
        
        
        TextField idresponse = new TextField("", "id reponse");
        idresponse.setUIID("TextFieldBlack");
        addStringValue("Id Reponse :",idresponse);
        */
        
       
        
        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(e -> new ListReclamation(res).show());
        
        Button btnAjouter = new Button("Ajouter");
        
        //onclick button event 
        btnAjouter.addActionListener((e) -> {
            try {
                
                if(desc.getText().equals("") || date == null ||type.getText().equals("") ) {
                    Dialog.show("Veuillez vérifier les données","","Annuler", "OK");
                }else{
                    System.out.println("Je commence !");
                    InfiniteProgress ip = new InfiniteProgress(); //Loading  after insert data
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    
                    //njibo iduser men session (current user)
                    Reclamation c = new Reclamation();
                    c.setDesc(desc.getText());
                    c.setType(type.getText());
                    c.setDate(date.getText());
                    //c.setId_reponse(1);
                    //Date date = new Date();
                    //date.setDate(CENTER);
                        /*String.valueOf(titre.getText()).toString(),
                                  String.valueOf(contenu.getText()).toString(),
                                  format.format(new Date()),
                                  format.format(new Date()),
                                  String.valueOf(imageBlog.getText()).toString(),
                                  0);*/
                    //System.out.println("Adding a new Constat !! ");
                    System.out.println("data  Reclamation == "+c);

                    //appelle methode ajouter mt3 service bch nzido données ta3na fi base 
                    ReclamationService.getInstance().ajoutRec(c);
                    
                    iDialog.dispose(); //na7io loading ba3d ma3mlna ajout
                    //ba3d l ajout nemchiw lel affichage
                    new ListReclamation(res).show();
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
