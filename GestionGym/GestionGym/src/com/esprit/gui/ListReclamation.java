/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.gui;

import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.esprit.entites.Reclamation;
import com.esprit.services.ReclamationService;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author ASUS
 */
public class ListReclamation extends BaseForm{
    private int likeCount = 0;
    Form current;
    public ListReclamation(Resources res){
        super("Reclamation",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
        
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        super.addSideMenu(res);
        getContentPane().setScrollVisible(false);
        /// debut bar         
        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
        
                
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();
        
        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        //RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        
        refreshTheme();
        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));
        
        ButtonGroup barGroup = new ButtonGroup();
        
        RadioButton all = RadioButton.createToggle("Reclamation", barGroup);
       
        all.setUIID("SelectBar");
       
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(1, all)
                
        ));
        /// end bar
        //Add "Ajouter" button
        Button addButton = new Button("Ajouter");
        addButton.addActionListener(e -> new AjoutReclamationForm(res).show());
        
        
        /*
        ArrayList<Reclamation> list = ReclamationService.getInstance().afficheRec();
        for(Reclamation r : list){
            addButton(r , res);
        }
        add(addButton);*/
        ArrayList<Reclamation> reclamations = ReclamationService.getInstance().afficheRec();
        ArrayList<Reclamation> todayReclamations = new ArrayList<>();
        String todayDateStr = new SimpleDateFormat("MM/dd/yy").format(new Date());
        //String todayDateStr = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        for (Reclamation r : reclamations) {
            if (r.getDate().equals(todayDateStr)) {
                todayReclamations.add(r);
            }
        }

        Container todayContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        todayContainer.add(new Label("Aujourd'hui"));
        for (Reclamation r : todayReclamations) {
            addButton(todayContainer, r, res);
        }

        Container allContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        allContainer.add(new Label("Autres"));
        for (Reclamation r : reclamations) {
            if (!todayReclamations.contains(r)) {
                addButton(allContainer, r, res);
    }
}

add(todayContainer);
add(allContainer);
add(addButton);
    }
    
    private void addButton(Container container, Reclamation r, Resources res) {
    Container cnt = new Container(new BorderLayout());
    Label dateTxt = new Label("Date : " + r.getDate(), "NewsTopLine2");
    Label typeTxt = new Label("Type : " + r.getType(), "NewsTopLine2");
    Label descTxt = new Label("Description : " + r.getDesc(), "NewsTopLine2");

    Button deleteBtn = new Button();
    deleteBtn.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DELETE, deleteBtn.getStyle()));
    deleteBtn.addPointerPressedListener(e -> {
        Dialog dig = new Dialog("Suppression");
        if (dig.show("Suppression", "Vous voulez supprimer cette reclamation ?", "Annuler", "Oui")) {
            dig.dispose();
        } else {
            dig.dispose();
            ReclamationService.getInstance().deleteRec(r.getId());
            new ListReclamation(res).show();
            /*if (ReclamationService.getInstance().deleteRec(r.getId())) {
                new ListReclamation(res).show();
            }*/
        }

    });

    Button editBtn = new Button();
    editBtn.setIcon(FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, editBtn.getStyle()));
    editBtn.addPointerPressedListener(e -> {
        new ModifierReclamationForm(res, r).show();
    });

    Button showBtn = new Button();
    showBtn.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DETAILS, editBtn.getStyle()));
    showBtn.addPointerPressedListener(e -> {
        new ReclamationDetailsForm(res, r).show();
    });

    Style style = cnt.getStyle();
    style.setBgColor(0xFF0000);
    style.setFgColor(0x0000Ff);
    style.setBorder(Border.createLineBorder(5));
    cnt.setUIID("vehiculeContainer");
    cnt.add(BorderLayout.CENTER,
            BoxLayout.encloseY(
                    dateTxt,
                    typeTxt,
                    descTxt,
                    BoxLayout.encloseX(
                            deleteBtn,
                            editBtn,
                            showBtn),
                    createLineSeparator(0xFF0000)
            ));

    container.add(cnt);
    refreshTheme();
}
/*
    private void addButton( Reclamation r, Resources res) {
            
            
            Container cnt = new Container(new BorderLayout());
            Label dateTxt = new Label("Date : " +r.getDate(),"NewsTopLine2");
            Label typeTxt = new Label("Type : " +r.getType(),"NewsTopLine2");
            Label descTxt = new Label("Description : " +r.getDesc(),"NewsTopLine2");
            //Label prenomconducteurTxt = new Label("Prenom Conducteur : " +prenomconducteur,"NewsTopLine2");
            //Label datecTxt = new Label("Date Creation : " +date_creation,"NewsTopLine2");
            //Label datemTxt = new Label("Date Modification : " +date_modification,"NewsTopLine2");
            
            Button deleteBtn = new Button();
            deleteBtn.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DELETE, deleteBtn.getStyle()));
            deleteBtn.addPointerPressedListener(e -> {
                Dialog dig = new Dialog("Suppression");
                if(dig.show("Suppression","Vous voulez supprimer cette reclamation ?","Annuler","Oui")) {
                    dig.dispose();
                } else {
                    dig.dispose();

                    if(ReclamationService.getInstance().deleteRec(r.getId())) {
                        new ListReclamation(res).show();
                    }
                }
                
            });
               
            Button editBtn = new Button();
            editBtn.setIcon(FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, editBtn.getStyle()));
            editBtn.addPointerPressedListener(e -> {
                new ModifierReclamationForm(res,r).show();
            });
            
            Button showBtn = new Button();
            showBtn.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DETAILS, editBtn.getStyle()));
            showBtn.addPointerPressedListener(e -> {
                new ReclamationDetailsForm(res,r).show();
            });

        Style style = cnt.getStyle();
        style.setBgColor(0xFF0000);
        style.setFgColor(0x0000Ff);
        style.setBorder(Border.createLineBorder(5));
        cnt.setUIID("vehiculeContainer");
        //Theme currentTheme =  UIManager.initFirstTheme("/theme");
        cnt.add(BorderLayout.CENTER, 
                BoxLayout.encloseY(
                        dateTxt,
                        typeTxt,
                        descTxt,
                BoxLayout.encloseX(
                        deleteBtn,
                        editBtn,
                        showBtn),
                createLineSeparator(0xFF0000)
                   
               
                
            ));
        

            add(cnt);
            refreshTheme();
    }*/
    
}
