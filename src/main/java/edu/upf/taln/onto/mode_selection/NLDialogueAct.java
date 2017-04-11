package edu.upf.taln.onto.mode_selection;

public abstract class NLDialogueAct {

    public enum OutputFormat {CONLL, RAW}

    String da; 	
    float arousal; 
    float valence;
    String url;
    //int order;
    
    String content;
    
    NLDialogueAct(String daVal, float aVal, float vVal) {
    	da = daVal;
    	arousal = aVal;
    	url ="";    	    	
    	valence = vVal;   
    }
    
    public void setDAContent(String str){
    	content = str;
    }

    public String getDAContent() {
    	return content;
    }
    
    public String getDialogueAct() {
    	return da;    	
    }
        
    public float getArousal() {
    	return arousal;
    }
    
    public float getValence() {
    	return valence;
    }
    
    public void setURL(String str){
    	url = str;
    }

    public String getURL() {
    	return url;
    }
    
    /*
    public void setOrder(int orderVal) {
    	order = orderVal;
    }
    
    public int getOrder() {
    	return order;
    }
    */
    
    public abstract OutputFormat getOutputFormat();
            
}