/**
 * Sample Skeleton for 'Ufo.fxml' Controller Class
 */

package it.polito.tdp.ufo;

import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;

import it.polito.tdp.ufo.model.AnnoCount;
import it.polito.tdp.ufo.model.Model;
import it.polito.tdp.ufo.model.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class UfoController {
	
	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<AnnoCount> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxStato"
    private ComboBox<State> boxStato; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void handleAnalizza(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	AnnoCount anno = this.boxAnno.getValue();
    	if(anno == null) {
    		this.txtResult.setText("Errore: selezionare un anno!\n");
    		return;
    	}
    	
    	State stato = this.boxStato.getValue();
    	if(stato == null) {
    		this.txtResult.setText("Errore: selezionare uno Stato!");
    		return;
    	}
    	
    	txtResult.appendText("Stati che precedono " + stato + ":\n");
    	
    	List<State> predecessori = model.mostraPredecessori(stato);
    	for(State s : predecessori) {
    		txtResult.appendText(s.toString()+"; ");
    	}
    	
    	txtResult.appendText("\n\nStati che succedono " + stato + ":\n");
    	
    	List<State> successori = model.mostraSuccessori(stato);
    	for(State s : successori) {
    		txtResult.appendText(s.toString()+"; ");
    	}
    	
    	List<State> raggiungibili = model.displayNeighboursBreadthFirstIterator(stato);
    	
    	txtResult.appendText("\n\nStati raggiungibili da " + stato +
    			": (" +raggiungibili.size()+ ")\n");
    	    	
    	for(State r : raggiungibili) {
    		txtResult.appendText(r.toString()+"; ");
    	}
    }

    @FXML
    void handleAvvistamenti(ActionEvent event) {
    	
    	AnnoCount anno = this.boxAnno.getValue();
    	
    	if(anno == null) {
    		this.txtResult.setText("Errore: selezionare un anno!\n");
    		return;
    	}
    	
    	model.creaGrafo(anno.getAnno());
    	
		this.boxStato.getItems().clear();
    	this.boxStato.getItems().addAll(model.getStati());
    	
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	
    	State stato = this.boxStato.getValue();
    	if(stato == null) {
    		this.txtResult.setText("Errore: selezionare uno Stato!");
    		return;
    	}
    	
    	List<State> sequenza = model.getPercorsoMax(stato);
    	txtResult.appendText("\nStato di partenza: " + stato + "\n");
    	txtResult.appendText("\nPercorso max: " + sequenza + "\n");
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Ufo.fxml'.";
        
        txtResult.setStyle("-fx-font-family: monospace");
    }

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
		this.boxAnno.getItems().addAll(model.getAvvistamenti());
		this.boxStato.getItems().clear();
	}
        
}
