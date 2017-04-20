package edu.upf.taln.onto.mode_selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.hp.hpl.jena.rdf.model.*;

public final class ModeSelection {

    static String dialogueIRI = "http://kristina-project.eu/ontologies/dialogue_actions";
    static String modeSelectionIRI = "http://kristina-project.eu/ontologies/mode_selection";
    static String ontoIRI = "http://kristina-project.eu/ontologies/la/onto";
    static String contextIRI = "http://kristina-project.eu/ontologies/la/context";
    static String baseIRI = "http://kristina-project.eu/ms";
    static int counter = 0;

    Map<Integer, String> verbalDialogueElements = new HashMap<>();
    Map<Integer, String> nonVerbalDialogueElements = new HashMap<>();

    enum Mode {

        VERBAL, NON_VERBAL
    };

    public ModeSelection(String dmOutputOWL, UserProfileIni profile) throws CustomException, UnsupportedEncodingException {

        SystemAction sa = new SystemAction(dmOutputOWL);

        processResponses(sa, profile);
    }

    private void processResponses(SystemAction systemAction, UserProfileIni profile) throws CustomException {

        float valence = systemAction.getValence();
        float arousal = systemAction.getArousal();
        List<Resource> dialogActs = systemAction.getDialogActs();

        //identity
        String gender = profile.getGender();
        int age = profile.getAge();
        //culture
        String country = profile.getCountry();
        //personality
        String proximity = profile.getProximity();
        String personality = profile.getPersonality();

        // process the read DAs in order to assign them to verbal vs non-verbal output and add respective mode-selection tags		
        for (int order = 0; order < dialogActs.size(); order++) {

            Resource dialogAct = dialogActs.get(order);

            String daClass = systemAction.getClass(dialogAct); // get the type of the DA instance
            System.out.println(order + " DA is " + daClass);

            Mode mode = null;
            Model modelTmp = null;

            if (daClass.equals("PersonalGreet") | daClass.equals("Greeting")) { // create verbal owl DA
                mode = Mode.VERBAL;
                modelTmp = createVerbal(dialogAct, arousal, valence, counter);
                addFacialExpr(modelTmp, dialogAct, "friendly");
                if (gender.equals("female") | gender.equals("male") && age >= 70) { //elder people
                    if (country.equals("es")) {
                        if (proximity.equals("close") && personality.equals("extroverted")) {
                            addCharacteristics(modelTmp, dialogAct, "very expressive", "close", "joyful", "informal", "colloquial");
                        }
                    }
                    if (country.equals("ge")) {
                        if (proximity.equals("distant") && personality.equals("introverted")) {
                            addCharacteristics(modelTmp, dialogAct, "expressive", "medium close", "neutral", "formal", "reserved");
                        }
                    }
                }
                if (gender.equals("male") | gender.equals("female") && age <= 40) {
                    if (country.equals("es")) {
                        if (proximity.equals("close") && personality.equals("extroverted")) {
                            addCharacteristics(modelTmp, dialogAct, "expressive", "close", "joyful", "informal", "colloquial");
                        }
                    }
                    if (country.equals("ge")) {
                        if (proximity.equals("distant") && personality.equals("introverted")) {
                            addCharacteristics(modelTmp, dialogAct, "expressive", "medium close", "joyful", "formal", "heartly");
                        }
                    }
                }

            }
            if (daClass.equals("Thank") | daClass.equals("AnswerThank")) { // create verbal owl DA

                mode = Mode.VERBAL;
                modelTmp = createVerbal(dialogAct, arousal, valence, counter);
                addFacialExpr(modelTmp, dialogAct, "friendly");

                if (gender.equals("female") | gender.equals("male")) { //elder people

                    if (country.equals("es")) {
                        if (proximity.equals("close") && personality.equals("extroverted")) {
                            if (age >= 70) { //spanish elderly
                                addCharacteristics(modelTmp, dialogAct, "expressive", "close", "thankful", "formal", "heartly");
                            }
                            if (age <= 40) { //spanish young
                                addCharacteristics(modelTmp, dialogAct, "expressive", "close", "thankful", "informal", "heartly");
                            }
                        }
                    }
                    if (country.equals("ge")) {
                        if (proximity.equals("distant") && personality.equals("introverted")) {
                            if (age >= 70) { //spanish elderly
                                addCharacteristics(modelTmp, dialogAct, "expressive", "distant", "thankful", "formal", "heartly");
                            }
                            if (age <= 40) { //spanish young
                                addCharacteristics(modelTmp, dialogAct, "expressive", "distant", "thankful", "formal", "heartly");
                            }
                        }
                    }
                }
            }
            if (daClass.equals("Apologise")) { // create verbal owl DA

                mode = Mode.VERBAL;
                modelTmp = createVerbal(dialogAct, arousal, valence, counter);
                addFacialIntention(modelTmp, dialogAct, "apologetic");
                if (gender.equals("female") | gender.equals("male")) { //elder people

                    if (proximity.equals("distant") && personality.equals("introverted")) {
                        if (age >= 70) {
                            if (country.equals("es")) {

                                addCharacteristics(modelTmp, dialogAct, "expressive", "close", "serious", "formal", "heartly");
                            }
                            if (country.equals("ge")) {

                                addCharacteristics(modelTmp, dialogAct, "lower expressive", "distant", "serious", "formal", "heartly");
                            }
                        }
                        if (age <= 40) {
                            addCharacteristics(modelTmp, dialogAct, "expressive", "distant", "serious", "formal", "heartly");
                        }

                    }
                    if (proximity.equals("close") && personality.equals("introverted")) {
                        addCharacteristics(modelTmp, dialogAct, "expressive", "close", "serious", "formal", "heartly");
                    }
                }
            }
            if (daClass.equals("GoodBye") | daClass.equals("SayGoodBye")) { // create verbal owl DA
                mode = Mode.VERBAL;
                modelTmp = createVerbal(dialogAct, arousal, valence, counter);
                addFacialExpr(modelTmp, dialogAct, "friendly");
                if (gender.equals("female") | gender.equals("male")) { //elder people
                    if (country.equals("es")) {
                        if (proximity.equals("close") && personality.equals("extroverted")) {
                            if (age >= 70) {
                                addCharacteristics(modelTmp, dialogAct, "expressive ", "close", "joyful", "formal", "heartly");

                            }
                            if (age <= 40) {
                                addCharacteristics(modelTmp, dialogAct, "expressive", "close", "joyful", "informal", "colloquial");
                            }
                        }
                    }
                    if (country.equals("ge")) {
                        if (personality.equals("introverted")) {
                            if (proximity.equals("distant") && age >= 70) {
                                addCharacteristics(modelTmp, dialogAct, "expressive", "distant", "joyful", "formal", "reserved");

                            }
                            if (proximity.equals("close") && age <= 40) {
                                addCharacteristics(modelTmp, dialogAct, "expressive", "close", "joyful", "formal", "reserved");
                            }
                        }
                    }
                }

            }
            if (daClass.equals("Declare") | daClass.equals("Statement")) {
                mode = Mode.VERBAL;
                modelTmp = createVerbal(dialogAct, arousal, valence, counter);

                //PORK
                Resource Pork = getResourceByClass(modelTmp, "Pork");
                if (Pork != null) {
                    System.out.println("This is the instance of the class pork: " + Pork.toString());
                    addFacialIntention(modelTmp, Pork, "apologetic");
                }

                //ALLERGY
                Resource Allergy = getResourceByClass(modelTmp, "Allergy");
                if (Allergy != null) {
                    System.out.println("This is the instance of the class Allergy: " + Allergy.toString());
                    addFacialIntention(modelTmp, Allergy, "worried");
                }

                //Swabian pokets
                Resource SwabPockets = getResourceByClass(modelTmp, "SwabianPockets");
                if (SwabPockets != null) {
                    System.out.println("This is the instance of the class Swabian Pockets: " + SwabPockets.toString());
                    if (country.equals("ge")) {
                        addFacialExpr(modelTmp, dialogAct, "smiley");
                        addFacialIntensity(modelTmp, dialogAct, "high");
                    } else {
                        addFacialExpr(modelTmp, dialogAct, "smiley");
                        addFacialIntensity(modelTmp, dialogAct, "low");
                    }
                }
                /*Resource falseTruthValueRes = getFalseTruthValueResource(modelTmp);
                if (Pork != null | SwabPockets != null) {
                    System.out.println("This is the instance whose truth value is false: " + falseTruthValueRes.toString());
                    addYesNoFacialExpr(modelTmp, dialogAct, "apologetic");
                }*/
            }
            if (daClass.equals("ShowWeather")){
                //WEATHER
                Resource Cold = getResourceByClass(modelTmp, "cold");
                if (Cold != null) {
                    System.out.println("This is the instance of the class cold: " + Cold.toString());
                    addFacialExpr(modelTmp, Cold, "neutral");
                    //addFacialIntensity(modelTmp, Cold, "high");
                }
                Resource hot = getResourceByClass(modelTmp, "hot");
                if (hot != null) {
                    System.out.println("This is the instance of the class cold: " + Cold.toString());
                    addFacialExpr(modelTmp, hot, "smiley");
                    addFaceEnthusiasm(modelTmp, hot, "high");
                }
                
            }
            
            if (daClass.equals("ReadNewspaper") | daClass.equals("ShowWebpage") ){
                addFacialExpr(modelTmp, dialogAct, "default");
            }
            
            if (daClass.equals("AskTask") | daClass.equals("AskTaskFollowUp")){
                addFacialExpr(modelTmp, dialogAct, "smiley");
            }
            
            if (daClass.equals("SimpleMotivate")){
                addFacialExpr(modelTmp, dialogAct, "smiley");
            }
                        
            if (modelTmp != null && mode != null) {
                addDA(modelTmp, mode, order);
            }

        }

    }

