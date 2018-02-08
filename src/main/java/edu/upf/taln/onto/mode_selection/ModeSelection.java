package edu.upf.taln.onto.mode_selection;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.hp.hpl.jena.rdf.model.*;
import edu.upf.taln.onto.mode_selection.EmotionSetting.Style;
import edu.upf.taln.onto.mode_selection.EmotionSetting.Expressivity;
import edu.upf.taln.onto.mode_selection.EmotionSetting.Personality;
import edu.upf.taln.onto.mode_selection.EmotionSetting.Proximity;
import edu.upf.taln.onto.mode_selection.EmotionSetting.Social;
import java.io.File;
import org.apache.commons.configuration.ConfigurationException;



public final class ModeSelection{

    static String dialogueIRI = "http://kristina-project.eu/ontologies/dialogue_actions";
    static String modeSelectionIRI = "http://kristina-project.eu/ontologies/mode_selection";
    static String ontoIRI = "http://kristina-project.eu/ontologies/la/onto";
    static String contextIRI = "http://kristina-project.eu/ontologies/la/context";
    static String baseIRI = "http://kristina-project.eu/ms";

    //strings for culture
    static String german = "de";
    static String spanish = "es";
    static String turkish = "tr";
    static String polish = "pl";

    static String iniFilePath = "src/main/resources/";

    public static void setIniFilePath(String ontoIniDirectory) {
        iniFilePath = ontoIniDirectory;
    }
    
    public static UserProfileIni loadProfile(String language, String scenario) throws ConfigurationException {

        File profileFile = new File(iniFilePath + "proto2_ms_profile_" + scenario + "_" + language + ".ini");
        if (!profileFile.exists()) {
            profileFile = new File(iniFilePath + "default_profile.ini");
        }
        return new UserProfileIni(profileFile);
    }
    
    public enum Mode {
        VERBAL, NON_VERBAL
    };

    enum DialogueAct {
      //strings for DAs
    PersonalGreet, SimpleGreet, MorningGreet, EveningGreet,
    ShareJoy, SimpleMotivate, Introduce, Thank, AnswerThank,
    SimpleGoodbye, PersonalSayGoodbye, MeetAgainSayGoodbye, 
    AskMood, SimpleApologise, PersonalApologise, ReadNewspaper, ShowWebpage, Canned,
    ProactiveList, IRResponse, ShowWeather, AskTask, AskTaskFollowUp, RequestFeedback, CalmDown, Console,
    CheerUp, Acknowledge, Accept, Affirm, Reject, Declare, Clarification, RequestRepeat, RequestRephrase,
    StateMissingComprehension, UnknownRequest, UnknownStatement, NotFound   
    }
    
    enum GenericDialogAct {
        Greeting, GoodBye, CalmDown, Acknowledge, AskTask, Clarify, Declare, Thank, AskMood, CheerUp, Apologise, Request
    }
    
    enum FacialExpression{
        joyful, apologetic, curious, caring, grateful, content, relaxed, enthusiastic, serious, neutral
    }

    Map<Integer, String> verbalDialogueElements = new HashMap<>();
    Map<Integer, String> nonVerbalDialogueElements = new HashMap<>();
    
    private final HashMap<String, EmotionSetting> emotions;
    private final Map<DialogueAct, GenericDialogAct> Da2tag;
    private final Map<GenericDialogAct, FacialExpression> tag2FacExpr;
    private final Map<FacialExpression, ValenceArousal> FacExpr2VA;
    
