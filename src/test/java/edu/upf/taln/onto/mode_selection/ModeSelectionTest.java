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
    
    public void genericTest(String RDFfilePath, String profileFilePath) throws Exception {

        String str = FileUtils.readFileToString(new File(RDFfilePath));    
        //str = "<rdf:RDF\r\n    xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\r\n    xmlns:j.0=\"http://kristina-project.eu/ontologies/dialogue_actions#\" > \r\n  <rdf:Description rdf:about=\"http://kristina-project.eu/tmp#9cd2e567-112f-478f-8ffe-53e82461e660\">\r\n    <j.0:startWith rdf:resource=\"http://kristina-project.eu/tmp#fda93f3b-858f-40fc-b778-40da7bb41a64\"/>\r\n    <j.0:containsSystemAct rdf:resource=\"http://kristina-project.eu/tmp#fda93f3b-858f-40fc-b778-40da7bb41a64\"/>\r\n    <j.0:hasArousal rdf:datatype=\"http://www.w3.org/2001/XMLSchema#double\">0.25</j.0:hasArousal>\r\n    <j.0:hasValence rdf:datatype=\"http://www.w3.org/2001/XMLSchema#double\">0.1</j.0:hasValence>\r\n    <rdf:type rdf:resource=\"http://kristina-project.eu/ontologies/dialogue_actions#SystemAction\"/>\r\n  </rdf:Description>\r\n  <rdf:Description rdf:about=\"http://kristina-project.eu/tmp#fda93f3b-858f-40fc-b778-40da7bb41a64\">\r\n    <rdf:type rdf:resource=\"http://kristina-project.eu/ontologies/dialogue_actions#SimpleGreet\"/>\r\n    <j.0:isBelief rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">false</j.0:isBelief>\r\n    <j.0:isAdvice rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">false</j.0:isAdvice>\r\n    <j.0:isFormal rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">false</j.0:isFormal>\r\n    <j.0:directness rdf:datatype=\"http://www.w3.org/2001/XMLSchema#float\">0.0</j.0:directness>\r\n    <j.0:verbosity rdf:datatype=\"http://www.w3.org/2001/XMLSchema#float\">0.0</j.0:verbosity>\r\n  </rdf:Description>\r\n</rdf:RDF>\r\n";

        UserProfileIni profile = new UserProfileIni(new File(profileFilePath));
        
        ModeSelection mdParser = new ModeSelection(str, profile);

        Set<Integer> keys;
        Map<Integer, String> tmp;

        System.out.println("---------------- nonVerbal: ");
        tmp = mdParser.generateModeRDF(str, profile, ModeSelection.Mode.NON_VERBAL);
        keys = tmp.keySet();
        for (Integer key : keys) {
            System.out.println(key + " " + tmp.get(key));
        }

        System.out.println("----------------- verbal: ");
        tmp = mdParser.generateModeRDF(str, profile, ModeSelection.Mode.VERBAL);
        keys = tmp.keySet();
        for (Integer key : keys) {
            System.out.println(key + " " + tmp.get(key));
            //System.out.println(tmp.get(key));
        }
    }
    
    @Test
    public void testGreet() throws Exception {

        genericTest("src/test/resources/dm-output-greet.rdf", "src/test/resources/young_german.ini");
    }
    
    @Test
    public void testGoodBye() throws Exception {
        
        genericTest("src/test/resources/dm-output-GoodBye.rdf", "src/test/resources/user_profile.ini");
    }
    
    @Test
    public void testAskTask() throws Exception {
        genericTest("src/test/resources/dm-output-AskTask.rdf", "src/test/resources/user_profile.ini");
    }
    
    @Test
    public void testCalmDown() throws Exception {
        genericTest("src/test/resources/dm-output-CalmDown.rdf", "src/test/resources/user_profile.ini");
    }
    
    @Test
    public void testAck() throws Exception {
        genericTest("src/test/resources/dm-output-Ack.rdf", "src/test/resources/user_profile.ini");
    }
    
    @Test
    public void testPork() throws Exception {
        genericTest("src/test/resources/notEatPork.rdf", "src/test/resources/user_profile.ini");
    }
    
    @Test
    public void testAllergy() throws Exception {
        genericTest("src/test/resources/lactoseAllergy.rdf", "src/test/resources/user_profile.ini");
    }
    
    @Test
    public void testNewspaper() throws Exception {
        genericTest("src/test/resources/article.rdf", "src/test/resources/young_german.ini");
    }
    
    @Test
    public void testASkTask() throws Exception {
        genericTest("src/test/resources/dm-output-AskTask.rdf", "src/test/resources/young_german.ini");
    }
    
    @Test
    public void testAskTaskFollowUp() throws Exception {
        genericTest("src/test/resources/dm-output-AskTaskFollowUp.rdf", "src/test/resources/young_german.ini");
    }
    
    @Test
    public void testSimpleMotivate() throws Exception {
        genericTest("src/test/resources/dm-output-SimpleMotivate.rdf", "src/test/resources/young_german.ini");
    }
    
    @Test
    public void testWeather() throws Exception {
        genericTest("src/test/resources/dm-output-weather.rdf", "src/test/resources/young_german.ini");
    }
}