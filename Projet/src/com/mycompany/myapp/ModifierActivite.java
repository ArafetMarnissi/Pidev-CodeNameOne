/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

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
import com.mycompany.entities.Activite;
import com.mycompany.services.ServiceActivite;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 *
 * @author mmarr
 */
public class ModifierActivite extends BaseForm{
    
    Form current;
    public ModifierActivite(Resources res,Activite a){
      super("Newsfeed",BoxLayout.y());
        
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("container");
        setTitle("Modif Activite");
        getContentPane().setScrollVisible(false);
        
         super.addSideMenu(res);
        
        TextField objet = new TextField(a.getNomActivite() , "Nom" , 20 , TextField.ANY);
        TextField description = new TextField(a.getDescriptionActivite(), "Description" , 20 , TextField.ANY);
        TextField nbreP = new TextField(String.valueOf(a.getNbrePlace()) , "Nombre de places" , 20 , TextField.NUMERIC);
        //TextField datep = new TextField(a.getDateActivite() , "Date" , 20 , TextField.ANY);
        
         objet.setUIID("NewsTopLine");
        description.setUIID("NewsTopLine");
        nbreP.setUIID("NewsTopLine");
        
        
        objet.setSingleLineTextArea(true);
        description.setSingleLineTextArea(true);
        nbreP.setSingleLineTextArea(true);
        
        
         Button btnModifier = new Button("Modifier");
       btnModifier.setUIID("Button");
       
       //Event onclick btnModifer
       
       btnModifier.addPointerPressedListener(l ->   { 
           
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
           a.setNomActivite(objet.getText());
           a.setDescriptionActivite(description.getText());
           a.setnbrePlace(Integer.parseInt(nbreP.getText()));
                          if(ServiceActivite.getInstance().modifierActivite(a)) { // if true
            try {
                      new AffichageActivite(res).show();
                  } catch (IOException ex) {
                      System.out.println(ex.getMessage());
                  }
                    refreshTheme();
       }
           });    
       Button btnAnnuler = new Button("Annuler");
       btnAnnuler.addActionListener(e -> {
           try {
                      new AffichageActivite(res).show();
                  } catch (IOException ex) {
                      System.out.println(ex.getMessage());
                  }
                    refreshTheme();
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
                new FloatingHint(description),
                createLineSeparator(),
                new FloatingHint(nbreP),
                createLineSeparator(),//ligne de séparation
               
               
                btnModifier,
                btnAnnuler
                
               
        );
        
        add(content);
        show();
    }
}
