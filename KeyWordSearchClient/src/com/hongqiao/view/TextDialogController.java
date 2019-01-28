package com.hongqiao.view;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.hongqiao.client.ViewClient;
import com.hongqiao.dto.Text;
import com.hongqiao.util.Util;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Dialog to edit details of a text.
 * 
 * @author Hongqiao
 */
public class TextDialogController extends Util{

    @FXML
    private TextField idField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField summaryField;

    private Stage dialogStage;
    private UserView mainApp;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the text to be edited in the dialog.
     * 
     * @param text
     */
    public void setText(Text text) {
        idField.setText(text.getId());
        titleField.setText(text.getTitle());
        summaryField.setText(text.getSummary());
        
    }

    /**
     * Returns true if the user clicked Save, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks save.
     */
    @FXML
    private void handleSave() {
        if (isInputValid()) {
        	Text text=new Text(idField.getText(),titleField.getText(),summaryField.getText());
        	List<Text> textList=new ArrayList<Text>();
        	textList.add(text);
        	String param="method="+method.UpdateText+"&updateText="+TextListToJason(textList);
            String textJson= ViewClient.sendPost("http://localhost:8080/KeyWordSearch/TextServlet? ", param);
            Object json = new JSONTokener(textJson).nextValue();

			if (json instanceof JSONObject) {
				JSONObject jsonObject = (JSONObject) json;
				if(!jsonObject.isNull("error")){
	            	// Show error message.
	            	Alert alert = new Alert(AlertType.INFORMATION);
	            	alert.setTitle("Error Information");
	            	alert.setHeaderText(null);
	            	String message=jsonObject.toString();
	            	alert.setContentText(message.replaceAll("[\\[\\]]", ""));
	            	alert.showAndWait();
	                return;
	            }
			}
            
          
        	textList=Util.JsonToTextList(textJson);
        	if(textList==null||textList.size()==0)return;
        	mainApp.clearTextData();
        	mainApp.setTextData(textList);       	
            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleDelete() {
    	String param="method="+method.DeleteText+"&id="+idField.getText();
        String textJson= ViewClient.sendPost("http://localhost:8080/KeyWordSearch/TextServlet? ", param);
        Object json = new JSONTokener(textJson).nextValue();

		if (json instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject) json;
			if(!jsonObject.isNull("error")){
            	// Show error message.
            	Alert alert = new Alert(AlertType.INFORMATION);
            	alert.setTitle("Error Information");
            	alert.setHeaderText(null);
            	String message=jsonObject.toString();
            	alert.setContentText(message.replaceAll("[\\[\\]]", ""));
            	alert.showAndWait();
                return;
            }
		}
        
		List<Text> textList=Util.JsonToTextList(textJson);
    	if(textList==null||textList.size()==0)return;
    	mainApp.clearTextData();
    	mainApp.setTextData(textList);       	
        okClicked = true;
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (titleField.getText() == null || titleField.getText().length() == 0) {
            errorMessage += "No valid title!\n"; 
        }
        if (idField.getText() == null || idField.getText().length() == 0) {
            errorMessage += "No valid id!\n"; 
        }
        if (summaryField.getText() == null || summaryField.getText().length() == 0) {
            errorMessage += "No valid summary description!\n"; 
        }

      

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show error message.
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Error Information");
        	alert.setHeaderText(null);
        	alert.setContentText(errorMessage);
        	alert.showAndWait();
            return false;
        }
    }
    
    public void setMainApp(UserView mainApp) {
        this.mainApp = mainApp;
    }
}