    public ModeSelection() {
        
        //map DA to tag
        Da2tag = new HashMap<>();
        Da2tag.put(DialogueAct.Accept,          GenericDialogAct.Acknowledge);
        Da2tag.put(DialogueAct.Acknowledge,     GenericDialogAct.Acknowledge);
        Da2tag.put(DialogueAct.Affirm,          GenericDialogAct.Acknowledge);
        Da2tag.put(DialogueAct.AnswerThank,     GenericDialogAct.Thank);
        Da2tag.put(DialogueAct.AskMood,         GenericDialogAct.AskMood);
        Da2tag.put(DialogueAct.AskTask,         GenericDialogAct.AskTask);
        Da2tag.put(DialogueAct.AskTaskFollowUp, GenericDialogAct.AskTask);
        Da2tag.put(DialogueAct.CalmDown,        GenericDialogAct.CalmDown);
        Da2tag.put(DialogueAct.Canned,          GenericDialogAct.Declare);
        Da2tag.put(DialogueAct.CheerUp,         GenericDialogAct.CheerUp);
        Da2tag.put(DialogueAct.Clarification,   GenericDialogAct.Apologise);
        Da2tag.put(DialogueAct.Console,         GenericDialogAct.Apologise);
        Da2tag.put(DialogueAct.Declare,         GenericDialogAct.Declare);
        Da2tag.put(DialogueAct.EveningGreet,    GenericDialogAct.Greeting);
        Da2tag.put(DialogueAct.IRResponse,      GenericDialogAct.Declare);
        Da2tag.put(DialogueAct.Introduce,       GenericDialogAct.Declare);
        Da2tag.put(DialogueAct.MeetAgainSayGoodbye, GenericDialogAct.GoodBye);
        Da2tag.put(DialogueAct.MorningGreet,    GenericDialogAct.Greeting);
        Da2tag.put(DialogueAct.NotFound,        GenericDialogAct.Apologise);
        Da2tag.put(DialogueAct.PersonalApologise, GenericDialogAct.Apologise);
        Da2tag.put(DialogueAct.PersonalGreet,   GenericDialogAct.Greeting);
        Da2tag.put(DialogueAct.PersonalSayGoodbye, GenericDialogAct.GoodBye);
        Da2tag.put(DialogueAct.ProactiveList,   GenericDialogAct.Declare);
        Da2tag.put(DialogueAct.ReadNewspaper,   GenericDialogAct.Declare);
        Da2tag.put(DialogueAct.Reject,          GenericDialogAct.Apologise);
        Da2tag.put(DialogueAct.RequestFeedback, GenericDialogAct.Request);
        Da2tag.put(DialogueAct.RequestRepeat,   GenericDialogAct.Request);
        Da2tag.put(DialogueAct.RequestRephrase, GenericDialogAct.Request);
        Da2tag.put(DialogueAct.ShareJoy,        GenericDialogAct.CheerUp);
        Da2tag.put(DialogueAct.ShowWeather,     GenericDialogAct.Declare);
        Da2tag.put(DialogueAct.ShowWebpage,     GenericDialogAct.Declare);
        Da2tag.put(DialogueAct.SimpleApologise, GenericDialogAct.Apologise);
        Da2tag.put(DialogueAct.SimpleGoodbye,   GenericDialogAct.GoodBye);
        Da2tag.put(DialogueAct.SimpleGreet,     GenericDialogAct.Greeting);
        Da2tag.put(DialogueAct.SimpleMotivate,  GenericDialogAct.CheerUp);
        Da2tag.put(DialogueAct.StateMissingComprehension, GenericDialogAct.Apologise);
        Da2tag.put(DialogueAct.Thank,           GenericDialogAct.Thank);
        Da2tag.put(DialogueAct.UnknownRequest,  GenericDialogAct.Apologise);
        Da2tag.put(DialogueAct.UnknownStatement, GenericDialogAct.Apologise);
        
        
        //map tag to FacExpr
        tag2FacExpr = new HashMap<>();
        tag2FacExpr.put(GenericDialogAct.Acknowledge,   FacialExpression.relaxed);
        tag2FacExpr.put(GenericDialogAct.Apologise,     FacialExpression.apologetic);
        tag2FacExpr.put(GenericDialogAct.AskMood,       FacialExpression.content);
        tag2FacExpr.put(GenericDialogAct.AskTask,       FacialExpression.curious);
        tag2FacExpr.put(GenericDialogAct.CalmDown,      FacialExpression.caring);
        tag2FacExpr.put(GenericDialogAct.CheerUp,       FacialExpression.joyful);
        tag2FacExpr.put(GenericDialogAct.Clarify,       FacialExpression.apologetic);
        tag2FacExpr.put(GenericDialogAct.Declare,       FacialExpression.neutral);
        tag2FacExpr.put(GenericDialogAct.GoodBye,       FacialExpression.content);
        tag2FacExpr.put(GenericDialogAct.Greeting,      FacialExpression.joyful);
        tag2FacExpr.put(GenericDialogAct.Request,       FacialExpression.curious);
        tag2FacExpr.put(GenericDialogAct.Thank,         FacialExpression.grateful);
        
        //map tag to Valence Arousal
        FacExpr2VA = new HashMap<>();
        FacExpr2VA.put(FacialExpression.apologetic,     new ValenceArousal((float)-0.16,(float) -0.29));
        FacExpr2VA.put(FacialExpression.caring,         new ValenceArousal((float) 0.7,(float) -0.14));
        FacExpr2VA.put(FacialExpression.content,        new ValenceArousal((float) 0.54,(float) 0.59));
        FacExpr2VA.put(FacialExpression.curious,        new ValenceArousal((float) 0.4,(float) 0.6));
        FacExpr2VA.put(FacialExpression.enthusiastic,   new ValenceArousal((float) 0.75,(float) 0.48));
        FacExpr2VA.put(FacialExpression.grateful,       new ValenceArousal((float) 0.68,(float) 0.27));
        FacExpr2VA.put(FacialExpression.joyful,         new ValenceArousal((float) 0.67,(float) 0.32));
        FacExpr2VA.put(FacialExpression.neutral,        new ValenceArousal(0,0));
        FacExpr2VA.put(FacialExpression.relaxed,        new ValenceArousal((float) 0.59,0));
        FacExpr2VA.put(FacialExpression.serious,        new ValenceArousal((float) 0.07,(float) 0.02));
        
        
        //definim cada conjunt de gestures (4 casos de moment):
        emotions = new HashMap<>();
        emotions.put("case1", new EmotionSetting(Proximity.close, Style.formal , Personality.extroverted ,Expressivity.high , Social.colloquial));
        emotions.put("case2", new EmotionSetting(Proximity.close, Style.informal, Personality.introverted, Expressivity.medium, Social.heartful));
        emotions.put("case3", new EmotionSetting(Proximity.distant, Style.formal, Personality.extroverted, Expressivity.medium, Social.heartful));
        emotions.put("case4", new EmotionSetting(Proximity.distant, Style.formal, Personality.introverted, Expressivity.low, Social.reserved));
    }
    
