package com.hongqiao.view;

import java.io.IOException;
import java.util.List;

import com.hongqiao.client.ViewClient;
import com.hongqiao.dto.Text;
import com.hongqiao.util.Util;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UserView extends Application {

	private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Text> textData = FXCollections.observableArrayList();

    public ObservableList<Text> getTextData() {
        return textData;
    }
    public void setAllTextData() {
    	String textJson=ViewClient.sendGet("http://localhost:8080/KeyWordSearch/TextServlet","method=GetAllText");
    	List<Text> textList=Util.JsonToTextList(textJson);
    	if(textList==null||textList.size()==0)return;
    	for(int i=0;i<textList.size();i++) {
    		textData.add(textList.get(i));
    	}
    }
    
    public void setTextData( List<Text> textList) {
    	if(textList==null||textList.size()==0) {
    		return;
    	}
    	for(int i=0;i<textList.size();i++) {
    		textData.add(textList.get(i));
    	}
    }
    public void clearTextData() {
    	textData.clear();
    }
    public UserView() {
    	
    }
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initRootLayout();

        showOverview();
    }
    
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(UserView.class.getResource("/com/hongqiao/view/RootLayOut.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
    public void showOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(UserView.class.getResource("/com/hongqiao/view/SearchUI.fxml"));
            AnchorPane Overview = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(Overview);

            // Give the controller access to the main app.
            textClientController controller = loader.getController();
            controller.setMainApp(this);
            rootLayout.setCenter(Overview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean showEditDialog(Text text) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(UserView.class.getResource("/com/hongqiao/view/EditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
           
            
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit text");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            TextDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setText(text);
            // Give the controller access to the main app.
            controller.setMainApp(this);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
   
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