    public void addDA(Model model, Mode mode, Integer order) {

        ByteArrayOutputStream nonVerbalStream = new ByteArrayOutputStream();
        model.write(nonVerbalStream, "RDF/XML");
        String owlStr = nonVerbalStream.toString();

        if (mode == Mode.NON_VERBAL) {
            nonVerbalDialogueElements.put(order, owlStr);
        } else { // 
            verbalDialogueElements.put(order, owlStr);
        }
        model.close();

    }

    private Model createNonVerbal(int order, float arousal, float valence, String responseCls, int counter) {

        Model modelTmp = ModelFactory.createDefaultModel();
        Property rdfType = modelTmp.getProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        Property hasOrder = modelTmp.createProperty(dialogueIRI + "#" + "hasOrder");
        Property hasArousal = modelTmp.createProperty(dialogueIRI + "#" + "hasArousal");
        Property hasValence = modelTmp.createProperty(dialogueIRI + "#" + "hasValence");

        Resource daRes = modelTmp.createResource(baseIRI + "ins" + counter++);
        Resource daCls = modelTmp.createResource(dialogueIRI + "#" + responseCls);
        daRes.addProperty(rdfType, daCls);

        Literal aLiteral = modelTmp.createTypedLiteral(arousal);
        daRes.addLiteral(hasArousal, aLiteral);
        Literal vLiteral = modelTmp.createTypedLiteral(valence);
        daRes.addLiteral(hasValence, vLiteral);
        Literal oLiteral = modelTmp.createTypedLiteral(order);
        daRes.addLiteral(hasOrder, oLiteral);

        Property hasExpressivity = modelTmp.getProperty(modeSelectionIRI + "#" + "hasExpressivity");
        Literal expLiteral = modelTmp.createLiteral("very expressive");
        daRes.addLiteral(hasExpressivity, expLiteral);

        return modelTmp;
    }

