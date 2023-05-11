/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package com.mycompany.myapp;

import com.codename1.components.FloatingHint;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.User;
import com.mycompany.services.ServiceUser;

/**
 * Account activation UI
 *
 * @author Shai Almog
 */
public class ModifierUser extends BaseForm {

   Form current;
    public ModifierUser(Resources res,SessionManager a){
      super("Newsfeed",BoxLayout.y());
        
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("container");
        setTitle("Ajout Abonnement");
        getContentPane().setScrollVisible(false);
        
         super.addSideMenu(res);
        
        TextField objet = new TextField(a.getNom() , "Nom" , 20 , TextField.ANY);
        //TextField description = new TextField(a.getPrixAbonnement(), "Description" , 20 , TextField.NUMERIC);
        TextField prenom = new TextField(String.valueOf(a.getPrenom()) , "Prenom" , 20 , TextField.ANY);
        //TextField datep = new TextField(a.getDateActivite() , "Date" , 20 , TextField.ANY);
        
         objet.setUIID("NewsTopLine");
        //description.setUIID("NewsTopLine");
        prenom.setUIID("NewsTopLine");
        
        
        objet.setSingleLineTextArea(true);
        prenom.setSingleLineTextArea(true);
        
        
         Button btnModifier = new Button("Modifier");
       btnModifier.setUIID("Button");
       
       //Event onclick btnModifer
       
       btnModifier.addPointerPressedListener(l ->   { 
           
           
           
           a.setNom((objet.getText()));                          //Float.parseFloat(prixAbonnement.getText())
           a.setPrenom(prenom.getText());
           if(ServiceUser.getInstance().modifierUser(a)) { // if true
           new ProfileForm(res).show();
                }
           });    
       Button btnAnnuler = new Button("Annuler");
       btnAnnuler.addActionListener(e -> {
           new ProfileForm(res).show();
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
                new FloatingHint(prenom),
                createLineSeparator(),//ligne de séparation
               
               
                btnModifier,
                btnAnnuler
                
               
        );
        
        add(content);
        show();
    }    
    
}
