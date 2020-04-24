package sut;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import org.junit.Test;

import sut.TST;

public class InstructionCoverageTests {
	
	@Test
	public void testSize() {
		TST<Integer> st = new TST<>();
		
		int result = st.size();
		
		assertEquals(0, result);
	}
	
	@Test
	public void testContainsWithNull() {
		TST<Integer> st = new TST<>();
		assertThrows(IllegalArgumentException.class, () -> {
			st.contains(null);
		});
	}
	
	@Test
	public void testContainsValidKey() throws FileNotFoundException {
		
		TST<Integer> st = populateTST();
		
		boolean result = st.contains("she");
		
		assertTrue(result);
	}
	
	@Test
	public void testGetWithNullKey() {
		TST<Integer> st = new TST<>();
		assertThrows(IllegalArgumentException.class, () -> {
			st.get(null);
		});
	}
	
	@Test
	public void testGetWithEmptyKey() {
		TST<Integer> st = new TST<>();
		st.put("string", 0);
		assertThrows(IllegalArgumentException.class, () -> {
			st.get("");
		});
	}
	
	
	@Test
	public void testGetWithEmptyTST() {
		TST<Integer> st = new TST<>();
		
		Integer result = st.get("string");
		assertEquals(null, result);
	}
	
	@Test
	public void testGetWithValidKey() {
		TST<Integer> st = new TST<>();
		st.put("string", 0);
		
		int result = st.get("string");
		int expected = 0;
		assertEquals(expected, result);
	}
	
	@Test
	public void testPutWithNullKey() {
		TST<Integer> st = new TST<>();
		
		assertThrows(IllegalArgumentException.class, () -> {
			st.put(null, null);
		});
	}
	
	@Test
	public void testPutWithExistingKey() {
		TST<Integer> st = new TST<>();
		
		st.put("string", 0);
		boolean verifier = st.contains("string") ;
		assertTrue(verifier);
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
	public void testLongestPrefixOfPart1() throws FileNotFoundException {
		TST<Integer> st = populateTST();
		
		String result = st.longestPrefixOf("c");
		
		assertEquals("", result);
	}
	
	@Test
	public void testLongestPrefixOfPart2() throws FileNotFoundException {
		TST<Integer> st = populateTST();
		
		String result = st.longestPrefixOf("shell");
		
		assertEquals("she", result);
	}
	
	@Test
	public void testKeys() {
		TST<Integer> st = new TST<>();
		
		Iterable<String> result = st.keys();
		Queue<String> expected = new LinkedList<>();
		assertEquals(expected, result);
	}	
	
	@Test
	public void testKeysWithPrefixWithNullString() {
		TST<Integer> st = new TST<>();
		
		assertThrows(IllegalArgumentException.class, () -> {
			st.keysWithPrefix(null);
		});
	}	
	
	@Test
	public void testKeysWithPrefixWithEmptyTST() throws FileNotFoundException {
		TST<Integer> st = populateTST();
		
		Iterable<String> result = st.keysWithPrefix("string");
		Queue<String> expected = new LinkedList<>();
		assertEquals(expected, result);
	}	
	
	@Test
	public void testKeysWithPrefixWithExistingKeyPrefix() throws FileNotFoundException {
		TST<Integer> st = new TST<>();
		st.put("string", 0);
		
		Iterable<String> result = st.keysWithPrefix("string");
		Queue<String> expected = new LinkedList<>();
		expected.add("string");
		assertEquals(expected, result);
	}	
	
	@Test
	public void testKeysThatMatchWithNullPattern() {
		TST<Integer> st = new TST<>();
		
		Iterable<String> result = st.keysThatMatch(null);
		Queue<String> expected = new LinkedList<>();
		assertEquals(expected, result);
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
		sc.close();
		return st;
	}

}
