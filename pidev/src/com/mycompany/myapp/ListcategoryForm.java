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
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
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
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Category;
import com.mycompany.entities.Produit;
import com.mycompany.services.ProduitService;
import com.mycompany.services.servicecategory;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;


/**
 *
 * @author damma
 */
public class ListcategoryForm extends BaseForm{
    
    Form current;
    public ListcategoryForm(Resources res){
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Liste des Catégories");
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
        //////////////
        
        Tabs swipe = new Tabs();
        
        Label s1= new Label();
        Label s2= new Label();
        addTab(swipe,s1, res.getImage("salle-back.jpg"),"","",res);
//        addTab(swipe,s1, res.getImage("logo1.png"),"","",res); 
        
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
        RadioButton mesListes = RadioButton.createToggle("Mes categories", barGroup);
        mesListes.setUIID("SelectBar");
        //RadioButton liste = RadioButton.createToggle("Autres", barGroup);
        //liste.setUIID("SelectBar");
//        RadioButton partage = RadioButton.createToggle("Mes produits", barGroup);
//        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("bleufleche-ConvertImage.png"), "Container");


        /*mesListes.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
        
         ListcategoryForm a = new ListcategoryForm(res);
            a.show();
            refreshTheme();
        });*/
//        partage.addActionListener((e) -> {
//               InfiniteProgress ip = new InfiniteProgress();
//        final Dialog ipDlg = ip.showInifiniteBlocking();
//        
//         ajoutercategorieForm a = new ajoutercategorieForm(res);
//            a.show();
//            refreshTheme();
//        });
//partage.addActionListener((e)->{
//     InfiniteProgress ip = new InfiniteProgress();
//        final Dialog ipDlg = ip.showInifiniteBlocking();
//        
//         ListProduit a = new ListProduit(res);
//            a.show();
//            refreshTheme();
//});

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(1, mesListes/*, liste, partage*/),
                FlowLayout.encloseBottom(arrow)
        ));

        //partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(false);
            updateArrowPosition(mesListes, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        //bindButtonSelection(liste, arrow);
        //bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        
        ArrayList<Category>list=servicecategory.getInstance().affichagecategory();
        
        for(Category rec : list){
            
            String urlImage="logo1.png";
            Image placeHolder=Image.createImage(120, 90);
            EncodedImage enc=EncodedImage.createFromImage(placeHolder, false);
            URLImage urlim=URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);
            try {
                addButton(urlim,rec,res);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            ScaleImageLabel image=new ScaleImageLabel(urlim);
            Container containerImage=new Container();
            image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        }
    }
    
    public void showMessage(String message) {
        Dialog.show("Message", message, "OK", null);
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

    private void addButton(Image img, Category a,Resources res) throws IOException{
        
        
        //System.out.println(a.getImageCategorie());
        
//        try{
//        img=Image.createImage("/"+a.getImageCategorie());
//        }catch(IOException ex){
//             ex.printStackTrace();
//        }

EncodedImage enc = null;
        try{
        //img=Image.createImage("/C:/Users/damma/OneDrive/Bureau/testpidev/PIDEV-Golden-Gym/Pidev/public/uploads/produits/"+a.getImage_produit());
           enc=EncodedImage.create("/bleufleche-640a0a683eece.png");
        }catch(IOException ex){
             ex.printStackTrace();
        }
        String url=Statics.URL_REP_IMAGES_CAT+"/"+a.getImageCategorie();
         System.out.println(url);
        Image immg=URLImage.createToStorage(enc, url, url,URLImage.RESIZE_SCALE);
        // System.out.println(immg.toString());
        ImageViewer imgv = new ImageViewer(Image.createImage("/bleufleche-640a0a683eece.png"));
        imgv.setImage(immg);
        imgv.setImage(imgv.getImage().scaled(300, 300));
        
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Container cnt=BorderLayout.west(imgv);
        ArrayList<Produit>list=ProduitService.getInstance().affichageproduit(a.getId());
        
        imgv.addPointerReleasedListener(e->{
            if(list.isEmpty()){
            showMessage("Aucun produit sous cette categorie!");
        }else{
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
        
         ListProduit az = new ListProduit(res,a.getId());
         az.recupdata(a.getId());
            az.show();
            refreshTheme(); 
            }
        });
        
        
        
       Label nomtxt= new Label(""+a.getNomCategory(),"NewsTopLine2");
       
       Label sss=new Label(" ");
       
       //Label idtxt= new Label("-id:"+a.getId(),"NewsTopLine");
       
       
       
       createLineSeparator();
       
      // cnt.add(BorderLayout.CENTER,BoxLayout.encloseY(BoxLayout.encloseX(idtxt),BoxLayout.encloseX(nomtxt)));
       
       //suppression 
//       Label supp=new Label(" ");
//       supp.setUIID("NewsTopLine");
//       Style suppstyle=new Style(supp.getUnselectedStyle());
//       suppstyle.setFgColor(0xf21f1f);
//       
//        FontImage suppimg = FontImage.createMaterial(FontImage.MATERIAL_DELETE, suppstyle);
//        supp.setIcon(suppimg);
//        supp.setTextPosition(RIGHT);
//        
//        supp.addPointerPressedListener(l -> {
//            
//            Dialog dig =new Dialog("suppression");
//            
//            if(dig.show("suppression","Vous voulez supprimer cette categorie?","Annuler","Oui")){
//                dig.dispose();
//            }
//            else{
//                dig.dispose();
//                if(servicecategory.getInstance().deletecategorie(a.getId())){
//                    new ListcategoryForm(res).show();
//                }
//            }
//        });
        
        //update
//        Label update=new Label(" ");
//       update.setUIID("NewsTopLine");
//       Style updatestyle=new Style(update.getUnselectedStyle());
//       updatestyle.setFgColor(0xf7ad02);
//       
//       FontImage mFontImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, updatestyle);
//       update.setIcon(mFontImage);
//        update.setTextPosition(LEFT);
//        
//        update.addPointerPressedListener(l -> {
//            //System.out.println("hello update");
//            new modifercategorieForm(res,a).show();
//        });
       
        
        cnt.add(BorderLayout.CENTER,BoxLayout.encloseY(
                BoxLayout.encloseX(sss),
                BoxLayout.encloseX(nomtxt)));
        
       add(cnt);
    }
    
    
}
