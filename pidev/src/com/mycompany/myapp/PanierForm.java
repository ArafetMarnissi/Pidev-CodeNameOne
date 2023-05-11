/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
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
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Commande;
import com.mycompany.entities.Produit;
import com.mycompany.entities.SessionPanier;
import com.mycompany.services.ProduitService;
import com.mycompany.services.ServiceCommande;
import com.mycompany.utils.Statics;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author marni
 */
public class PanierForm extends BaseForm{
        Form current;
    public PanierForm(Resources res ) {
       super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Panier");
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
        ////test de creation des produit et les ajouter dans le panier
//        SessionPanier.getInstance();
//        Produit p = new Produit(1, "vitam c", "description", 120, 2, "whey-63ed4d7c9c9a5.jpg");
//        SessionPanier.getInstance().addProduct(p);
//        System.out.println(SessionPanier.getPanier().toString());



        ///////////
        
        
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
                GridLayout.encloseIn(2, mesCommandes, Panier),
                FlowLayout.encloseBottom(arrow)
        ));

        Panier.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(Panier, arrow);
        });
        bindButtonSelection(mesCommandes, arrow);
        
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        
      
        //Appel affichage methode
            HashMap<Produit,Integer>list=SessionPanier.getPanier();
        
       
            for(Produit prod :list.keySet()){
            
            String urlImage="/logo1.png";
            Image placeHolder=Image.createImage(120, 90);
            EncodedImage enc=EncodedImage.createFromImage(placeHolder, false);
            URLImage urlim=URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);
            int quant = list.get(prod);
            try {
                addButton(urlim,prod,quant,res);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            ScaleImageLabel image=new ScaleImageLabel(urlim);
            Container containerImage=new Container();
            image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        }
            /////////button passer commande
        Button btnAjouter = new Button("Passer Commande");
        addStringValue("", btnAjouter);
        btnAjouter.addActionListener((e) -> {
            AjoutCommandeForm a = new AjoutCommandeForm(res);
            a.show();
            refreshTheme();
               });
        
        
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

  private void addButton(Image img, Produit a ,int quant,Resources res) throws IOException{
        EncodedImage enc = null;
        try{
       
           enc=EncodedImage.create("/bleufleche-640a0a683eece.png");
        }catch(IOException ex){
             ex.printStackTrace();
        }
        String url=Statics.URL_REP_IMAGES+"/"+a.getImage_produit();
         //System.out.println(url);
        Image immg=URLImage.createToStorage(enc, url, url,URLImage.RESIZE_SCALE);
        // System.out.println(immg.toString());
        ImageViewer imgv = new ImageViewer(Image.createImage("/bleufleche-640a0a683eece.png"));
        imgv.setImage(immg);
        imgv.setImage(imgv.getImage().scaled(300, 300));
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image=new Button(img.fill(width, height));
        image.setUIID("label");
        Container cnt=BorderLayout.west(imgv);
       imgv.addPointerReleasedListener(e->{
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();

        });
        
        
        
       Label nomtxt= new Label(""+a.getNom(),"NewsTopLine2");

       Label desclab= new Label(""+a.getDescription());
       desclab.getAllStyles().setFgColor(0x000000);
       
       Label Prixlab= new Label(""+a.getPrix_produit()+"DT","NewsTopLine");
       
       Label qant = new Label(""+quant);

       Label ss=new Label("   ");
       Label quantitie=new Label("quantite : ");

       Image myImage = Image.createImage("/full_star.png");
       Image scaledImage = myImage.scaled(50, 50);
       Image myImage1 = Image.createImage("/empty_star.png");
       Image scaledImage1 = myImage1.scaled(50, 50);
         createLineSeparator();
         
       //////////les boutons/////////////
       ///btn Plus
        Label BtnPlus = new Label(" ");
        BtnPlus.setUIID("NewsTopLine");
        Style modifierStyle = new Style(BtnPlus.getUnselectedStyle());
        modifierStyle.setFgColor(0xf7ad02);
        
        FontImage mFontImage = FontImage.createMaterial(FontImage.MATERIAL_ADD, modifierStyle);
        BtnPlus.setIcon(mFontImage);
        BtnPlus.setTextPosition(RIGHT);
        
        
        BtnPlus.addPointerPressedListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent l) {
                SessionPanier.getInstance().addProduct(a);
                PanierForm a = new PanierForm(res);
                a.show();
                refreshTheme();
            }
        });
        ///Btn moins
         Label BtnMoins = new Label(" ");
        BtnPlus.setUIID("NewsTopLine");
        Style modifierStyle2 = new Style(BtnMoins.getUnselectedStyle());
        modifierStyle.setFgColor(0xf7ad02);
        
        FontImage MoinsFontImage = FontImage.createMaterial(FontImage.MATERIAL_REMOVE, modifierStyle);
        BtnMoins.setIcon(MoinsFontImage);
        BtnMoins.setTextPosition(RIGHT);
        
        
        BtnMoins.addPointerPressedListener(l -> {
            if(SessionPanier.getPanier().get(a)>1){
                SessionPanier.getInstance().decreaseProduct(a);
                PanierForm b = new PanierForm(res);
                b.show();
                refreshTheme();}
        });

       //////////////////////////////////
        
        cnt.add(BorderLayout.CENTER,BoxLayout.encloseY(
                /*BoxLayout.encloseX(idtxt),*/
                BoxLayout.encloseX(nomtxt,Prixlab),
                BoxLayout.encloseX(desclab,ss),
                BoxLayout.encloseX(quantitie,qant,BtnPlus,BtnMoins)
               // BoxLayout.encloseX(BoxLayout.encloseX(stars),cart)
                
        ));
        
       add(cnt);
    }
      private void addStringValue(String s, Component v) {
        
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
        .add(BorderLayout.CENTER,v));
        add(createLineSeparator(0xeeeeee));
    }
}
