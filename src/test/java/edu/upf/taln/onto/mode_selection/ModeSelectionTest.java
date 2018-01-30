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

        String str = FileUtils.readFileToString(new File("src/test/resources/dm-output-greet.rdf"));    	

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
    public void testApologize() throws Exception {

        String str = FileUtils.readFileToString(new File("src/test/resources/dm-output-apologize.rdf"));    	

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
    public void testThank() throws Exception {

        String str = FileUtils.readFileToString(new File("src/test/resources/dm-output-thank.rdf"));    	

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
            //System.out.println(tmp.get(key));
        }
    }
    
    @Test
    public void testGoodBye() throws Exception {

        String str = FileUtils.readFileToString(new File("src/test/resources/dm-output-GoodBye.rdf"));    	

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
            //System.out.println(tmp.get(key));
        }
    }
    
    @Test
    public void testAskTask() throws Exception {

        String str = FileUtils.readFileToString(new File("src/test/resources/dm-output-AskTask.rdf"));    	

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
            //System.out.println(tmp.get(key));
        }
    }
    
    @Test
    public void testCalmDown() throws Exception {

        String str = FileUtils.readFileToString(new File("src/test/resources/dm-output-CalmDown.rdf"));    	

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
            //System.out.println(tmp.get(key));
        }
    }
    
    @Test
    public void testAck() throws Exception {

        String str = FileUtils.readFileToString(new File("src/test/resources/dm-output-Ack.rdf"));    	

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
            //System.out.println(tmp.get(key));
        }
    }
    
    @Test
    public void testReject() throws Exception {

        String str = FileUtils.readFileToString(new File("src/test/resources/dm-output-Reject.rdf"));    	

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
            //System.out.println(tmp.get(key));
        }
    }
    
    @Test
    public void testPork() throws Exception {

    	String str = FileUtils.readFileToString(new File("src/test/resources/notEatPork.rdf"));

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
    public void testAllergy() throws Exception {

    	String str = FileUtils.readFileToString(new File("src/test/resources/lactoseAllergy.rdf"));

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
    public void testPockets() throws Exception {

    	String str = FileUtils.readFileToString(new File("src/test/resources/ContentRDFs/likeSwabianPockets.rdf"));

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
        }
    }
    
    @Test
    public void testNewspaper() throws Exception {

    	String str = FileUtils.readFileToString(new File("src/test/resources/article.rdf"));

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
        }
    }
    
    @Test
    public void testASkTask() throws Exception {

    	String str = FileUtils.readFileToString(new File("src/test/resources/dm-output-AskTask.rdf"));

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
        }
    }
    
    @Test
    public void testAskTaskFollowUp() throws Exception {

    	String str = FileUtils.readFileToString(new File("src/test/resources/dm-output-AskTaskFollowUp.rdf"));

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
        }
    }
    
    @Test
    public void testSimpleMotivate() throws Exception {

    	String str = FileUtils.readFileToString(new File("src/test/resources/dm-output-SimpleMotivate.rdf"));

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
        }
    }
    
    @Test
    public void testWeather() throws Exception {

    	String str = FileUtils.readFileToString(new File("src/test/resources/dm-output-weather.rdf"));

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
        }
    }
    
    @Test
    public void testProfileJoan() throws Exception {
       throw new Exception();
    }
}