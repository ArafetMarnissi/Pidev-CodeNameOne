/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
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
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Produit;
import com.mycompany.services.ProduitService;
import com.mycompany.utils.Statics;
import java.io.IOException;

/**
 *
 * @author damma
 */
public class Detailprod extends BaseForm  {
    Form current;

   public Detailprod(Resources res,int id1){
        
        super("Newsfeed",BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("NewsTopLine2");
        setTitle("Detail produit");
//        current.getTitleComponent().getStyle().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_LARGE));
//        current.getTitleComponent().getStyle().setFgColor(0x00000);
        getContentPane().setScrollVisible(false);
        
        Tabs swipe = new Tabs();
        
        Label s1= new Label();
        Label s2= new Label();
        
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
        
        String urlImage="/logo1.png";
            Image placeHolder=Image.createImage(120, 90);
            EncodedImage enc=EncodedImage.createFromImage(placeHolder, false);
            URLImage urlim=URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);
//            Produit a=ProduitService.getInstance();
//            addButton(a, res);
   
   }
    
    private void addTab(Tabs swipe, Label spacer , Image image, String string, String text, Resources res) {
        int size= Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        
        if(image.getHeight() < size){
            image = image.scaledHeight(size);
        }
        
        if(image.getHeight() > Display.getInstance().getDisplayHeight()/2){
            image = image.scaledHeight(Display.getInstance().getDisplayHeight()/2);
        }
        
        ScaleImageLabel imageScale =new ScaleImageLabel(image);
        imageScale.setUIID("container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
        Label overLay =new Label("","ImageOverlay");
        
        Container page1=
                LayeredLayout.encloseIn(
                imageScale,
                        overLay,
                        BorderLayout.south(
                        BoxLayout.encloseY(
                        new SpanLabel(text,"LargeWhiteText"),
                                FlowLayout.encloseIn(),
                                spacer
                        )
                        )
                );
        
        swipe.addTab("",res.getImage("logo1.png"),page1);
    }
   private void addButton(Produit a,Resources res) throws IOException{
       
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
        imgv.setImage(imgv.getImage().scaled(500, 500));
        Container cnt=BorderLayout.west(imgv);
        
        Label nomtxt= new Label(""+a.getNom(),"NewsTopLine2");
          
       
       Label desclab= new Label(""+a.getDescription());
       desclab.getAllStyles().setFgColor(0x000000);
       
       Label Prixlab= new Label(""+a.getPrix_produit()+"DT","NewsTopLine");
       
       Image myImage = Image.createImage("/full_star.png");
       Image scaledImage = myImage.scaled(50, 50);
       Image myImage1 = Image.createImage("/empty_star.png");
       Image scaledImage1 = myImage1.scaled(50, 50);
       
       Label[] stars = new Label[5];
        for(int i=0;i<5;i++){
            if(i<Math.round(a.getNote())){
                stars[i] = new Label(scaledImage);
            }else{
                stars[i] = new Label(scaledImage1);
            }
        }
        
        cnt.add(BorderLayout.CENTER,BoxLayout.encloseY(
                /*BoxLayout.encloseX(idtxt),*/
                BoxLayout.encloseX(nomtxt,Prixlab),
                BoxLayout.encloseX(desclab),
                BoxLayout.encloseX(stars)
        ));
        
       add(cnt);
       
   }
}
