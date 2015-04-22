/**
 * 
 */
package test.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

/**
 * @author azhuk
 * Creation date: Oct 4, 2013
 *
 */
public class TestCollections {
	
	public interface A { 
	    String text = "a"; 
	} 
	 
	public interface B { 
	    String text = "b"; 
	} 
	
	
	HashMap<Integer,Integer> map = new  HashMap<Integer,Integer> () {
		
	private static final long serialVersionUID = 1L;

	{
		put(1,1);
		put(2,2);
		put(3,3);
	 }};
	 
	 
	 public static final  void main() {
		 long x = 5;
		 long y = 2;
		 byte b = (byte) (x/y);
	 }
	 
	@Test
	public void testCloneMap() {	
		
		
	 
	 HashMap<Integer,Integer> cloneMap  = (HashMap<Integer, Integer>) map.clone();
	 
	 assertEquals(map, cloneMap);
	 
	 cloneMap.put(1, 2);
	 
	 assertNotEquals (map, cloneMap);
	 //assertEquals(Integer.valueOf(2),  cloneMap.get(1));	 	 
	}
	
	@Test
	public void testSizeMap() {		
	 
	 HashMap<Integer,Integer> cloneMap  = (HashMap<Integer, Integer>) map.clone();	 
	 assertEquals(map, cloneMap);
	 
	 assertEquals(map.size(), cloneMap.size());
	 
	 
	 cloneMap.put(1, 2);
	 
	 assertNotEquals (map, cloneMap);
	 //assertEquals(Integer.valueOf(2),  cloneMap.get(1));	 	 
	}
	
	public void testSorting() {
		
		ArrayList<Integer> aList = new  ArrayList<Integer>();
		aList.add(3);
		aList.add(1);
		aList.add(2);		
	
	}
	
	@Test
	public void testArraSize() {
		int[] mass = {1, 2}; 
		int arraySize = 10;
		List<String> list = new ArrayList(arraySize); 
		assertEquals(arraySize, list.size() );
		list.add("03"); 
		list.add("04"); 
		assertNotEquals(arraySize, list.size());
		assertEquals(2, list.size());
	}
	
	
	@Test
	public void testOperators() {
		
		int i = 5; 
		i = i++;		
		System.out.println(i);		
		
		i = 5; 
		i = i++ + i++; 
		System.out.println(i); 
		
		i = 5; 
		i = i++ + ++i; 
		System.out.println(i);
		
		i = 5;		
		i = ++i + ++i; 
		System.out.println(i);
		
	}
	
	
}
