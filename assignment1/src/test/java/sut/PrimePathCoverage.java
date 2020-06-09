package sut;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

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
	public void testLongestPrefixOfEmptyTrie() {
		//[1,3,5,6,13]
		TST<Integer> st = new TST<Integer>();
		
		String result = st.longestPrefixOf("s");
		assertEquals("", result);
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
		//[1,3,5,6,7,8]
		//[7,8,6,13]
		//[7,8,6,7]
		//[6,7,8,6]
		//[8,6,7,8]
		TST<Integer> st = new TST<>();
		st.put("d", 0);
		st.put("c", 0);
		String result = st.longestPrefixOf("b");

		assertEquals("", result);
	}

	@Test
	public void testLongestPrefixOfValid3() {
		//[1,3,5,6,7,9]
		//[7,8,6,13]
		//[7,9,6,7]
		//[6,7,9,6]
		//[6,7,8,6]
		//[9,6,7,8]
		TST<Integer> st = new TST<>();
		st.put("a", 0);
		st.put("d", 0);
		String result = st.longestPrefixOf("b");

		assertEquals("", result);
	}

	@Test
	public void testLongestPrefixOfValid4() {
		//[1,3,5,6,7,9]
		//[7,9,6,7]
		//[7,9,6,13]
		//[6,7,9,6]
		//[9,6,7,9]
		TST<Integer> st = new TST<>();
		st.put("a", 0);
		st.put("b", 0);
		String result = st.longestPrefixOf("c");

		assertEquals("", result);
	}

	@Test
	public void testLongestPrefixOfValid5() {
		//[1,3,5,6,7,10,12]
		//[6,7,10,12,6]
		//[7,10,12,6,7]
		//[10,12,6,7,9]
		//[7,9,6,13]
		//[6,7,9,6]
		TST<Integer> st = new TST<>();
		st.put("string", 0);
		String result = st.longestPrefixOf("sz");

		assertEquals("", result);
	}
	
	@Test
	public void testLongestPrefixOfValid6() {
		//[1,3,5,6,7,10,12]
		//[6,7,10,12,6]
		//[7,10,12,6,13]
		//[7,10,12,6,7]
		//[12,6,7,10,12]
		//[10,12,6,7,10]
		TST<Integer> st = new TST<>();
		st.put("string", 0);
		
		String result = st.longestPrefixOf("st");
		
		assertEquals("", result);
	}
	
	@Test
	public void testLongestPrefixOfValid7() {
		//[1,3,5,6,7,9]
		//[6,7,10,12,6]
		//[7,10,12,6,13]
		//[9,6,7,10,12]
		//[7,9,6,7]
		//[6,7,9,6]
		TST<Integer> st = new TST<>();
		st.put("s", 0);
		st.put("ut", 0);
		
		String result = st.longestPrefixOf("u");
		
		assertEquals("", result);
	}
	
	@Test
	public void testLongestPrefixOfValid8() {
		//[1,3,5,6,7,8]
		//[6,7,10,12,6]
		//[7,10,12,6,13]
		//[8,6,7,10,12]
		//[7,8,6,7]
		//[6,7,8,6]
		TST<Integer> st = new TST<>();
		st.put("s", 0);
		st.put("at", 0);
		
		String result = st.longestPrefixOf("a");
		
		assertEquals("", result);
	}
	
	@Test
	public void testLongestPrefixOfValid9() {
		//[1,3,5,6,7,10,12]
		//[6,7,10,12,6]
		//[7,10,12,6,7]
		//[10,12,6,7,8]
		//[7,8,6,13]
		//[6,7,8,6]
		TST<Integer> st = new TST<>();
		st.put("sb", 0);
		st.put("su", 0);
		
		String result = st.longestPrefixOf("sa");
		
		assertEquals("", result);
	}
	
	@Test
	public void testLongestPrefixOfValid10() {
		//[1,3,5,6,7,10,11,12]
		//[6,7,10,11,12,6]
		//[7,10,11,12,6,7]
		//[10,11,12,6,7,10]
		//[6,7,10,12,6]
		//[7,10,12,6,13]
		//[12,6,7,10,12]
		TST<Integer> st = new TST<>();
		st.put("sss", 0);
		
		String result = st.longestPrefixOf("ss");
		
		assertEquals("", result);
	}
	
	@Test
	public void testLongestPrefixOfValid11() {
		//[1,3,5,6,7,10,11,12]
		//[6,7,10,11,12,6]
		//[7,10,11,12,6,7]
		//[10,11,12,6,7,9]
		//[7,9,6,13]
		//[6,7,9,6]
		TST<Integer> st = new TST<>();
		st.put("s", 0);
		st.put("string", 0);
		st.put("sabre", 0);
		
		String result = st.longestPrefixOf("sz");

		assertEquals("s",result);
	}
	
	@Test
	public void testLongestPrefixOfValid12() {
		//[1,3,5,6,7,10,11,12]
		//[6,7,10,11,12,6]
		//[7,10,11,12,6,13]
		//[7,10,11,12,6,7]
		//[12,6,7,10,11,12]
		//[11,12,6,7,10,11]
		//[10,11,12,6,7,10]
		TST<Integer> st = new TST<>();
		st.put("st", 0);
		st.put("s", 0);
		
		String result = st.longestPrefixOf("st");
		
		assertEquals("st", result);
	}
	
	@Test
	public void testLongestPrefixOfValid13() {
		//[1,3,5,6,7,10,12]
		//[6,7,10,11,12,6]
		//[7,10,11,12,6,13]
		//[12,6,7,10,11,12]
		//[6,7,10,12,6]
		//[7,10,12,6,7]
		//[10,12,6,7,10]
		TST<Integer> st = new TST<>();
		st.put("st", 0);
		
		String result = st.longestPrefixOf("st");
		
		assertEquals("st", result);
	}
	
	@Test
	public void testLongestPrefixOfValid14() {
		//[6,7,10,11,12,6]
		//[7,10,11,12,6,13]
		//[1,3,5,6,7,9]
		//[9,6,7,10,11,12]
		//[7,9,6,7]
		//[6,7,9,6]
		TST<Integer> st = new TST<>();
		st.put("s", 0);
		st.put("u", 0);
		
		String result = st.longestPrefixOf("ut");
		
		assertEquals("u", result);
	}
	
	@Test
	public void testLongestPrefixOfValid15() {
		//[6,7,10,11,12,6]
		//[7,10,11,12,6,13]
		//[1,3,5,6,7,8]
		//[8,6,7,10,11,12]
		//[7,8,6,7]
		//[6,7,8,6]
		TST<Integer> st = new TST<>();
		st.put("s", 0);
		st.put("a", 0);
		
		String result = st.longestPrefixOf("at");
		
		assertEquals("a", result);
	}
	
	@Test
	public void testLongestPrefixOfValid16() {
		//[1,3,5,6,7,10,11,12]
		//[6,7,10,11,12,6]
		//[7,10,11,12,6,7]
		//[10,11,12,6,7,8]
		//[7,8,6,13]
		//[6,7,8,6]
		TST<Integer> st = new TST<>();
		st.put("as", 0);
		st.put("a", 0);
		
		String result = st.longestPrefixOf("aa");
		
		assertEquals("a", result);
	}

}
