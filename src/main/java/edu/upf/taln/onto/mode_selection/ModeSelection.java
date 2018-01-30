package edu.upf.taln.onto.mode_selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.hp.hpl.jena.rdf.model.*;
import java.io.File;
import org.apache.commons.configuration.ConfigurationException;

public final class ModeSelection {

    static String dialogueIRI = "http://kristina-project.eu/ontologies/dialogue_actions";
    static String modeSelectionIRI = "http://kristina-project.eu/ontologies/mode_selection";
    static String ontoIRI = "http://kristina-project.eu/ontologies/la/onto";
    static String contextIRI = "http://kristina-project.eu/ontologies/la/context";
    static String baseIRI = "http://kristina-project.eu/ms";
    static int counter = 0;

    static String iniFilePath = "src/main/resources/";

    public static void setIniFilePath(String ontoIniDirectory) {
        iniFilePath = ontoIniDirectory;
    }

    Map<Integer, String> verbalDialogueElements = new HashMap<>();
    Map<Integer, String> nonVerbalDialogueElements = new HashMap<>();

    enum Mode {

        VERBAL, NON_VERBAL
    };

    public static UserProfileIni loadProfile(String language, String scenario) throws ConfigurationException {

        File profileFile = new File(iniFilePath + "proto2_ms_profile_" + scenario + "_" + language + ".ini");
        if (!profileFile.exists()) {
            profileFile = new File(iniFilePath + "default_profile.ini");
        }
        return new UserProfileIni(profileFile);
    }

    public ModeSelection(String owlStr, UserProfileIni profile) throws CustomException, UnsupportedEncodingException {

        SystemAction sa = new SystemAction(owlStr);

        processResponses(sa, profile);
    }

