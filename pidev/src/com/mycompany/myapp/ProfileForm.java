/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package com.mycompany.myapp;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
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
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.services.ServiceUser;
import java.io.IOException;


/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class ProfileForm extends BaseForm {

    public ProfileForm(Resources res) {
          super("Newsfeed", BoxLayout.y());
//        Toolbar tb = new Toolbar(true);
//        setToolbar(tb);
//        getTitleArea().setUIID("Container");
//        setTitle("Mon Profil");
//        getContentPane().setScrollVisible(false);
//        
//        super.addSideMenu(res);
//        
//        
//        Button cartButton = new Button("");
//        cartButton.setUIID("NewsTopLine");
//        Style cartStyle = new Style(cartButton.getUnselectedStyle());
//        cartStyle.setFgColor(0xf21f1f);
//        FontImage cartIcon = (FontImage) FontImage.createMaterial(FontImage.MATERIAL_SHOPPING_CART, cartStyle).scaled(100, 100);
//        tb.addCommandToRightBar("", cartIcon, e -> {
//            InfiniteProgress ip = new InfiniteProgress();
//            final Dialog ipDlg = ip.showInifiniteBlocking();
//        
//             PanierForm a = new PanierForm(res);
//             a.show();
//             refreshTheme();
//        });


        Image img = res.getImage("salle-back.jpg");
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label facebook = new Label("786 followers", res.getImage("facebook-logo.png"), "BottomPad");
        Label twitter = new Label("486 followers", res.getImage("twitter-logo.png"), "BottomPad");
        facebook.setTextPosition(BOTTOM);
        twitter.setTextPosition(BOTTOM);
        
      

       
        

        TextField email = new TextField(SessionManager.getEmail(), "E-Mail", 20, TextField.EMAILADDR);
        email.setUIID("TextFieldBlack");
        addStringValue("E-Mail", email);
        email.setEditable(false);
        
        Label l1=new Label(" ");
        Label l3=new Label(" ");
        Label l2=new Label(" ");
        
        
        TextField nom = new TextField(SessionManager.getNom(), "nom");
        nom.setUIID("TextFieldBlack");
        addStringValue("nom", nom);
        nom.setEditable(false);
        
        TextField prenom = new TextField(SessionManager.getPrenom(), "prenom");
        prenom.setUIID("TextFieldBlack");
        addStringValue("prenom", prenom);
        prenom.setEditable(false);
        
       String status =  SessionManager.isStatus().toString();
       String type;
       if (status=="false")
       {
                      type="non vérifié";
       }
       else
       {
           type="vérifié";
       }
           
        TextField hello = new TextField(type, "Etat");
        hello.setUIID("TextFieldBlack");
        addStringValue("Etat ", hello);
        hello.setEditable(false);
        
        
     Button boutonModifier = new Button("Modifier");
        addStringValue("", boutonModifier);
        boutonModifier.addPointerPressedListener(l ->   { 

           if(ServiceUser.getInstance().modifierUser(SessionManager.getInstance())) { // if true
           new ModifierUser(res,SessionManager.getInstance()).show();
                }
           });
        Button Retour = new Button("Retour");
        addStringValue("", Retour);
        Retour.addPointerPressedListener(l ->   { 

           
              try {
                  new AffichageActivite(res).show();
              } catch (IOException ex) {
                  System.out.println(ex.getMessage());
              }
                
           });
        
        
        

       
    }
    
    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
    
    
   
}


