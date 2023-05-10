/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Container;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author damma
 */
public class Star extends Container  {
    private Image fullStar;
    private Image emptyStar;
    
    public Star(int currentRating, int maxRating) {
        fullStar = Resources.getGlobalResources().getImage("/full_star.png");
        emptyStar = Resources.getGlobalResources().getImage("/empty_star.png");
        
        setLayout(new FlowLayout(CENTER));
        setUIID("Star");
        
        ScaleImageLabel starLabel=null;
        for (int i = 0; i < maxRating; i++) {
            Image starImage = i < currentRating ? fullStar : emptyStar;
            starLabel = new ScaleImageLabel(starImage);
            starLabel.setPreferredSize(new Dimension(30, 30));
            add(starLabel);
        }
    }
    
}
