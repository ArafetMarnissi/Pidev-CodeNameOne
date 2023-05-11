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
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
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
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.Validator;
import com.mycompany.entities.Activite;
import com.mycompany.services.ServiceActivite;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;






/**
 *
 * @author mmarr
 */
public class AjoutActivite extends BaseForm {
    
    Form current;
    public AjoutActivite(Resources res){
        super("Newsfeed",BoxLayout.y());
        
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("container");
        //setTitle("Ajout Activite");
        getContentPane().setScrollVisible(false);
        
        tb.addSearchCommand(e -> {
            
        });
        Tabs swipe = new Tabs();
        Label s1 = new Label();
        Label s2 = new Label();
        
        addTab(swipe,s1, res.getImage("logo1.png"),"","",res);
        
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
        RadioButton mesListes = RadioButton.createToggle("Les Activités", barGroup);
        mesListes.setUIID("SelectBar");
        /*RadioButton liste = RadioButton.createToggle("Autres", barGroup);
        liste.setUIID("SelectBar");*/
        RadioButton partage = RadioButton.createToggle("Les coachs", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("arrow.png"), "Container");


        mesListes.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
        
        //  ListReclamationForm a = new ListReclamationForm(res);
          //  a.show();
             try {
                      new AffichageActivite(res).show();
                  } catch (IOException ex) {
                      System.out.println(ex.getMessage());
                  }
                    refreshTheme();
           
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(2, mesListes, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(partage, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        //bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        
        partage.addActionListener((e) -> {
         InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
                    try {
                      new AffichageActivite(res).show();
                  } catch (IOException ex) {
                      System.out.println(ex.getMessage());
                  }
                    refreshTheme();
                    
        });
                 /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                  SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
        
        TextField obj = new TextField("","entrer");
        obj.setInputMode("text");
        obj.setConstraint(TextField.INITIAL_CAPS_SENTENCE);
        obj.setMaxSize(15);
        obj.setUIID("TextFieldBlack");
        addStringValue("Nom de l'activite",obj);

        
        TextField desc = new TextField("","entrer");
        desc.setUIID("TextFieldBlack");
        addStringValue("Description",desc);
        
        
        TextField nbre = new TextField("","entrer");
        nbre.setUIID("TextFieldBlack");
        addStringValue("Nombre de places",nbre);
      
        
        Picker dateP=new Picker();
        dateP.setType(Display.PICKER_TYPE_DATE);
         dateP.setUIID("TextFieldBlack");
        addStringValue("Date de l'activite",dateP);
        
        /*Picker timeP=new Picker();
        timeP.setType(Display.PICKER_TYPE_TIME);
         timeP.setUIID("TextFieldBlack");
        addStringValue("Debut",timeP);
        long timeInMillis = timeP.getTime();

        // Convertir le temps en heures, minutes et secondes
        Date date = new Date(timeInMillis);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);*/
      /* Picker timeP=new Picker();
        timeP.setType(Display.PICKER_TYPE_TIME);
         timeP.setUIID("TextFieldBlack");
        addStringValue("Debut",timeP);
        long timeInMillis = timeP.getTime()*60;
        Date now = new Date(timeInMillis);*/
       
        
      
        
       /* Button btnAjout = new Button("Ajouter");
        addStringValue("", btnAjout);
        btnAjout.addActionListener((e)->{
            
            
            try {
                if(obj.getText() =="" || desc.getText() =="" || nbre.getText() ==""){
                    Dialog.show("Données invalides","","Annuler","OK");
                } 
                Dialog dig = new Dialog("Date");
            
            if(dig.show("Date","La date n'est pas modifiable. êtes vous sur de l'ajouter ?","Annuler","Oui")) {
                dig.dispose();
            }
                else{
                  InfiniteProgress ip = new InfiniteProgress();
                  final Dialog iDialog = ip.showInfiniteBlocking();
            
               //System.out.println(sdf1.format(now));
                  Activite a = new Activite(String.valueOf(obj.getText()),String.valueOf(desc.getText().toString()),Integer.valueOf(nbre.getText()),sdf.format(dateP.getDate()));
                    System.out.println("data Activite == "+a);
                    
                    ServiceActivite.getInstance().ajoutActivite(a);
                    iDialog.dispose();
                     new AffichageActivite(res).show();
                    refreshTheme();
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        });*/
    }

    private void addStringValue(String obj, Component c) {
        
        add(BorderLayout.west(new Label(obj,"PaddedLabel"))
        .add(BorderLayout.CENTER,c)
        );
        add(createLineSeparator(0xeeeeee));
    }

    private void addTab(Tabs swipe, Label spacer ,Image image, String string, String text, Resources res) {
       int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
       
       if(image.getHeight() < size){
           image=image.scaledHeight(size);
       }
       if(image.getHeight() > Display.getInstance().getDisplayHeight() / 2){
           image=image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
       }
        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
        Label overLay=new Label("","ImageOverlay");
        
        Container page1=LayeredLayout.encloseIn(imageScale,overLay,BorderLayout.south(BoxLayout.encloseY(new SpanLabel(text, "LargewhiteText"),FlowLayout.encloseIn(),spacer)));
      swipe.addTab("", res.getImage("logo1.png"), page1);
    }
    
    public void bindButtonSelection(Button btn, Label l ){
        btn.addActionListener(e -> {
        if(btn.isSelected()){
            updateArrowPosition(btn,l);
        }
    });
    }

    private void updateArrowPosition(Button btn, Label l) {
        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth()/2 - l.getWidth()/2 );
        l.getParent().repaint();
    }
}
