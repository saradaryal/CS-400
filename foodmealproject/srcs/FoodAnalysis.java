package application;

import java.util.List;

import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * FoodAnalysis helper class. Methods to create a pie chart and 
 * create the food analysis view
 * @author Max Huddleston
 *
 */
public class FoodAnalysis {
	// The root layout for the food analysis
	BorderPane foodAnalysisLayout;
	// The total calorie label
	Label calorieCount;
	
	public FoodAnalysis(EventHandler<ActionEvent> exitEvent) {
		foodAnalysisLayout = new BorderPane();
		
		VBox topPane = new VBox();
		calorieCount = new Label();
		topPane.getChildren().addAll(new Label("Meal Analysis"), calorieCount);
		foodAnalysisLayout.setTop(topPane);
		
		Button exitButton = new Button("Exit");
		exitButton.setOnAction(exitEvent);
		exitButton.setPrefWidth(1400);
		foodAnalysisLayout.setBottom(exitButton);
	}
	/**
	 * Get the FoodAnalysis view consisting of the calorie count and the 
	 * pie chart showing the nutrient breakdown
	 * 
	 * @param foodList list of food to analyze
	 * @param exitEvent event to occur when the exit button is clicked 
	 * @return the FoodAnalysis view
	 */
	public BorderPane getFoodAnalysisView(List<FoodItem> foodList) {		
		double calorieSum = foodList.stream()
				.map(foodItem -> foodItem.getNutrientValue("calories"))
				.reduce(0.0, (a,b) -> a + b);
		calorieCount.setText("Total Calories: " + calorieSum);	
		
		foodAnalysisLayout.setCenter(getAnalysis(foodList));
		
		return foodAnalysisLayout;
	}
	
	/**
	 * Get the pie chart analysis associated with a given food list
	 * Displays the four nutrient total values
	 * @param foodList
	 * @return
	 */
	private VBox getAnalysis(List<FoodItem> foodList) {
		VBox root = new VBox();
		PieChart chart = new PieChart();
		
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		
		String[] nutrients = { "fat", "carbohydrate", "fiber", "protein" };
		double[] nutrientSums = new double[nutrients.length];
		
		for (FoodItem foodItem : foodList) {
			for (int i = 0; i < nutrients.length; i++) {
				nutrientSums[i] += foodItem.getNutrientValue(nutrients[i]);
			}
		}
			
		for (int i = 0; i < nutrients.length; i++) {
			pieChartData.add(new PieChart.Data(nutrients[i], nutrientSums[i]));
		}
		
		chart.setData(pieChartData);
		chart.setTitle("Meal Analysis");
		
		HBox nutrientFacts = new HBox();
		for (int i = 0; i < nutrients.length;  i++) {
			nutrientFacts.getChildren().add(
					new Label(nutrients[i] + ": " + nutrientSums[i] + "    ")
			);
		}
		
		root.getChildren().addAll(nutrientFacts, chart);
		
		return root;
	}
}