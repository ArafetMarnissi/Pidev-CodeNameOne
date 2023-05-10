/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Category;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.BorderLayout;
import com.mycompany.services.servicecategory;

/**
 *
 * @author damma
 */
public class modifercategorieForm extends BaseForm {
    
    Form current;
    public modifercategorieForm(Resources res,Category cat){
        super("Newsfeed",BoxLayout.y());
        
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("container");
        setTitle("Modifier");
        getContentPane().setScrollVisible(false);
      
        
        super.addSideMenu(res);
        
        TextField nomC =new TextField(cat.getNomCategory(),"Nom",40,TextField.ANY);
        TextField imgc =new TextField(cat.getImageCategorie(),"img",20,TextField.ANY);

        nomC.setUIID("NewsTopLine");
        imgc.setUIID("NewsTopLine");
        
        nomC.setSingleLineTextArea(true);
        imgc.setSingleLineTextArea(true);
        
        Button btnmodifier=new Button("modifier");
        btnmodifier.setUIID("Button");
        //addStringValue("", btnmodifier);
        
        btnmodifier.addPointerPressedListener(l -> {
            
            cat.setNomCategory(nomC.getText());
            if(imgc.getText()==cat.getImageCategorie())
            {cat.setImageCategorie(imgc.getText());}
            else{
                cat.setImageCategorie(imgc.getText()+".png");
            }
            
            

            if(servicecategory.getInstance().modifiercategorie(cat)){
                new ListcategoryForm(res).show();
            }
            });
            Button btnannuler=new Button("Annuler");
            btnannuler.addActionListener(e -> {
                new ListcategoryForm(res).show();
            });
            
            Label l1= new Label();
            Label l2= new Label("");
            Label l3= new Label("");
            Label l4= new Label("");
            
            Container content=BoxLayout.encloseY(
            l1,l2,l3,l4,
                    new FloatingHint(nomC),
            createLineSeparator(),
            new FloatingHint(imgc),
            btnmodifier,
            btnannuler
            );
            
            add(content);
            show();
                    
        
        

    }

    
    
}
