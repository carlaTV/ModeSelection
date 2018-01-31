package edu.upf.taln.onto.mode_selection;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.commons.configuration.tree.DefaultExpressionEngine;

/**
 *
 * @author rcarlini
 */
public class UserProfileIni {
    
    private static final String IDENTITY_SECTION = "identity";
    private static final String NAME_KEY = "name";
    private static final String GENDER_KEY = "gender";
    private static final String AGE_KEY = "age";

    private static final String CULTURE_SECTION = "culture";
    private static final String COUNTRY_KEY = "country";

    private static final String PERSONALITY_SECTION = "personality";
    private static final String TYPE_KEY = "type";
    private static final String PROXIMITY_KEY = "proximity";
    private static final String PERSONALITY_KEY = "personality";

    private final HierarchicalINIConfiguration config;	

    public UserProfileIni(File iniFile) throws ConfigurationException {
        config = new HierarchicalINIConfiguration(iniFile);
        
        DefaultExpressionEngine engine = new DefaultExpressionEngine();
        engine.setPropertyDelimiter("#");
        config.setExpressionEngine(engine);        
    	
        config.setDelimiterParsingDisabled(true);
        config.refresh();
    }
    
    public String getName() {
        SubnodeConfiguration confSection = config.getSection(IDENTITY_SECTION);
        return confSection.getString(NAME_KEY);
    }
    
    public String getGender() {
        SubnodeConfiguration confSection = config.getSection(IDENTITY_SECTION);
        return confSection.getString(GENDER_KEY);
    }
    
    public int getAge() {
        SubnodeConfiguration confSection = config.getSection(IDENTITY_SECTION);
        return confSection.getInt(AGE_KEY);
    }
    
    public String getCountry() {
        SubnodeConfiguration confSection = config.getSection(CULTURE_SECTION);
        return confSection.getString(COUNTRY_KEY);
    }
    
    public String getType() {
        SubnodeConfiguration confSection = config.getSection(PERSONALITY_SECTION);
        return confSection.getString(TYPE_KEY);
    }
    
    public String getProximity() {
        SubnodeConfiguration confSection = config.getSection(PERSONALITY_SECTION);
        return confSection.getString(PROXIMITY_KEY);
    }
     public String getPersonality() {
        SubnodeConfiguration confSection = config.getSection(PERSONALITY_SECTION);
        return confSection.getString(PERSONALITY_KEY);
    }
}

