/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upf.taln.onto.mode_selection;

/**
 *
 * @author carlatv
 */
public class EmotionSetting {
    
    enum Proximity {
        close, distant, medium_close
    }
    
    enum Style {
        formal, informal
    }
    
    enum Personality{
        extroverted, introverted
    }
    
    enum Expressivity{
        high, medium, low
    }
    
    enum Social {
        reserved, colloquial, heartful
    }
    
   Proximity proximity; 
   Style style;
   Personality personality;
   Expressivity expressivity;
   Social social;

    public EmotionSetting(Proximity proximity, Style style, Personality personality, Expressivity expressivity, Social social) {
        this.proximity = proximity;
        this.style = style;
        this.personality = personality;
        this.expressivity = expressivity;
        this.social = social;
    }

    public Proximity getProximity() {
        return proximity;
    }

    public Style getStyle() {
        return style;
    }

    public Personality getPersonality() {
        return personality;
    }

    public Expressivity getExpressivity() {
        return expressivity;
    }

    public Social getSocial() {
        return social;
    }

   
}
