package sut;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PrimePathCoverage {

	@Test
	public void testLongestPrefixOfWithNullQuery() {
		//[1, 2]
		TST<Integer> st = new TST<>();
		assertThrows(IllegalArgumentException.class, () -> {
			st.longestPrefixOf(null);
		});
	}

	@Test
	public void testLongestPrefixOfWithEmptyQuery() {
		//[1, 3, 4]
		TST<Integer> st = new TST<>();
		String result = st.longestPrefixOf("");
		assertNull(result);
	}

	@Test
	public void testLongestPrefixOfValid1() {
		//[1,3,5,6,7,8]
		//[7,8,6,7]
		//[7,9,6,13]
		//[6,7,9,6]
		//[6,7,8,6]
		//[8,6,7,9]
		TST<Integer> st = new TST<>();
		st.put("c", 0);
		st.put("a", 0);
		String result = st.longestPrefixOf("b");
		
		assertEquals("", result);
	}
	
	@Test
	public void testLongestPrefixOfValid2() {
		//[1,3,5,6,7,9]
		//[7,9,6,7]
		//[7,9,6,13]
		//[6,7,9,6]
		//[9,6,7,9]
		TST<Integer> st = new TST<>();
		st.put("d", 0);
		st.put("c", 0);
		String result = st.longestPrefixOf("b");
		
		assertEquals("", result);
	}

	@Test
	public void testLongestPrefixOfValid3() {
		//[1,3,5,6,7,9]
		//[7,9,6,7]
		//[7,9,6,13]
		//[6,7,9,6]
		//[9,6,7,9]
		TST<Integer> st = new TST<>();
		st.put("d", 0);
		st.put("a", 0);
		String result = st.longestPrefixOf("b");
		
		assertEquals("", result);
	}


}
