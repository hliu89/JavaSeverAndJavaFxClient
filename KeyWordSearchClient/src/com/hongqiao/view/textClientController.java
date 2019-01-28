package com.hongqiao.view;

import java.util.List;

import com.hongqiao.client.ViewClient;
import com.hongqiao.dto.Text;
import com.hongqiao.util.Util;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class textClientController extends Util {
	@FXML
    private TableView<Text> TextnTable;
    @FXML
    private TableColumn<Text, String> titleColumn;
    @FXML
    private TableColumn<Text, String> summaryColumn;
    @FXML
    private TextField searchField;
    
    // Reference to the main application.
    private UserView mainApp;
    
 
    //The constructor. 
    public textClientController() {
    }
    
    private void showTextDetails(Text text) {
        if (text != null) {
            // Fill the labels with info from the text object.
        	titleColumn.setText(text.getTitle());
        	summaryColumn.setText(text.getSummary());

        } else {
            // Text is null, remove all the text.
        	titleColumn.setText("");
        	summaryColumn.setText("");
        }
    }
   
    
    
    @FXML
    private void initialize() {
    	TextnTable.setRowFactory( tv -> {
    	    TableRow<Text> row = new TableRow<>();
    	    row.setOnMouseClicked(event -> {
    	        if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
    	            row.getItem();
    	        }
    	    });
    	    return row ;
    	});
    	titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        summaryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSummary()));
        
        
    }
    @FXML
    private void handleEditText() {
        Text text = TextnTable.getSelectionModel().getSelectedItem();
        if (text != null) {
            boolean okClicked = mainApp.showEditDialog(text);
            if (okClicked) {
            	showTextDetails(text);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("No Selection");
        	alert.setHeaderText(null);
        	alert.setContentText("Please select a text in the table.");
        	alert.showAndWait();
        }
    }
    
    @FXML
    private void KeyWordSearch() {
        String keyWord=searchField.getText();
        String param="method="+method.SearchByKeyWord+"&KeyWord="+keyWord;
        String textJson= ViewClient.sendPost("http://localhost:8080/KeyWordSearch/TextServlet? ", param);
        
        List<Text> textList=Util.JsonToTextList(textJson);
        System.out.println(textJson+"size: "+textList.size());
    	mainApp.clearTextData();
    	mainApp.setTextData(textList);
    	TextnTable.setItems(mainApp.getTextData());
    }
    
    public void setMainApp(UserView mainApp) {
        this.mainApp = mainApp;
        mainApp.setAllTextData();
        // Add observable list data to the table
        TextnTable.setItems(mainApp.getTextData());
    }
    
}