    private Model createVerbal(Resource da, float arousal, float valence, int counter) {

        Model modelTmp = ModelFactory.createDefaultModel();

        StmtIterator iter = da.listProperties();
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();

            if (!"followedBy".equals(removePrefix(stmt.getPredicate().toString()))) {
                modelTmp.add(stmt);
                if ("containsSemantics".equals(removePrefix(stmt.getPredicate().toString()))) {
                    StmtIterator reifiedIter = stmt.getObject().asResource().listProperties();
                    while (reifiedIter.hasNext()) {
                        modelTmp.add(reifiedIter.nextStatement());
                    }
                }
            }
        }

        Property rdfType = modelTmp.getProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        Property hasOrder = modelTmp.createProperty(dialogueIRI + "#" + "hasOrder");
        Property hasArousal = modelTmp.createProperty(dialogueIRI + "#" + "hasArousal");
        Property hasValence = modelTmp.createProperty(dialogueIRI + "#" + "hasValence");
        Property containsSystemAct = modelTmp.createProperty(dialogueIRI + "#" + "containsSystemAct");

        Resource actionObj = modelTmp.createResource(baseIRI + "ins" + counter++);
        Resource systemActionClass = modelTmp.createResource(dialogueIRI + "#" + "SystemAction"); // to facilitate parsing (recognition of DA object) in OntoDialogueAct
        modelTmp.add(actionObj, rdfType, systemActionClass);

        Literal aLiteral = modelTmp.createTypedLiteral(arousal);
        modelTmp.addLiteral(actionObj, hasArousal, aLiteral);
        Literal vLiteral = modelTmp.createTypedLiteral(valence);
        modelTmp.addLiteral(actionObj, hasValence, vLiteral);
        modelTmp.add(actionObj, containsSystemAct, da);
        /*
        //EXPRESSIVITY (copied from non verbal)
        Property hasExpressivity = modelTmp.getProperty(modeSelectionIRI + "#" + "hasExpressivity");
        Literal expLiteral = modelTmp.createLiteral("very expressive");
        da.addLiteral(hasExpressivity, expLiteral);
         */

