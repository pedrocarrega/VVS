package sut;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class InstructionCoverage {
	
	@Test
	public void testContainsWithNull() {
		TST<Integer> st = new TST<>();
		assertThrows(NullPointerException.class, () -> {
			st.contains(null);
		});
	}
	
	@Test
	public void testContainsValidKey() throws FileNotFoundException {
		
		TST<Integer> st = populateTST();
		
		boolean result = st.contains("she");
		
		assertEquals(true, result);
	}
	
	@Test
	public void testLongestPrefixOfIllegalArgument() {
		TST<Integer> st = new TST<>();
		assertThrows(IllegalArgumentException.class, () -> {
			st.longestPrefixOf(null);
		});
	}
	
	@Test
	public void testLongestPrefixOfNull() {
		TST<Integer> st = new TST<>();
		
		String result = st.longestPrefixOf("");
		
		assertEquals(null, result);
	}
	
	@Test
	public void testLongestPrefixOfPart1() {
		TST<Integer> st = new TST<>();
		
		String result = st.longestPrefixOf("c");
		
		assertEquals("", result);
	}
	
	@Test
	public void testLongestPrefixOfPart2() {
		TST<Integer> st = new TST<>();
		
		String result = st.longestPrefixOf("shell");
		
		assertEquals("she", result);
	}
	

	
	private TST<Integer> populateTST() throws FileNotFoundException {
		
		Scanner sc = new Scanner(new File("data/someWords.txt"));
		TST<Integer> st = new TST<>();
		
		int i=0;
		while(sc.hasNextLine()) {
			String[] keys = sc.nextLine().split(" ");
			for(String key : keys)
				st.put(key, ++i);
		}
		
		return st;
	}

}
