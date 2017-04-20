package edu.upf.taln.onto.mode_selection;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;

/**
 *
 * @author rcarlini
 */
public class ModeSelectionTest {

    public ModeSelectionTest() {
    }

    @Test
    public void testGreet() throws Exception {

        String str = FileUtils.readFileToString(new File("src/test/resources/eatPork.rdf"));    	

        UserProfileIni profile = new UserProfileIni(new File("src/test/resources/young_german.ini"));
        
        ModeSelection mdParser = new ModeSelection(str, profile);

        Set<Integer> keys;
        Map<Integer, String> tmp;

        System.out.println("---------------- nonVerbal: ");
        tmp = mdParser.getNonVerbalDialogueElements();
        keys = tmp.keySet();
        for (Integer key : keys) {
            System.out.println(key + " " + tmp.get(key));
        }

        System.out.println("----------------- verbal: ");
        tmp = mdParser.getVerbalDialogueElements();
        keys = tmp.keySet();
        for (Integer key : keys) {
            System.out.println(key + " " + tmp.get(key));
            //System.out.println(tmp.get(key));
        }
    }
    
    @Test
    public void testPork() throws Exception {

    	String str = FileUtils.readFileToString(new File("src/test/resources/eatPork.rdf"));

        UserProfileIni profile = new UserProfileIni(new File("src/test/resources/user_profile.ini"));
        
        ModeSelection mdParser = new ModeSelection(str, profile);

        Set<Integer> keys;
        Map<Integer, String> tmp;

        System.out.println("---------------- nonVerbal: ");
        tmp = mdParser.getNonVerbalDialogueElements();
        keys = tmp.keySet();
        for (Integer key : keys) {
            System.out.println(key + " " + tmp.get(key));
        }

        System.out.println("----------------- verbal: ");
        tmp = mdParser.getVerbalDialogueElements();
        keys = tmp.keySet();
        for (Integer key : keys) {
            System.out.println(key + " " + tmp.get(key));            
        }
    }
    
    @Test
    public void testProfileJoan() throws Exception {
       throw new Exception();
    }
}