        return modelTmp;

    }

    public String decode(String str) throws UnsupportedEncodingException {
        return URLDecoder.decode(str, "UTF-8");
    }

    public Map<Integer, String> getVerbalDialogueElements() {
        return verbalDialogueElements;
    }

    public Map<Integer, String> getNonVerbalDialogueElements() {
        return nonVerbalDialogueElements;
    }

    public String removePrefix(String uri) {
        return uri.substring(uri.lastIndexOf('#') + 1);
    }

    public Resource getResourceByClass(Model model, String className) {

        Resource insObj = null;
        RSIterator iter = model.listReifiedStatements();
        while (iter.hasNext()) {
            ReifiedStatement stmt = iter.nextRS();
            if (stmt.getStatement().getObject().toString().equals(ontoIRI + "#" + className)) {
                insObj = stmt.getStatement().getSubject();
            }
        }

        return insObj;

    }

    public Resource getFalseTruthValueResource(Model model) {

        Resource insObj = null;
        RSIterator iter = model.listReifiedStatements();
        while (iter.hasNext()) {
            ReifiedStatement stmt = iter.nextRS();
            if (stmt.getStatement().getPredicate().toString().equals(contextIRI + "#" + "hasTruthValue")) {
                insObj = stmt.getStatement().getSubject();
            }
        }

        return insObj;

    }

    public void addCharacteristics(Model modelTmp, Resource dialogAct, String expressivity, String proximity, String attitude, String style, String social) {

        //EXPRESSIVITY
        Property hasExpressivity = modelTmp.getProperty(modeSelectionIRI + "#" + "hasExpressivity");
        Literal expLiteral = modelTmp.createLiteral(expressivity);
        modelTmp.addLiteral(dialogAct, hasExpressivity, expLiteral);

        //PROXIMITY
        Property hasProximity = modelTmp.getProperty(modeSelectionIRI + "#" + "hasProximity");
        Literal proxLiteral = modelTmp.createLiteral(proximity);
        modelTmp.addLiteral(dialogAct, hasProximity, proxLiteral);

        //ATTITUDE
        Property hasAttitude = modelTmp.getProperty(modeSelectionIRI + "#" + "hasAttitude");
        Literal attiLiteral = modelTmp.createLiteral(attitude);
        modelTmp.addLiteral(dialogAct, hasAttitude, attiLiteral);

        //STYLE
        Property hasStyle = modelTmp.getProperty(modeSelectionIRI + "#" + "hasStyle");
        Literal styleLiteral = modelTmp.createLiteral(style);
        modelTmp.addLiteral(dialogAct, hasStyle, styleLiteral);

        //SOCIAL
        Property hasSocial = modelTmp.getProperty(modeSelectionIRI + "#" + "hasSocial");
        Literal socLiteral = modelTmp.createLiteral(social);
        modelTmp.addLiteral(dialogAct, hasSocial, socLiteral);
    }

    public void addFacialExpr(Model modelTmp, Resource dialogAct, String facialExpr) {
        //TARGETED WORD
        Property FacialExpression = modelTmp.getProperty(modeSelectionIRI + "#" + "FacialExpression");
        Literal faceLiteral = modelTmp.createLiteral(facialExpr);
        modelTmp.addLiteral(dialogAct, FacialExpression, faceLiteral);
    }
    
    public void addFacialIntention(Model modelTmp, Resource dialogAct, String facialInt) {
        //TARGETED WORD
        Property facialIntention = modelTmp.getProperty(modeSelectionIRI + "#" + "facialIntention");
        Literal intentionLiteral = modelTmp.createLiteral(facialInt);
        modelTmp.addLiteral(dialogAct, facialIntention, intentionLiteral);
    }

    public void addFacialIntensity(Model modelTmp, Resource dialogAct, String intensity) {
        Property FacIntensity = modelTmp.getProperty(modeSelectionIRI + "#" + "FacialIntensity");
        Literal intensLiteral = modelTmp.createLiteral(intensity);
        modelTmp.addLiteral(dialogAct, FacIntensity, intensLiteral);
    }

    public void addFaceEnthusiasm(Model modelTmp, Resource dialogAct, String enthusiasm) {
        Property FacEnthusiasm = modelTmp.getProperty(modeSelectionIRI + "#" + "FacialEnthusiasm");
        Literal enthLiteral = modelTmp.createLiteral(enthusiasm);
        modelTmp.addLiteral(dialogAct, FacEnthusiasm, enthLiteral);
    }

    public void addYesNoFacialExpr(Model modelTmp, Resource dialogAct, String facialExpr) {
        //YES OR NO
        Property FacialExpression = modelTmp.getProperty(modeSelectionIRI + "#" + "FacialExpression");
        Literal faceLiteral = modelTmp.createLiteral(facialExpr);
        modelTmp.addLiteral(dialogAct, FacialExpression, faceLiteral);
    }
}
