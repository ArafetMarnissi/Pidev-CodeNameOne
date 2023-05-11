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
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Activite;
import com.mycompany.services.ServiceActivite;
import com.mycompany.utils.fich;
import java.io.IOException;
import java.util.ArrayList;


/**
 *
 * @author mmarr
 */
public class AffichageActiviteCoach extends BaseForm{
    
      Form current;
    public AffichageActiviteCoach(Resources res,int id) throws IOException{
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
        
        addTab(swipe,s1, res.getImage("salle-back.jpg"),"","",res);
        
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


        partage.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
        
        try {
            //  ListReclamationForm a = new ListReclamationForm(res);
            //  a.show();
            new AffichageCoach(res).show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
                    refreshTheme();
        });
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
            updateArrowPosition(mesListes, arrow);
        });
        bindButtonSelection(mesListes, arrow);
       // bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        
        ArrayList<Activite> list= ServiceActivite.getInstance().affichageactsbycoach(id);
       
        for(Activite a:list){
            String urlImage="logo1.png";
            Image placeHolder=Image.createImage(120, 90);
            EncodedImage enc=EncodedImage.createFromImage(placeHolder, false);
            URLImage urlim=URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);
            addButton(urlim, a, res,urlim);
            ScaleImageLabel image=new ScaleImageLabel(urlim);
            Container containerImage=new Container();
            image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        }
        
            /*Label ta22 = new Label("ce coach ne possede pas d'activité !","NewsTopLine2");
            Container cnt=new Container(new BoxLayout(BoxLayout.Y_AXIS));
            ta22.setAlignment(Component.CENTER);
            cnt.add(ta22);*/
        
        
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

    private void updateArrowPosition(Button btn, Label l)
            {
        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth()/2 - l.getWidth()/2 );
        l.getParent().repaint();
    } 
    
    private void addButton(Image img, Activite a, Resources res,Image img1) throws IOException{
         EncodedImage enc = null;
        try{
        //img=Image.createImage("/C:/Users/damma/OneDrive/Bureau/testpidev/PIDEV-Golden-Gym/Pidev/public/uploads/produits/"+a.getImage_produit());
           enc=EncodedImage.create("/rpm-mills-800x534-1-64032742659b2.jpg");
        }catch(IOException ex){
             ex.printStackTrace();
        }
        String url=fich.URL_REP_IMAGES_ACT+"/"+a.getImage();
        // System.out.println(url);
        Image immg=URLImage.createToStorage(enc, url, url,URLImage.RESIZE_SCALE);
        // System.out.println(immg.toString());
        ImageViewer imgv = new ImageViewer(Image.createImage("/rpm-mills-800x534-1-64032742659b2.jpg"));
        imgv.setImage(immg);
        imgv.setImage(imgv.getImage().scaled(300, 300));
        int height = Display.getInstance().convertToPixels(13.5f);
        int width = Display.getInstance().convertToPixels(14f);
        /*Button image=new Button(img.fill(width, height));
        image.setUIID("label");*/
        Container cnt=BorderLayout.west(imgv);
        
        
        
       /* try{
        img1=Image.createImage("/640a0d8ad2310.png");
        }catch(IOException ex){
            ex.printStackTrace();
        }*/

        Button image1=new Button(img1.fill(width, height));
        image1.setUIID("label");
        //Container cnt=BorderLayout.west(image);
        Container cnt1=BorderLayout.east(image1);
        
        String nb = String.valueOf(a.getNbrePlace());
        Label ta = new Label(a.getNomActivite(),"NewsTopLine2");
        Label ta1 = new Label(a.getDescriptionActivite(),"CenterLabel");
        Label ta2 = new Label(nb,"CenterLabel");
        //System.out.println("Activite == "+nb);
         createLineSeparator();
                  Button participer=new Button();
         ArrayList<Activite> listEn= ServiceActivite.getInstance().findpartbyid(a.getId());
         System.out.println("--------------------------------------------------");
         System.out.println(listEn);
         System.out.println("--------------------------------------------------");
         
          for(Activite p:listEn){
         System.out.println("--------------------------------------------------");
         System.out.println(p.isEn());
         System.out.println("--------------------------------------------------");
              if(p.isEn()== false){
              participer=new Button("Participer");
              //cnt.add(participer);
         participer.addActionListener(e-> {
             /*boolean result= ServiceActivite.getInstance().findpartbyid(a.getId());
             System.out.println(result);*/
             ServiceActivite.getInstance().ajoutPart(a.getId());
             try {
                      new AffichageActivite(res).show();
                  } catch (IOException ex) {
                      System.out.println(ex.getMessage());
                  }
                    refreshTheme();
         });
              }else{
            participer=new Button("Annuler");
            //cnt.add(participer1);
         participer.addActionListener(e-> {
             /*boolean result= ServiceActivite.getInstance().findpartbyid(a.getId());
             System.out.println(result);*/
            ServiceActivite.getInstance().deletePart(p.getId_p());
             try {
                      new AffichageActivite(res).show();
                  } catch (IOException ex) {
                      System.out.println(ex.getMessage());
                  }
                    refreshTheme();
         });
              }
          }
         
      
        
                cnt.add(BorderLayout.CENTER, BoxLayout.encloseY(
                BoxLayout.encloseX(ta),
                BoxLayout.encloseX(ta1),
                BoxLayout.encloseX(ta2,participer)));
                //BoxLayout.encloseX(lModifier,lSupprimer)));
        

        
        add(cnt);
        add(cnt1);
    }
    
}
