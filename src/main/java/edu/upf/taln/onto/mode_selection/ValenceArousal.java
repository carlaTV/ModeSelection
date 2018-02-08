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
public class ValenceArousal {
    
    float valence;
    private float arousal;

    public ValenceArousal(float valence, float arousal) {
        this.valence = valence;
        this.arousal = arousal;
    }

    public float getValence() {
        return valence;
    }

    public void setValence(float valence) {
        this.valence = valence;
    }

    public float getArousal() {
        return arousal;
    }

    public void setArousal(float arousal) {
        this.arousal = arousal;
    }

    
}
