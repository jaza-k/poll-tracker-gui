package application;
	
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;







import model.Factory;
import model.PollList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import application.*;


public class PollTrackerApp extends Application {
	Stage primaryStage;
	static PollList polls;
	
	
	private void setupView() {
		FXMLLoader loader = new FXMLLoader();
		Scene scene;
		try {
			System.out.println("Polls at the beginning of the entire app: " + polls);
			System.out.println("Currently on the first view");
			Parent setupView = (Parent) loader.load(new FileInputStream("src/view/SetupView.fxml"));
			SetupController setupController = loader.getController();
			setupController.linkWithApplication(this);
			scene = new Scene(setupView,400,300);
			primaryStage.setScene(scene);
			primaryStage.show();		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void pollView() {
		FXMLLoader loader = new FXMLLoader();
		Scene scene;
//		polls = Factory.getInstance().createEmptyPolls();

		try {
			System.out.println("About to load the EditPollController class\n");
			loader = new FXMLLoader();
			Tab editView = new Tab("Edit", loader.load(new FileInputStream("src/view/EditPollView.fxml")));
			EditPollController editPollController = loader.getController();
			//editPollController.setPolls(polls);
			editPollController.refresh();
			loader = new FXMLLoader();
			System.out.println("hit here");
			Tab visualizeView = new Tab("Visualize", loader.load(new FileInputStream("src/view/VisualizePollView.fxml")));
			VisualizePollController visualizePollController = loader.getController();
			
			visualizePollController.setPolls(polls);
			
			System.out.println("before start");
			
			visualizePollController.start();
			
			System.out.println("REACHED");
			TabPane root = new TabPane(editView, visualizeView);
			System.out.println("REACHED1");
			scene = new Scene(root, 800,500);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (FileNotFoundException e) {
			System.out.println("File error");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO error");
			e.printStackTrace();
		}
		
	}
	
	public static PollList getthepoll() {
		return polls;
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		setupView();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}