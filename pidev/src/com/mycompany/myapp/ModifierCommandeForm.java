/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Commande;
import com.mycompany.services.ServiceCommande;
import java.util.Vector;


/**
 *
 * @author Lenovo
 */
public class ModifierCommandeForm extends BaseForm {
    
    Form current;
    public ModifierCommandeForm(Resources res , Commande c) {
         super("Newsfeed",BoxLayout.y()); 
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Modifier Commande");
        getContentPane().setScrollVisible(false);
        
        
        super.addSideMenu(res);
        
        TextField adresse_livraison = new TextField(c.getAdresse_livraison() , "adreese de livraison" , 20 , TextField.ANY);
                          
        //Vector  lel comboBox
        Vector<String> vectorPaiement;
        vectorPaiement = new Vector();
        
        vectorPaiement.add("à la livraison");
        vectorPaiement.add("chèque");
        vectorPaiement.add("carte bancaire");
        
        ComboBox<String>methode_paiement = new ComboBox<>(vectorPaiement);
        
       //addStringValue("methode_paiement",methode_paiement);
        
        /*
        TextField methode_paiement = new TextField(c.getMethode_paiement(), "méthode de paiement" , 20 , TextField.ANY);
        */
        TextField telephone = new TextField(String.valueOf(c.getTelephone()) , "votre numero de telephone" , 20 , TextField.ANY);
 
        //etat bch na3mlo comobbox bon lazm admin ya3mlleha approuver mais just chnwarikom ComboBox
/*        
        ComboBox etatCombo = new ComboBox();
        
        etatCombo.addItem("Non Traiter");
        
        etatCombo.addItem("Traiter");
        
        if(r.getEtat() == 0 ) {
            etatCombo.setSelectedIndex(0);
        }
        else 
            etatCombo.setSelectedIndex(1);
      */  
        
        
        
        
        adresse_livraison.setUIID("NewsTopLine");
        methode_paiement.setUIID("NewsTopLine");
        telephone.setUIID("NewsTopLine");
        
        adresse_livraison.setSingleLineTextArea(true);
        methode_paiement.setSelectedItem(c.getMethode_paiement());
        //methode_paiement.setSingleLineTextArea(true);
        telephone.setSingleLineTextArea(true);
        
        Button btnModifier = new Button("Modifier");
       btnModifier.setUIID("Button");
       
       //Event onclick btnModifer
       
       btnModifier.addPointerPressedListener(l ->   { 
           
           c.setAdresse_livraison(adresse_livraison.getText());
           c.setMethode_paiement(methode_paiement.getSelectedItem());
           c.setTelephone(Integer.parseInt(telephone.getText()));
            
      
       
       //appel de service affichage des commandes
       
        if(ServiceCommande.getInstance().modifierCommande(c)) { 
           new ListCommandeForm(res).show();
       }
        });
       Button btnAnnuler = new Button("Annuler");
       btnAnnuler.addActionListener(e -> {
           new ListCommandeForm(res).show();
       });
       
       
       Label l2 = new Label("");
       
       Label l3 = new Label("");
       
       Label l4 = new Label("");
       
       Label l5 = new Label("");
       
        Label l1 = new Label();
        
        Container content = BoxLayout.encloseY(
                l1, l2, 
                new FloatingHint(adresse_livraison),
                createLineSeparator(),
                methode_paiement,
                //new FloatingHint(methode_paiement),
                createLineSeparator(),
                telephone,
                createLineSeparator(),//ligne de séparation
                btnModifier,
                btnAnnuler
                
               
        );
        
        add(content);
        show();
        
        
    }
}
