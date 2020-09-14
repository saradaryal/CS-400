package application;
	


import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;




public class Main extends Application {
	

	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			
			primaryStage.setTitle("GUI");
			BorderPane root = new BorderPane();
			
			int sceneWidth = 600;
			
			//status bar for add, save, and load buttons
			BorderPane statusBarHolder = new BorderPane();
			HBox statusBar = new HBox();	
			
			//add button
			Button addButton = new Button();
			addButton.setText("Add");
			
			EventHandler<ActionEvent> cancelEvent = e -> {
			};
			EventHandler<ActionEvent> addEvent = e -> {
			};
			AddFoodView view = new AddFoodView(cancelEvent, addEvent);
			addButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {

					BorderPane addRoot = view.getAddFoodView();
					Scene addScene = new Scene(addRoot, 500, 500);
					primaryStage.setScene(addScene);
				}

			});
			
			
			
			Button saveButton = new Button();
			saveButton.setText("Save");
			Button loadButton = new Button();
			loadButton.setText("Load");
			Label title = new Label("Food Analysis and Meals");		
			statusBar.getChildren().addAll(addButton, saveButton, loadButton);			
			statusBarHolder.setRight(statusBar);
			statusBarHolder.setCenter(title);
			
			
			//temp food list
			ObservableList foods = FXCollections.observableArrayList(
			          "Avacados", "Brats", "Pizza");
			ListView foodList = new ListView(foods);
			foodList.setPrefWidth(280);
			foodList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
				@Override
				public ListCell<String> call(ListView<String> param) {
					return new AddFoodButton();
				}
			});
			ScrollPane pane = new ScrollPane();
		    pane.setContent(foodList);
		    pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		    Group group = new Group();
		    group.getChildren().add(pane);
		    
		    
		    //temp meal list
		    ObservableList meals = FXCollections.observableArrayList(
			          "Meal 1", "Meal 2", "Meal 3");
			ListView mealsList = new ListView(meals);
			mealsList.setPrefWidth(280);
			mealsList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
				@Override
				public ListCell<String> call(ListView<String> param) {
					return new AddMealButton();
				}
			});
			ScrollPane pane2 = new ScrollPane();
		    pane2.setContent(mealsList);
		    pane2.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		    Group group2 = new Group();
		    group2.getChildren().add(pane2);
		    
		    
		    //holds food list and meal list
		    HBox listHolder = new HBox();
		    listHolder.getChildren().addAll(group, group2);

		    
			EventHandler<ActionEvent> cancelEvent2 = e -> {
			};
			EventHandler<ActionEvent> applyQuery = e -> {
			};
			FilterRulesForm form = new FilterRulesForm(cancelEvent2, applyQuery);
			Button queryButton = new Button();
			queryButton.setText("Query");
			queryButton.setPrefWidth(sceneWidth);
			queryButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {

					BorderPane queryRoot = form.getFilterRuleView();
					Scene scene = new Scene(queryRoot, 500, 500);
					primaryStage.setScene(scene);
				}

			});
			
			
			
		
	
			
			root.setTop(statusBarHolder);
			root.setCenter(listHolder);
			root.setBottom(queryButton);
			

			
			
			Scene scene = new Scene(root,sceneWidth,460);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
			primaryStage.setScene(scene);
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
