import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class PerformanceAnalysisHash implements PerformanceAnalysis {

    // The input data from each file is stored in this/ per file
    private ArrayList<String> inputData;
    private String fileName;
    private TreeMap testTree;
    private HashTable testTable;
    private String test;
    private String insertReport;
    private String deleteReport;
    private String searchReport;
    private String printReport;
    
    public PerformanceAnalysisHash()
    {
    		test = "hello";
    		testTree = new TreeMap<>();
    		testTable = new HashTable<>();
    }

    public PerformanceAnalysisHash(String details_filename){
    		this();
    		fileName = details_filename;
    		try 
    		{
    			loadData(details_filename);
    		} 
    		catch (IOException e)
    		{
    			e.printStackTrace();
    			System.out.println("Invalid File");
    		}
    }
    @Override
    public void compareDataStructures() {
        //TODO: Complete this function which compares the ds and generates the details
    		compareInsertion();
    		compareDeletion();
    		compareSearch();
    		printReport = insertReport + "\n" + deleteReport + "\n" + searchReport + "\n";
    }

    @Override
    public void printReport() {
        //TODO: Complete this method
    		System.out.println("Performance Analysis Report");
    		System.out.println("------------------------------------------------------------------------");
    		System.out.println(String.format("|%22s|%15s|%15s|%25s|%15s", "FileName", "Operation",
    				 "Data Structure", "Time Taken (Micro Sec)", "Bytes Used"));
    		System.out.println("------------------------------------------------------------------------");
    		System.out.println(printReport);
    		System.out.println("------------------------------------------------------------------------");
    }

    @Override
    public void compareInsertion() {
        //TODO: Complete this method
    		long treeMapStart = System.nanoTime();
    		long treeMapStartMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    		for (int i = 0; i < inputData.size(); i++)
    		{
    			String input = inputData.get(i);
    			testTree.put(input, test);
    		}
    		long treeMapEndMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    		long treeMapUsedMemory = treeMapStartMemory - treeMapEndMemory;
    		long treeMapTime = System.nanoTime() - treeMapStart;
    		//
    		long hashStart = System.nanoTime();
    		long hashStartMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    		for (int i = 0; i < inputData.size(); i++)
    		{
    			String input = inputData.get(i);
    			testTable.put(input, test);
    		}
    		long hashEndMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    		long hashUsedMemory = hashStartMemory - hashEndMemory;
    		long hashTime = System.nanoTime() - hashStart;
    		
    		insertReport = String.format("|%22s|%15s|%15s|%25s|%15s", fileName, "PUT", "HASHTABLE", hashTime, 
    				hashUsedMemory) + "\n" + String.format("|%22s|%15s|%15s|%25s|%15s", fileName, "PUT", 
    				"TREE MAP", treeMapTime, treeMapUsedMemory);
    }

    @Override
    public void compareDeletion() {
        //TODO: Complete this method
    		long treeMapStart = System.nanoTime();
		long treeMapStartMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		for (int i = 0; i < inputData.size(); i++)
		{
			String input = inputData.get(i);
			testTree.remove(input);
		}
		long treeMapEndMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long treeMapUsedMemory = treeMapStartMemory - treeMapEndMemory;
		long treeMapTime = System.nanoTime() - treeMapStart;
		//
		long hashStart = System.nanoTime();
		long hashStartMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		for (int i = 0; i < inputData.size(); i++)
		{
			String input = inputData.get(i);
			testTable.remove(input);
		}
		long hashEndMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long hashUsedMemory = hashStartMemory - hashEndMemory;
		long hashTime = System.nanoTime() - hashStart;
		
		deleteReport = String.format("|%22s|%15s|%15s|%25s|%15s", fileName, "DELETE", "HASHTABLE", hashTime, 
				hashUsedMemory) + "\n" + String.format("|%22s|%15s|%15s|%25s|%15s", fileName, "DELETE", 
				"TREE MAP", treeMapTime, treeMapUsedMemory);
    }

    @Override
    public void compareSearch() {
        //TODO: Complete this method
    	long treeMapStart = System.nanoTime();
		long treeMapStartMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		for (int i = 0; i < inputData.size(); i++)
		{
			String input = inputData.get(i);
			testTree.get(input);
		}
		long treeMapEndMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long treeMapUsedMemory = treeMapStartMemory - treeMapEndMemory;
		long treeMapTime = System.nanoTime() - treeMapStart;
		//
		long hashStart = System.nanoTime();
		long hashStartMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		for (int i = 0; i < inputData.size(); i++)
		{
			String input = inputData.get(i);
			testTable.get(input);
		}
		long hashEndMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long hashUsedMemory = hashStartMemory - hashEndMemory;
		long hashTime = System.nanoTime() - hashStart;
		
		searchReport = String.format("|%22s|%15s|%15s|%25s|%15s", fileName, "GET", "HASHTABLE", hashTime, 
				hashUsedMemory) + "\n" + String.format("|%22s|%15s|%15s|%25s|%15s", fileName, "GET", 
				"TREE MAP", treeMapTime, treeMapUsedMemory);
    }

    /*
    An implementation of loading files into local data structure is provided to you
    Please feel free to make any changes if required as per your implementation.
    However, this function can be used as is.
     */
    @Override
    public void loadData(String filename) throws IOException {

        // Opens the given test file and stores the objects each line as a string
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        inputData = new ArrayList<>();
        String line = br.readLine();
        while (line != null) {
            inputData.add(line);
            line = br.readLine();
        }
        br.close();
    }
    public static void main(String[] args)
    {
    		PerformanceAnalysisHash test = new PerformanceAnalysisHash();
    		test.compareDataStructures();
    		test.printReport();
    }
}
