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
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
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
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

import com.mycompany.entities.Commande;
import com.mycompany.services.ServiceCommande;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Lenovo
 */
public class AjoutCommandeForm extends BaseForm {
    
    
    Form current;
    public AjoutCommandeForm(Resources res ) {
          super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Passer Commande");
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
        
        //
        
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
        RadioButton AjoutCommande = RadioButton.createToggle("Ajouter", barGroup);
        AjoutCommande.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

///////////radio buuton pour afficher la liste des commandes
        mesCommandes.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
        
          ListCommandeForm a = new ListCommandeForm(res);
            a.show();
            refreshTheme();
        });
/////////radio buuton pour ajouter une commande
        AjoutCommande.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
        
          AjoutCommandeForm a = new AjoutCommandeForm(res);
            a.show();
            refreshTheme();
        });



        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(2, mesCommandes, AjoutCommande),
                FlowLayout.encloseBottom(arrow)
        ));

        AjoutCommande.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(AjoutCommande, arrow);
        });
        bindButtonSelection(mesCommandes, arrow);
        bindButtonSelection(AjoutCommande, arrow);
        
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        
        //
        
      
        TextField adresse_livraison = new TextField("", "entrer l adreese de livraison!!");
        adresse_livraison.setUIID("TextFieldBlack");
        addStringValue("adresse_livraison",adresse_livraison);
        
        
        Vector<String> vectorPaiement;
        vectorPaiement = new Vector();
        
        vectorPaiement.add("à la livraison");
        vectorPaiement.add("chèque");
        vectorPaiement.add("carte bancaire");
        
        ComboBox<String>methode_paiement = new ComboBox<>(vectorPaiement);
        methode_paiement.setUIID("TextFieldBlack");
       addStringValue("methode_paiement",methode_paiement);
    /*     TextField methode_paiement = new TextField("", "entrer la methode de paiement!!");
        methode_paiement.setUIID("TextFieldBlack");
        addStringValue("methode_paiement",methode_paiement);
    */    
        TextField telephone = new TextField("", "entrer votre numero de telephone",8,TextField.NUMERIC);
        telephone.setUIID("TextFieldBlack");
        addStringValue("telephone",telephone);
        
        //////////
        Button btnAjouter = new Button("Ajouter");
        addStringValue("", btnAjouter);
        
        
        //onclick button event 

        btnAjouter.addActionListener((e) -> {
            
            
            try {
                
                //int tel = Integer.parseInt(telephone.getText());
                if(adresse_livraison.getText().equals("") || telephone.getText().equals("")) {
                    Dialog.show("Veuillez vérifier les données","","Annuler", "OK");
                 
                }else{
                    if (telephone.getText().length() != 8) {
                    Dialog.show("Numéro de téléphone invalide", "", "Annuler", "OK");
                    }
                
                
                
                else {
                    InfiniteProgress ip = new InfiniteProgress();; 
                
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    
                    
                    Commande c = new Commande(
                            format.format(new Date()),
                            String.valueOf(adresse_livraison.getText()).toString(),
                            100,
                            String.valueOf(methode_paiement.getSelectedItem()),
                           Integer.parseInt( telephone.getText())
                    );
                    
                    System.out.println("data  commande == "+c.toString());
                    
                    
                     
                    ServiceCommande.getInstance().ajoutCommande(c);
                    
                    
                    iDialog.dispose(); 
                    
                    
                    new ListCommandeForm(res).show();
                    
                    
                    refreshTheme();//Actualisation
                            
                }}
                
            }catch(Exception ex ) {
                ex.printStackTrace();
            }
            
            
            
            
            
        });
        
        
    }

    private void addStringValue(String s, Component v) {
        
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
        .add(BorderLayout.CENTER,v));
        add(createLineSeparator(0xeeeeee));
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
        
        swipe.addTab("",res.getImage("salle-back.jpg"), page1);
        
        
        
        
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
    
   
   
    
}
