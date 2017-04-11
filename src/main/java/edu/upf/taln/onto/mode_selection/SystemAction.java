/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upf.taln.onto.mode_selection;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import static edu.upf.taln.onto.mode_selection.ModeSelection.dialogueIRI;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rcarlini
 */
public class SystemAction {

    static String baseIRI = "http://kristina-project.eu/ms";
    static String dialogueIRI = "http://kristina-project.eu/ontologies/dialogue_actions";
    static String modeSelectionIRI = "http://kristina-project.eu/ontologies/mode_selection";
    
    private static String removePrefix(String uri) {
        return uri.substring(uri.lastIndexOf('#') + 1);
    }
    
    private final Model model;
    private final List<Resource> responseList = new ArrayList<>();
    
    public SystemAction(String owlStr) throws CustomException {
        
        StringReader sR = new StringReader(owlStr);
        model = ModelFactory.createDefaultModel();
        model.read(sR, null, "RDF/XML");
        
        loadResponses();
    }
    
    public final void loadResponses() throws CustomException {

        Resource actionObj = getAction();

        // read the DAs contained in the SystemAction (in an order-aware manner) to a List 
        Property startWith = model.getProperty(dialogueIRI + "#" + "startWith");
        Property followedBy = model.getProperty(dialogueIRI + "#" + "followedBy");


        Resource resObj = actionObj.getPropertyResourceValue(startWith); // get the first DA				
        while (resObj != null) {
            responseList.add(resObj);
            resObj = resObj.getPropertyResourceValue(followedBy);
        }

        System.out.println("System action " + actionObj + " contains the following DAs: \n");
        int count = 0;
        for (Resource response : responseList) {
            System.out.println("	" + count + " " + response + " instance of " + getClass(response) + "\n");
            count++;
        }
        System.out.println(" -------------------------------------------- ");
    }
    
    public Resource getAction() {
        // declaration of properties and classes
        Resource systemActionClass = model.getResource(dialogueIRI + "#" + "SystemAction");

        Property rdfType = model.getProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");

        // get all resources (instances) that belong to the SystemAction class
        ResIterator rIter = model.listResourcesWithProperty(rdfType, systemActionClass);

        Resource actionObj = rIter.nextResource(); // there is only one such object in the DM output [strictly speaking we should go over the iterator]		
        
        return actionObj;
    }

    public String getClass(Resource resource) {
        // get the type of the DA instance
        Property rdfType = model.getProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        return removePrefix(resource.getPropertyResourceValue(rdfType).toString());

    }

    public float getArousal() throws CustomException {
        return getArousal(getAction());
    }
    
    public float getArousal(Resource resource) throws CustomException {
        // read the arousal values (these are SystemAction level and apply to all of the contained DAs)
        Property hasArousal = model.getProperty(dialogueIRI + "#" + "hasArousal");
        Statement arousalObj = resource.getProperty(hasArousal);
        if (arousalObj == null) {
            throw new CustomException("The arousal value is missing!");
        }
        return arousalObj.getFloat();
    }

    public float getValence() throws CustomException {
        return getValence(getAction());
    }
      
    public float getValence(Resource resource) throws CustomException {
        // read the valence  values (these are SystemAction level and apply to all of the contained DAs)
        Property hasValence = model.getProperty(dialogueIRI + "#" + "hasValence");
        Statement valenceObj = resource.getProperty(hasValence);
        if (valenceObj == null) {
            throw new CustomException("The valence value is missing!");
        }
        return valenceObj.getFloat();
    }

    public List<Resource> getDialogActs() {
        return responseList;
    }
    
}
