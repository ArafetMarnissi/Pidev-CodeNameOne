/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;

import com.mycompany.entities.Commande;
import com.mycompany.services.ServiceCommande;
import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public class ListCommandeForm extends BaseForm{
    
    Form current;
    public ListCommandeForm(Resources res ) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Mes Commandes");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        
        
        Button cartButton = new Button("");
        cartButton.setUIID("NewsTopLine");
        Style cartStyle = new Style(cartButton.getUnselectedStyle());
        cartStyle.setFgColor(0xf21f1f);
        FontImage cartIcon = (FontImage) FontImage.createMaterial(FontImage.MATERIAL_SHOPPING_CART, cartStyle).scaled(100, 100);
        tb.addCommandToRightBar("", cartIcon, e -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();
        
             PanierForm a = new PanierForm(res);
             a.show();
             refreshTheme();
        });
        
        //tb.addSearchCommand(e -> {});
//        Button cartButton = new Button("");
//        cartButton.setUIID("NewsTopLine");
//        Style cartStyle = new Style(cartButton.getUnselectedStyle());
//        cartStyle.setFgColor(0xf21f1f);
//        FontImage cartImage = FontImage.createMaterial(FontImage.MATERIAL_ADD_SHOPPING_CART, cartStyle);
//        cartButton.setIcon(cartImage);
//        cartButton.setTextPosition(Component.RIGHT);
//        tb.addComponentToRightSideMenu(cartButton);

        
        Tabs swipe = new Tabs();
        
        Label s1 = new Label();
        Label s2 = new Label();
        
        addTab(swipe,s1, res.getImage("salle-back.jpg"),"","",res);
        
        // current user
      /*  
        System.out.println("user connecté id ="+ SessionManager.getId());
        
        
        
        System.out.println("user connecté username ="+ SessionManager.getUserName());
        
        System.out.println("user connecté password ="+ SessionManager.getPassowrd());
        
        System.out.println("user connecté email ="+ SessionManager.getEmail());
        */
        
        
        
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
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesCommandes = RadioButton.createToggle("Mes Commandes", barGroup);
        mesCommandes.setUIID("SelectBar");
        RadioButton Panier = RadioButton.createToggle("Panier", barGroup);
        Panier.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

///////////radio buuton pour afficher la liste des commandes
        mesCommandes.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
        
          ListCommandeForm a = new ListCommandeForm(res);
            a.show();
            refreshTheme();
        });
        ////panier
                Panier.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
        
          PanierForm a = new PanierForm(res);
            a.show();
            refreshTheme();
        });



        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(2, mesCommandes, Panier ),
                FlowLayout.encloseBottom(arrow)
        ));

        mesCommandes.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(mesCommandes, arrow);
        });
        bindButtonSelection(mesCommandes, arrow);


        
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        
      
        //Appel affichage methode
        ArrayList<Commande>list = ServiceCommande.getInstance().affichageCommandes();
        
        for(Commande com : list ) {
             String urlImage ="back.png";
            
             Image placeHolder = Image.createImage(120, 90);
             EncodedImage enc =  EncodedImage.createFromImage(placeHolder,false);
             URLImage urlim = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);
             
                addButton(urlim,com,res);
        
                ScaleImageLabel image = new ScaleImageLabel(urlim);
                
                Container containerImg = new Container();
                
                image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        }
        
        
        
    }
    
    
    
    
    
    
    
    
       private void addTab(Tabs swipe, Label spacer , Image image, String string, String text, Resources res) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        
        if(image.getHeight() < size) {
            image = image.scaledHeight(size);
        }
        
        
        
        if(image.getHeight() > Display.getInstance().getDisplayHeight() / 2 ) {
            image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        
        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
        Label overLay = new Label("","ImageOverlay");
        
        
        Container page1 = 
                LayeredLayout.encloseIn(
                imageScale,
                        overLay,
                        BorderLayout.south(
                        BoxLayout.encloseY(
                        new SpanLabel(text, "LargeWhiteText"),
                                        spacer
                        )
                    )
                );
        
        swipe.addTab("",res.getImage("back.png"), page1);
        
        
        
        
    }
    
    
    
    public void bindButtonSelection(Button btn , Label l ) {
        
        btn.addActionListener(e-> {
        if(btn.isSelected()) {
            updateArrowPosition(btn,l);
        }
    });
    }

    private void updateArrowPosition(Button btn, Label l) {
        
        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth()  / 2  - l.getWidth() / 2 );
        l.getParent().repaint();
    }

    private void addButton(Image img,Commande com , Resources res) {
        
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        
        
        
        Label DateTxt = new Label("Date Commande : "+com.getDate_Commande(),"NewsTopLine2");
        Label addresseTxt = new Label("AL : "+com.getAdresse_livraison(),"NewsTopLine2");
        Label MpaiementTxt = new Label("MP : "+com.getMethode_paiement(),"NewsTopLine2" );
        Label prixTxt = new Label("prix : "+com.getPrix_commande(),"NewsTopLine2" );
        
        addresseTxt.getAllStyles().setFgColor(0x000000);
        
        createLineSeparator();
 
       
        
        //supprimer button
        Label lSupprimer = new Label(" ");
        lSupprimer.setUIID("NewsTopLine");
        Style supprmierStyle = new Style(lSupprimer.getUnselectedStyle());
        supprmierStyle.setFgColor(0xf21f1f);
        
        FontImage suprrimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprmierStyle);
        lSupprimer.setIcon(suprrimerImage);
        lSupprimer.setTextPosition(RIGHT);
        
        //click delete icon
        lSupprimer.addPointerPressedListener(l -> {
            
            Dialog dig = new Dialog("Suppression");
            
            if(dig.show("Suppression","Vous voulez supprimer cette commande ?","Annuler","Oui")) {
                dig.dispose();
            }
            else {
                dig.dispose();
                 }
                //n3ayto l suuprimer men service Reclamation
                if(ServiceCommande.getInstance().deleteCommande(com.getId())) {
                    new ListCommandeForm(res).show();
                }
           
        });
       
        //Update icon 
        Label lModifier = new Label(" ");
        lModifier.setUIID("NewsTopLine");
        Style modifierStyle = new Style(lModifier.getUnselectedStyle());
        modifierStyle.setFgColor(0xf7ad02);
        
        FontImage mFontImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyle);
        lModifier.setIcon(mFontImage);
        lModifier.setTextPosition(RIGHT);
        
        
        lModifier.addPointerPressedListener(l -> {
            //System.out.println("hello update");
            new ModifierCommandeForm(res,com).show();
        });

        ///details Icon
                
        Label lDetais = new Label(" ");
        lDetais.setUIID("NewsTopLine");
        Style detailsStyle = new Style(lDetais.getUnselectedStyle());
        detailsStyle.setFgColor(0xf7ad02);
        
        FontImage DFontImage = FontImage.createMaterial(FontImage.MATERIAL_INFO, detailsStyle);
        lDetais.setIcon(DFontImage);
        lDetais.setTextPosition(LEFT);
        
        
        lDetais.addPointerPressedListener(l -> {
            //System.out.println("hello update");
            new ListLigneCommandeForm(res,com.getId()).show();
            
        });
        
        
        
        ////
        
        
        cnt.add(BorderLayout.WEST,BoxLayout.encloseY(
                
                BoxLayout.encloseX(addresseTxt),
                BoxLayout.encloseX(DateTxt),
                BoxLayout.encloseX(MpaiementTxt),
                BoxLayout.encloseX(prixTxt,lModifier,lSupprimer,lDetais)));
        
        
        
        add(cnt);
    }
    
   
   
}
