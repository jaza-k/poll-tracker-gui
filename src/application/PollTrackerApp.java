package application;
	
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import model.PollList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Poll Tracker App which allows user to input Poll data and visualize it
 * @author Richi, Jaza, Josh
 *
 */
public class PollTrackerApp extends Application {
	/**
	 * instance variables for the PollTrackerApp Class
	 */
	//Stage instance variable for the class
	Stage primaryStage;
	//polls instance variable will be set to the updated factory singleton instance
	static  PollList polls;
	
	/**
	 * setupView Method loads the SetupView controller class and sets the <code>primaryStage</code>
	 * instance variable. Links this instance of PollTrackerApp with the SetupController
	 * Precondition: none
	 * Postcondition: SetupView will be loaded, primaryStage will be set, and an instance of PollTrackerApp
	 * will be linked to the SetupController
	 */
	private void setupView() {
		FXMLLoader loader = new FXMLLoader();
		Scene scene;
		try {
			//load the SetupView fxml file and controller for the file
			Parent setupView = (Parent) loader.load(new FileInputStream("src/view/SetupView.fxml"));
			SetupController setupController = loader.getController();
			//set the PollTrackerApp instance variable to this instance of PollTrackerApp
			setupController.linkWithApplication(this);
			//set the primaryStage instance variable
			scene = new Scene(setupView,400,300);
			primaryStage.setScene(scene);
			primaryStage.show();		
		} 
		//handle errors
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * pollView method which loads the VisualizePollView and EditPollView files and their controller classes
	 * sets the polls instance variables in both controller classes to the <code>polls</code> instance
	 * variable in this class, and creates two tabs which show the content created by the fxml files
	 * Precondition: none
	 * Postcondition: creates two tabs which show the data displayed by the fxml files
	 */
	void pollView() {
		FXMLLoader loader = new FXMLLoader();
		Scene scene;

		try {
			//load the EditPollView fxml file and its controller class, and create a tab where it is displayed
			loader = new FXMLLoader();
			Tab editView = new Tab("Edit", loader.load(new FileInputStream("src/view/EditPollView.fxml")));
			EditPollController editPollController = loader.getController();
			editPollController.refresh();
			//load the VisualizePollView fxml file and its controller class, and create a tab where it is displayed
			loader = new FXMLLoader();
			Tab visualizeView = new Tab("Visualize", loader.load(new FileInputStream("src/view/VisualizePollView.fxml")));
			VisualizePollController visualizePollController = loader.getController();
			//set the polls instance variable in the VisualizePollController class to the polls instance variable in this class
			visualizePollController.setPolls(polls);
			//start displaying the data from the VisualizePollView file
			visualizePollController.start();
			//link the controllers together
			editPollController.connector(this, visualizePollController);
			//display the tabs
			TabPane root = new TabPane(editView, visualizeView);
			//set the scene
			scene = new Scene(root, 800,500);
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
		//handle errors
		catch (FileNotFoundException e) {
			System.out.println("File error");
			e.printStackTrace();
		} 
		catch (IOException e) {
			System.out.println("IO error");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * start method
	 * sets the <code>primaryStage</code> instance variable to the parameter
	 * calls setupView method to start the application set it up
	 * Precondition: primaryStage must be of type Stage
	 * Postcondition: <code>primaryStage</code> instance variable will be set, and setupView will be called
	 * 
	 * @param primaryStage the Stage that the instance variable should be set to
	 */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		setupView();
	}
	
	/**
	 * main method for the PollTrackerApp class
	 * launches the application
	 * @param args String array
	 */
	public static void main(String[] args) {
		launch(args);
	}
}