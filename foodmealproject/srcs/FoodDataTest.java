package application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class FoodDataTest {
	FoodData foodData;
	static final String FOOD_FILE = "testing-food.csv";
	static final String MEAL_FILE = "testing-meal.csv";
	
	@Test
	void test001_addFoodItem() {
		foodData = new FoodData();
		
		foodData.addFoodItem(new FoodItem("12345", "Apple"));
		assertTrue(foodData.getAllFoodItems().get(0).getID().equals("12345"));
	}
	
	@Test
	void test002_writeOneToCSV() {
		foodData = new FoodData();
		
		FoodItem foodItem = new FoodItem("12345", "Apple");
		foodItem.addNutrient("calories", 12);
		foodItem.addNutrient("fiber", 54);
		foodItem.addNutrient("protein", 1);
		foodItem.addNutrient("carbohydrate", 0);
		foodItem.addNutrient("fat", 12);
		
		foodData.addFoodItem(foodItem);
		foodData.saveFoodItems(FoodDataTest.FOOD_FILE);
		
		try (Stream<String> stream = Files.lines(Paths.get(FoodDataTest.FOOD_FILE))) {
			stream.forEach(line -> {
				if (!line.contains("12345"))
					fail("Did not write successfully to file.");
			});
		} catch (IOException e) {
			fail("An IO exception occurred while processing the testing file");
		}
	}
	
	@Test
	void test003_loadMany() {
		foodData = new FoodData();
		foodData.loadFoodItems("./src/application/foodItems.csv");
		List<FoodItem> foodItems = foodData.getAllFoodItems();
		
		if (foodItems.size() == 0)
			fail("Did not correctly parse the input file.");
	}
	
	@Test
	void test004_filterByName() {
		foodData = new FoodData();
		foodData.loadFoodItems("./src/application/foodItems.csv");
		List<FoodItem> filteredItems = foodData.filterByName("oo");
				
		for (int i = 0; i < filteredItems.size(); i++) {
			if (!filteredItems.get(i).getName().contains("oo"))
				fail("Did not filter correctly based on a substring.");
		}
	}

    @Test
    void test005_filterByNutrients1() {
        foodData = new FoodData();
        foodData.loadFoodItems("./src/application/foodItems.csv");
        ArrayList<String> foodItems = new ArrayList<String>();
        foodItems.add("protein >= 39");
        List<FoodItem> filteredItems = foodData.filterByNutrients(foodItems);
        System.out.println(filteredItems.size());
        if (filteredItems.size() > 1 || filteredItems.size() == 0) {
            fail("Did not filter correctly based on nutreints.");
        }
        if (!filteredItems.get(0).getName().contains("LeanBodyStraw")) {
            System.out.println("The list contains " + filteredItems.get(0).getName());
            fail("Did not filter correctly based on nutrients.");
        }
    }

    @Test
    void test006_filterByNutrients2() {
        foodData = new FoodData();
        foodData.loadFoodItems("./src/application/foodItems.csv");
        ArrayList<String> foodItems = new ArrayList<String>();
        foodItems.add("calories <= 200");
        foodItems.add("fat >= 8");
        foodItems.add("carbohydrate <= 18");
        foodItems.add("fiber >= 2");
        foodItems.add("protein >= 4");
        List<FoodItem> filteredItems = foodData.filterByNutrients(foodItems);
        System.out.println("2 + " + filteredItems.size());
        if (!(filteredItems.size() == 4)) {
            fail("Did not filter correctly based on nutrients.");
        }
        if (!filteredItems.get(0).getName().contains("TuxedoSnackMix")) {
            System.out.println("The list contains " + filteredItems.get(0).getName());
            fail("Did not filter correctly based on nutrients.");
        } 
    }

}