    private void processResponses(SystemAction systemAction, UserProfileIni profile) throws CustomException {

        float valence = systemAction.getValence();
        float arousal = systemAction.getArousal();
        float defV;
        float defA;
        float V = 0;
        float A = 0;

        float ka = 0;
        float kv = 0;

        String facExpr = null;

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

            if (daClass.equals("PersonalGreet") || daClass.equals("SimpleGreet")) { // create verbal owl DA
                mode = Mode.VERBAL;
                
                facExpr = "joyful";
                defV = (float) 0.67;
                defA = (float) 0.32;

                if (defV < valence) {
                    A = defA;
                    V = valence;

                } else {
                    A = defA;
                    V = defV;
                }
                
                if (country.equals("es") || country.equals("Turkey")) {
                    kv = (float) 1.5;
                    V = V * kv;
                    ka = 1;
                    A = A * ka;
                }
                if (country.equals("ge") || country.equals("Poland")) {
                    kv = (float) 1.25;
                    V = V * kv;
                    ka = (float) 0.80;
                    A = A * ka;
                }
            }
            if (daClass.equals("Thank") || daClass.equals("AnswerThank")) { // create verbal owl DA

                mode = Mode.VERBAL;

                defV = (float) 0.68;
                defA = (float) 0.27;

                if (defV < valence) {
                    A = defA;
                    V = valence;

                } else {
                    A = defA;
                    V = defV;
                }

                facExpr = "grateful";
                
                if (country.equals("es") || country.equals("Turkey")) {
                    kv = (float) 1.10;
                    V = V * kv;
                    ka = (float) 1.20;
                    A = A * ka;
                }
                if (country.equals("ge") || country.equals("Poland")) {
                    kv = (float) 1.10;
                    V = V * kv;
                    ka = (float) 0.80;
                    A = A * ka;
                }

            }
            if (daClass.equals("Apologise")) { // create verbal owl DA

                mode = Mode.VERBAL;

                defV = (float) -0.16;
                defA = (float) -0.29;

                if (defV < valence) {
                    A = defA;
                    V = valence;

                } else {
                    A = defA;
                    V = defV;
                }

                facExpr = "apologetic";
                
                if (country.equals("es") || country.equals("Turkey")) {
                    kv = (float) 0.75;
                    V = V * kv;
                    ka = (float) 0.80;
                    A = A * ka;
                }
                if (country.equals("ge") || country.equals("Poland")) {
                    kv = (float) 1.20;
                    V = V * kv;
                    ka = (float) 1.00;
                    A = A * ka;
                }
            }
            if (daClass.equals("GoodBye") || daClass.equals("SayGoodBye")) { // create verbal owl DA
                mode = Mode.VERBAL;

                defV = (float) 0.54;
                defA = (float) 0.59;

                if (defV < valence) {
                    A = defA;
                    V = valence;

                } else {
                    A = defA;
                    V = defV;
                }
                
                facExpr = "content";
                
                if (country.equals("es") || country.equals("Turkey")) {
                    kv = (float) 1.30;
                    V = V * kv;
                    ka = (float) 1.00;
                    A = A * ka;
                }
                if (country.equals("ge") || country.equals("Poland")) {
                    kv = (float) 1.00;
                    V = V * kv;
                    ka = (float) 0.75;
                    A = A * ka;
                }

            }
            if (daClass.equals("ReadNewspaper") || daClass.equals("ShowWebpage") || daClass.equals("Canned") ) {
                mode = Mode.VERBAL;
                defV = (float) 0.75;
                defA = (float) 0.48;

                if (defV < valence) {
                    A = defA;
                    V = valence;

                } else {
                    A = defA;
                    V = defV;
                }

                facExpr = "enthusiastic";
                
                if (country.equals("es") || country.equals("Turkey")) {
                    kv = (float) 1.50;
                    V = V * kv;
                    ka = (float) 1.00;
                    A = A * ka;
                }
                if (country.equals("ge") || country.equals("Poland")) {
                    kv = (float) 1.25;
                    V = V * kv;
                    ka = (float) 0.75;
                    A = A * ka;
                }
            }

            if (daClass.equals("AskTask") || daClass.equals("AskTaskFollowUp")) {
                mode = Mode.VERBAL;
                defV = (float) 0.40;
                defA = (float) 0.60;

                if (defV < valence) {
                    A = defA;
                    V = valence;

                } else {
                    A = defA;
                    V = defV;
                }
                
                facExpr = "curious";
                
                if (country.equals("es") || country.equals("Turkey")) {
                    kv = (float) 1.30;
                    V = V * kv;
                    ka = (float) 1.00;
                    A = A * ka;
                }
                if (country.equals("ge") || country.equals("Poland")) {
                    kv = (float) 1.00;
                    V = V * kv;
                    ka = (float) 0.75;
                    A = A * ka;
                }
            }

            if (daClass.equals("CalmDown")) {
                mode = Mode.VERBAL;
                defV = (float) 0.70;
                defA = (float) -0.14;

                if (defV < valence) {
                    A = defA;
                    V = valence;

                } else {
                    A = defA;
                    V = defV;
                }
                
                facExpr = "caring";
                
                if (country.equals("es") || country.equals("Turkey")) {
                    kv = (float) 0.8;
                    V = V * kv;
                    ka = (float) 1.00;
                    A = A * ka;
                }
                if (country.equals("ge") || country.equals("Poland")) {
                    kv = (float) 1.00;
                    V = V * kv;
                    ka = (float) 1.20;
                    A = A * ka;
                }
            }
            if (daClass.equals("Ack")) {
                mode = Mode.VERBAL;
                defV = (float) 0.51;
                defA = (float) 0.00;

                if (defV < valence) {
                    A = defA;
                    V = valence;

                } else {
                    A = defA;
                    V = defV;
                }
                
                facExpr = "relaxed";
                
                if (country.equals("es") || country.equals("Turkey")) {
                    kv = (float) 1.50;
                    V = V * kv;
                    ka = (float) 1.00;
                    A = A * ka;
                }
                if (country.equals("ge") || country.equals("Poland")) {
                    kv = (float) 1.25;
                    V = V * kv;
                    ka = (float) 1.00;
                    A = A * ka;
                }
            }
            if (daClass.equals("Reject")) {
                mode = Mode.VERBAL;
                defV = (float) 0.07;
                defA = (float) 0.02;

                if (defV < valence) {
                    A = defA;
                    V = valence;

                } else {
                    A = defA;
                    V = defV;
                }
                
                facExpr = "serious";
                
                if (country.equals("es") || country.equals("Turkey")) {
                    kv = (float) 3.00;
                    V = V * kv;
                    ka = (float) 1.00;
                    A = A * ka;
                }
                if (country.equals("ge") || country.equals("Poland")) {
                    kv = (float) 1.50;
                    V = V * kv;
                    ka = (float) 1.00;
                    A = A * ka;
                }
            }
            
            if (daClass.equals("Declare")) {
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
                        //addFacialExpr(modelTmp, dialogAct, "smiley");
                        //canviem dialogAct per classInsRes:
                        addFacialExpr(modelTmp, SwabPockets, "smiley");
                        addFacialIntensity(modelTmp, SwabPockets, "high");
                    } else {
                        addFacialExpr(modelTmp, dialogAct, "smiley");
                        addFacialIntensity(modelTmp, dialogAct, "low");
                    }
                }
                