    public Map<Integer, String> generateModeRDF(String inputOwlStr, UserInfo profile, Mode mode) throws CustomException {
        
        SystemAction sa = new SystemAction(inputOwlStr);

        List<Resource> dialogActs = sa.getDialogActs();

        Map<Integer, String> outputDAs = new HashMap<>();
        for (int order = 0; order < dialogActs.size(); order++) {
            
            Resource dialogAct = dialogActs.get(order);
            String daClass = sa.getClass(dialogAct); // get the type of the DA instance

            // Map SystemAction DA to GenericDA
            DialogueAct da;
            try {
                da = DialogueAct.valueOf(daClass);
            } catch(IllegalArgumentException e) {
                da = DialogueAct.NotFound;
            }
            GenericDialogAct GenericDA = Da2tag.get(da); 
            System.out.println(order + " DA is " + sa.getClass(dialogAct));
            
            // Map GenericDA to FacialExpression
            FacialExpression FacExpr = tag2FacExpr.get(GenericDA);
            
            // Map FacialExpression to VA
            ValenceArousal VA = FacExpr2VA.get(FacExpr);
            
            // Merge SystemAction VA with default VA
            ValenceArousal defVA = mergeValenceArousal(sa, VA);
            float defValence = defVA.getValence();
            float defArousal = defVA.getArousal();
            
            // Given FacialExpression and mergedVA, get gestures
            EmotionSetting emotion = generateGestures(defVA, profile);
            
            // Generate model
            Model modelTmp =  generateModel(dialogAct, defArousal , defValence , order);
            addCharacteristics(modelTmp, dialogAct, emotion);
            addFacialExpr(modelTmp, dialogAct, FacExpr.name());
            
            ByteArrayOutputStream nonVerbalStream = new ByteArrayOutputStream();
            modelTmp.write(nonVerbalStream, "RDF/XML");
            String owlStr = nonVerbalStream.toString();
            
            outputDAs.put(order, owlStr);
        }
        
        return outputDAs;
    }
    
