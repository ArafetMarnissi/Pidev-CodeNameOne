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
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author mmarr
 */
public class AffichageQR extends BaseForm{
    
        Form current;
    public AffichageQR(Resources res,int id){
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


        partage.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
        
        //  ListReclamationForm a = new ListReclamationForm(res);
          //  a.show();
             new AffichageCoach(res).show();
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
         ArrayList<Activite> list= ServiceActivite.getInstance().getQrCode(id);
         
       // ArrayList<Activite> list= ServiceActivite.getInstance().affichageActivite();
        for(Activite a:list){
            //System.out.println("ID QRCODE"+a.getQrcode());
            String urlImage="logo1.png";
            Image placeHolder=Image.createImage(120, 90);
            EncodedImage enc=EncodedImage.createFromImage(placeHolder, false);
            URLImage urlim=URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);
            addButton(urlim, a, res,urlim);
            ScaleImageLabel image=new ScaleImageLabel(urlim);
            Container containerImage=new Container();
            image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        }
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
    
    private void addButton(Image img, Activite a, Resources res,Image img1){
         //ArrayList<Activite> list= ServiceActivite.getInstance().getQrCode(a.getId());
         //System.out.println(a.getId());
         a.setQrcode(a.getQrcode());
        
             System.out.println(a.getQrcode());
             try {
    Thread.sleep(2000); // Pause de 3 secondes
} catch (InterruptedException e) {
    // Gérer l'exception
}
             try{
        img=Image.createImage("/640a0d8ad2310.png");
            System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
            //System.out.println(a.getQrcode());
            System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
        }catch(IOException ex){
            ex.printStackTrace();
        }
        int height = Display.getInstance().convertToPixels(13.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image=new Button(img.fill(width, height));
        image.setUIID("label");
        Container cnt=BorderLayout.west(image);
        
       /* try{
        img1=Image.createImage("/640a0d8ad2310.png");
        }catch(IOException ex){
            ex.printStackTrace();
        }*/

       /* Button image1=new Button(img1.fill(width, height));
        image1.setUIID("label");
        //Container cnt=BorderLayout.west(image);
        Container cnt1=BorderLayout.east(image1);*/
        
       /* String nb = String.valueOf(a.getNbrePlace());
        Label ta = new Label(a.getNomActivite(),"NewsTopLine2");
        Label ta1 = new Label(a.getDescriptionActivite(),"CenterLabel");
        Label ta2=new Label();
        if(a.getNbrePlace()!=0){
        ta2 = new Label(nb+"  Places disponibles","CenterLabel");
        }else{
         ta2 = new Label(nb+"  Place disponible","CenterLabel"); 
        }*/
        
        //System.out.println("Activite == "+nb);
        Label ta = new Label("QR Code","NewsTopLine2");
         createLineSeparator();

          
         /*Button participer=new Button("Participer");
         participer.addActionListener(e-> {
             /*boolean result= ServiceActivite.getInstance().findpartbyid(a.getId());
             System.out.println(result);*/
         //});
         
       /* Label lSupprimer = new Label(" ");
        lSupprimer.setUIID("NewsTopLine");
        Style supprmierStyle = new Style(lSupprimer.getUnselectedStyle());
        supprmierStyle.setFgColor(0xf21f1f);
        
        FontImage suprrimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprmierStyle);
        lSupprimer.setIcon(suprrimerImage);
        lSupprimer.setTextPosition(RIGHT);
        
        
        
        lSupprimer.addPointerPressedListener(l -> {
            
            Dialog dig = new Dialog("Suppression");
            
            if(dig.show("Suppression","Voulez vous vraiment supprimer cette activité ?","Annuler","Oui")) {
                dig.dispose();
            }
            else {
                dig.dispose();
                 }
                //n3ayto l suuprimer men service Reclamation
                if(ServiceActivite.getInstance().deleteActivite(a.getId())) {
                    new AffichageActivite(res).show();
                }
           
        });
        
        Label lModifier = new Label(" ");
        lModifier.setUIID("NewsTopLine");
        Style modifierStyle = new Style(lModifier.getUnselectedStyle());
        modifierStyle.setFgColor(0xf7ad02);
        
        FontImage mFontImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyle);
        lModifier.setIcon(mFontImage);
        lModifier.setTextPosition(LEFT);
        
        
        lModifier.addPointerPressedListener(l -> {
            //System.out.println("hello update");
            new ModifierActivite(res,a).show();
        });*/
        
                cnt.add(BorderLayout.CENTER, BoxLayout.encloseY(
                BoxLayout.encloseX(ta)
               ));
                //BoxLayout.encloseX(lModifier,lSupprimer)));
        

        
        add(cnt);
        //add(cnt1);
    }
    
}