                //weather
                /*if (daClass.equals("ShowWeather")){
                mode = Mode.VERBAL;
                modelTmp = createVerbal(dialogAct, arousal, valence, counter);
                //WEATHER
                Resource Cold = getWeather(modelTmp, "cold");
                    if (Cold != null) {
                        System.out.println("This is the instance of the class cold: " + Cold.toString());
                        addFacialExpr(modelTmp, Cold, "neutral");
                        //addFacialIntensity(modelTmp, Cold, "high");
                    }
                    Resource hot = getWeather(modelTmp, "hot");
                    if (hot != null) {
                        System.out.println("This is the instance of the class cold: " + Cold.toString());
                        addFacialExpr(modelTmp, hot, "smiley");
                        addFaceEnthusiasm(modelTmp, hot, "high");
                    }
                
                }*/
                
            }
            modelTmp = createVerbal(dialogAct, A, V, counter);
           
            addFacialExpr(modelTmp, dialogAct, facExpr);

            if (V > 0 && kv > 1) {
                if (kv > 1) {
                    if (ka > 1) {
                        //CASE 1
                        //modelTmp, dialogAct, "proximity", "style", "personality", "expressivity", facExpr, "social"
                        addCharacteristics(modelTmp, dialogAct, "close", "informal", "extroverted", "high", facExpr, "colloquial");  
                    } else {
                        //CASE 2
                        addCharacteristics(modelTmp, dialogAct, "close", "informal", "introverted", "medium", facExpr, "heartly");
                    }
                }if (kv <= 1){
                    if(ka > 1){
                        //CASE 3
                        addCharacteristics(modelTmp, dialogAct, "distant", "formal", "extroverted", "medium", facExpr, "heartly");
                    }else{
                        //CASE 4
                        addCharacteristics(modelTmp, dialogAct, "distant", "formal", "introverted", "low", facExpr, "reserved");
                    }
                }
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

    /*public Resource getWeather(Model model, String className) {

        Resource insObj = null;
        RSIterator iter = model.listReifiedStatements();
        while (iter.hasNext()) {
            ReifiedStatement stmt = iter.nextRS();
            if (stmt.getStatement().getObject().toString().equals(ontoIRI + "#weatherClassification&gt;&#xD;\n" +
            "\\\"" + className)) {
                insObj = stmt.getStatement().getSubject();
            }
        }

        return insObj;

    }*/
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

    public void addCharacteristics(Model modelTmp, Resource dialogAct, String proximity, String style, String personality, String expressivity, String attitude, String social) {

        //PROXIMITY
        Property hasProximity = modelTmp.getProperty(modeSelectionIRI + "#" + "hasProximity");
        Literal proxLiteral = modelTmp.createLiteral(proximity);
        modelTmp.addLiteral(dialogAct, hasProximity, proxLiteral);

        //PERSONALITY
        Property hasPersonality = modelTmp.getProperty(modeSelectionIRI + "#" + "hasPersonality");
        Literal persLiteral = modelTmp.createLiteral(personality);
        modelTmp.addLiteral(dialogAct, hasPersonality, persLiteral);

        //STYLE
        Property hasStyle = modelTmp.getProperty(modeSelectionIRI + "#" + "hasStyle");
        Literal styleLiteral = modelTmp.createLiteral(style);
        modelTmp.addLiteral(dialogAct, hasStyle, styleLiteral);

        //EXPRESSIVITY
        Property hasExpressivity = modelTmp.getProperty(modeSelectionIRI + "#" + "hasExpressivity");
        Literal expLiteral = modelTmp.createLiteral(expressivity);
        modelTmp.addLiteral(dialogAct, hasExpressivity, expLiteral);

        //ATTITUDE
        Property hasAttitude = modelTmp.getProperty(modeSelectionIRI + "#" + "hasAttitude");
        Literal attiLiteral = modelTmp.createLiteral(attitude);
        modelTmp.addLiteral(dialogAct, hasAttitude, attiLiteral);

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
