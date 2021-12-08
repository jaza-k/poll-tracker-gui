package application;

import java.io.FileInputStream;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.Factory;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class SetupTester extends javafx.application.Application{

	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			
			FXMLLoader loader = new FXMLLoader();
			root = (BorderPane)loader.load(new FileInputStream("src/view/SetupView.fxml"));
			
			Scene scene = new Scene(root,400,400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(Factory.getInstance().toString());
	}
	
	public static void main(String[] args) {
		launch(args);
		System.out.println(Factory.getInstance().toString());
	}

}