        private ValenceArousal mergeValenceArousal(SystemAction systemAction, ValenceArousal defaultVA) throws CustomException {
            
            float valence = (systemAction.getValence());
            float arousal = systemAction.getArousal();
            float defaultV = defaultVA.getValence();
            float defaultA = defaultVA.getArousal();
            
            float V = 0;
            float A = 0;
            
            if (defaultV < valence){
                A = defaultA;
                V = valence;

            } else {
                A = defaultA;
                V = defaultV;
            }
            
            return new ValenceArousal(V, A);
        }
        
        private EmotionSetting generateGestures(ValenceArousal va, UserInfo profile) {
            
            float V = va.getValence();
            float A = va.getArousal();
            
            //evaluate language and age
            Integer Age =  profile.getAge();
            String culture = profile.getLanguage();
            float delta = 0;
            if (culture.equals("es") || culture.equals("pl")){
                if (Age < 60){
                    delta = (float) 0.5;
                }else{
                    delta = (float) 0.4;
                }
            }
            
            if (culture.equals("de") || culture.equals("tr")){
                if (Age < 60){
                    delta = (float) 0.3;
                }else{
                    delta = (float) 0.2;
                }
            }
            V = V + delta;
            A = A + delta;
            
            EmotionSetting emotion;
            if (V>0){
                if (A>0){
                    emotion = emotions.get("case1");
                }else{
                    emotion = emotions.get("case2");
                }
            }else{
                if(A>0){
                    emotion = emotions.get("case3");
                }else{
                    emotion = emotions.get("case4");
                }
            }
            return emotion;
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

   
    private Model generateModel(Resource da, float arousal, float valence, int counter) {

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
        //Property hasOrder = modelTmp.createProperty(dialogueIRI + "#" + "hasOrder");
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

    void addCharacterisc(Model modelTmp, Resource dialogAct, String propertyStr, String literalStr) {
        Property hasProximity = modelTmp.getProperty(modeSelectionIRI + "#" + propertyStr);
        Literal proxLiteral = modelTmp.createLiteral(literalStr);
        modelTmp.addLiteral(dialogAct, hasProximity, proxLiteral);
    }
    
   public void addCharacteristics(Model modelTmp, Resource dialogAct, EmotionSetting emotion) {

        //PROXIMITY
        Proximity proximity = emotion.getProximity();
        addCharacterisc(modelTmp, dialogAct, "hasProximity", proximity.name());

        //PERSONALITY
        Personality personality = emotion.getPersonality();
        Property hasPersonality = modelTmp.getProperty(modeSelectionIRI + "#" + "hasPersonality");
        Literal persLiteral = modelTmp.createLiteral(personality.name());
        modelTmp.addLiteral(dialogAct, hasPersonality, persLiteral);

        //STYLE
        Style style = emotion.getStyle();
        Property hasStyle = modelTmp.getProperty(modeSelectionIRI + "#" + "hasStyle");
        Literal styleLiteral = modelTmp.createLiteral(style.name());
        modelTmp.addLiteral(dialogAct, hasStyle, styleLiteral);

        //EXPRESSIVITY
        Expressivity expressivity = emotion.getExpressivity();
        Property hasExpressivity = modelTmp.getProperty(modeSelectionIRI + "#" + "hasExpressivity");
        Literal expLiteral = modelTmp.createLiteral(expressivity.name());
        modelTmp.addLiteral(dialogAct, hasExpressivity, expLiteral);

        //SOCIAL
        Social social = emotion.getSocial();
        Property hasSocial = modelTmp.getProperty(modeSelectionIRI + "#" + "hasSocial");
        Literal socLiteral = modelTmp.createLiteral(social.name());
        modelTmp.addLiteral(dialogAct, hasSocial, socLiteral);
    }
    
    /*public void addGestures(Model modelTmp, Resource dialogAct, Object cases, HashMap EmotionSetting){
        Property social = modelTmp.getProperty(modeSelectionIRI + "#" + "hasSocial");
        EmotionSetting SocialLiteral = emotions.get(Social);   
    }*/

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
