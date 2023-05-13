/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.esprit.entites.Reponse;
import com.esprit.services.ReponseService;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class ListReponse extends BaseForm{
    private int likeCount = 0;
    Form current;
    public ListReponse(Resources res){
        super("Reponse",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
        
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        addSideMenu(res);
        getContentPane().setScrollVisible(false);
        //Add "Ajouter" button
        Button addButton = new Button("Ajouter");
        addButton.addActionListener(e -> new AjoutReponse(res).show());
        
        ButtonGroup barGroup = new ButtonGroup();
        
        RadioButton all = RadioButton.createToggle("", barGroup);
       
        all.setUIID("SelectBar");
       
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(1, all)
                
        ));
        
        
        ArrayList<Reponse> list = ReponseService.getInstance().afficheRec();
        for(Reponse r : list){
            addButton(r , res);
        }
        add(addButton);
    }

    private void addButton( Reponse r, Resources res) {
            
            
            Container cnt = new Container(new BorderLayout());
            Label dateTxt = new Label("Date : " +r.getDate(),"NewsTopLine2");
            Label descTxt = new Label("Description : " +r.getDesc(),"NewsTopLine2");
            //Label prenomconducteurTxt = new Label("Prenom Conducteur : " +prenomconducteur,"NewsTopLine2");
            //Label datecTxt = new Label("Date Creation : " +date_creation,"NewsTopLine2");
            //Label datemTxt = new Label("Date Modification : " +date_modification,"NewsTopLine2");
            
            Button deleteBtn = new Button();
            deleteBtn.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DELETE, deleteBtn.getStyle()));
            deleteBtn.addPointerPressedListener(e -> {
                Dialog dig = new Dialog("Suppression");
                if(dig.show("Suppression","Vous voulez supprimer cette Reponse ?","Annuler","Oui")) {
                    dig.dispose();
                } else {
                    dig.dispose();

                    if(ReponseService.getInstance().deleteRec(r.getId())) {
                        new ListReponse(res).show();
                    }
                }
                
            });
               
            Button editBtn = new Button();
            editBtn.setIcon(FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, editBtn.getStyle()));
            editBtn.addPointerPressedListener(e -> {
                new ModifierReponse(res,r).show();
            });
            /*
            Button showBtn = new Button();
            showBtn.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DETAILS, editBtn.getStyle()));
            showBtn.addPointerPressedListener(e -> {
                //new ReponseDetailsForm(res,r).show();
            });
*/
        Style style = cnt.getStyle();
        style.setBgColor(0xFF0000);
        style.setFgColor(0x0000Ff);
        style.setBorder(Border.createLineBorder(5));
        cnt.setUIID("vehiculeContainer");
        //Theme currentTheme =  UIManager.initFirstTheme("/theme");
        cnt.add(BorderLayout.CENTER, 
                BoxLayout.encloseY(
                        dateTxt,
                        
                        descTxt,
                BoxLayout.encloseX(
                        deleteBtn,
                        editBtn
                        ),
                createLineSeparator(0xFF0000)
                   
               
                
            ));
        

            add(cnt);
            refreshTheme();
    }
}
