/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.esprit.entites.Reclamation;

/**
 *
 * @author ASUS
 */
public class ReclamationDetailsForm extends BaseForm {
    Form current;
    public ReclamationDetailsForm(Resources res, Reclamation c) {
        super("Details Reclamation",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
                    Container cnt = new Container(new BorderLayout());

        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        //setTitle("Ajout Vehicule");
        getContentPane().setScrollVisible(false);
        
        //
        Label dateTxt = new Label("Date Reclamation " +c.getDate(),"NewsTopLine2");
        Label typeTxt = new Label("Type : " +c.getType(),"NewsTopLine2");
        Label descTxt = new Label("Description : "+c.getDesc(),"NewsTopLine2");
        

        
       
        
        Button btnAnnuler = new Button("Retour");
        btnAnnuler.addActionListener(e -> new ListReclamation(res).show());
        
        Button btnModifier = new Button("Modifier");
        btnModifier.addActionListener(e -> new ModifierReclamationForm(res,c).show());
       
        
        
        Container buttonContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        buttonContainer.addAll(btnAnnuler, btnModifier);

        Container mainContainer = new Container(new BorderLayout());
        mainContainer.add(BorderLayout.CENTER, buttonContainer);

        // add the mainContainer to the form
        

  
        cnt.add(BorderLayout.CENTER, 
                BoxLayout.encloseY(
                        dateTxt,
                        typeTxt,
                        descTxt
                       
                   
               
                
            ));
        

            add(cnt);
            add(mainContainer);
    }
}
