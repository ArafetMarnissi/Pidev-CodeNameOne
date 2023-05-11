/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycomany.entities.Abonnement;
import com.mycompany.services.ServiceAbonnement;
import java.text.SimpleDateFormat;

/**
 *
 * @author saifz
 */
public class ModifierAbonnementForm extends BaseForm{
    
    
    Form current;
    public ModifierAbonnementForm(Resources res,Abonnement a){
      super("Newsfeed",BoxLayout.y());
        
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("container");
        setTitle("Ajout Abonnement");
        getContentPane().setScrollVisible(false);
        
         super.addSideMenu(res);
        
        TextField objet = new TextField(a.getNomAbonnement() , "Nom" , 20 , TextField.ANY);
        //TextField description = new TextField(a.getPrixAbonnement(), "Description" , 20 , TextField.NUMERIC);
        TextField dureeA = new TextField(String.valueOf(a.getDureeAbonnement()) , "Durée d abonnement" , 20 , TextField.ANY);
        TextField prixA = new TextField(String.valueOf(a.getPrixAbonnement()) , "Prix abonnement" , 20 , TextField.NUMERIC);
        //TextField datep = new TextField(a.getDateActivite() , "Date" , 20 , TextField.ANY);
        
         objet.setUIID("NewsTopLine");
        //description.setUIID("NewsTopLine");
        dureeA.setUIID("NewsTopLine");
        prixA.setUIID("NewsTopLine");
        
        
        objet.setSingleLineTextArea(true);
        prixA.setSingleLineTextArea(true);
        dureeA.setSingleLineTextArea(true);
        
        
         Button btnModifier = new Button("Modifier");
       btnModifier.setUIID("Button");
       
       //Event onclick btnModifer
       
       btnModifier.addPointerPressedListener(l ->   { 
           
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
           a.setNomAbonnement(objet.getText());
           a.setPrixAbonnement(Float.parseFloat(prixA.getText()));                          //Float.parseFloat(prixAbonnement.getText())
           a.setDureeAbonnement(dureeA.getText());
           if(ServiceAbonnement.getInstance().modifierAbonnement(a)) { // if true
           new AfficheAbonnementForm(res).show();
                }
           });    
       Button btnAnnuler = new Button("Annuler");
       btnAnnuler.addActionListener(e -> {
           new AfficheAbonnementForm(res).show();
       });
       
        Label l2 = new Label("");
       
       Label l3 = new Label("");
       
       Label l4 = new Label("");
       
       Label l5 = new Label("");
       
        Label l1 = new Label();
        
        Container content = BoxLayout.encloseY(
                l1, l2, l3,l4,
                new FloatingHint(objet),
                createLineSeparator(),
                //new FloatingHint(description),
                createLineSeparator(),
                new FloatingHint(dureeA),
                createLineSeparator(),//ligne de séparation
               
               
                btnModifier,
                btnAnnuler
                
               
        );
        
        add(content);
        show();
    }    
    
}
