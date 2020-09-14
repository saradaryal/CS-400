
/////////////////////////////////////////////////////////////////////////////
// 
// Project:          HashTable with Performance Analysis
// Files:            HashTableADT.java
//					 HashTable.java
// 					 PerformanceAnalysis.java
//					 PerformanceAnalysisHash.java
//					 AnalysisTest.java
// Semester:         CS400 Spring 2018
// Author(s):		 Brennan Fife, Dustin Li
// Instructor:       Deb Deppeler 
// Bugs:             No known bugs
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class HashTable<K, V> implements HashTableADT<K, V> {
    /* Instance variables and constructors
     */
	protected class HashNode<K, V> {
		private K k;
		private V v;
		private HashNode<K, V> next;
		
		public HashNode(K k, V v) { //CONSTRUCTOR
			this.k = k;
			this.v = v;
		}
	}
	private HashNode<K, V>[] table;
	private int tableSize = 7;//capacity of HashTable 
	private double loadFactor = 0.8;	//maximum load factor of HashTable 
	private int num; //number of current items inside HashTable
	
	public HashTable()
	{
		this(7, 0.8);
	}
	public HashTable(int initialCapacity, double loadFactor) {
		this.loadFactor = loadFactor;
		this.tableSize = initialCapacity;
		table = new HashNode[tableSize];
		for (int i = 0; i < tableSize; i++) {
			table[i] = null;
		}	
	}

    private int getHashNum(K key)
    {
    		int hashCode = key.hashCode();
    		int hashNum = hashCode % tableSize;
    		return hashNum;
    }
    
    private void resize()
    {
    		double loadFactor = (num/tableSize);
    		if (loadFactor > this.loadFactor)
    		{
    			int newSize = tableSize * 2;
    			tableSize = newSize;
    			HashNode<K, V>[] newTable = new HashNode[newSize];
    			for (int i = 0; i < tableSize; i++)
    			{
    				newTable[i] = table[i];
    				table = newTable;
    			}
    		}
    }
    
    @Override
    public V put(K key, V value) { //check for null
    	//TODO: Implement put method - using efficient algorithm
    		if (key == null || get(key) != null)
    		{
    			throw new IllegalArgumentException("Invalid Key");
    		}
    		else
    		{
    			int hashValue = getHashNum(key);
			if (table[hashValue] == null)
			{
				table[hashValue] = new HashNode<>(key, value);
				num++;
				resize();
			}
			else
			{
				HashNode<K, V> temp = table[hashValue];
				temp.next = new HashNode<K, V>(key, value);
				table[hashValue] = temp;
				num++;
				resize();
    			}
    		}
		return value;
    }

    @Override
    public void clear() {
    		table = new HashNode[tableSize];
    		num = 0;
    }

    @Override
    public V get(K key) { //check for null key)
        //TODO: Implement the get method
        if (key == null)
        {
        		throw new IllegalArgumentException();
        }
    		for (int i = 0; i < tableSize; i++)
        {
        		if (table[i].k.equals(key))
        		{
        			return table[i].v;
        		}
        		else 
        		{
        			HashNode<K, V> temp = table[i].next;
        			while(temp != null)
        			{
        				if (temp.k.equals(key))
        				{
        					return temp.v;
        				}
        				else
        				{
        					temp = temp.next;
        				}
        			}
        		}
        }
        return null;
    }

    @Override
    public boolean isEmpty() 
    {
    		return num == 0; 
    }

    @Override
    public V remove(K key) {
       //TODO: Implement the remove method
        if (key == null || get(key) == null)
        {
        		throw new IllegalArgumentException();
        }
        int hashValue = getHashNum(key);
        if (table[hashValue].k.equals(key))
        {
        		V ret = table[hashValue].v;
        		table[hashValue] = table[hashValue].next;
        		return ret;
        }
        else
        {
        		
        		HashNode<K, V> prev = null;
        		HashNode<K, V> curr = table[hashValue];
        		prev.next = curr;
        		while (curr.next != null)
        		{
        			if (curr.k.equals(key))
        			{
        				prev.next = curr.next;
        				return curr.v;
        			}
        			else 
        			{
        				prev = curr;
        				curr = curr.next;
        			}
        		}
        }
		return null; 
    }

    @Override
    public int size() 
    {
        return tableSize;
    }
}