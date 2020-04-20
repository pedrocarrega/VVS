package sut;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class EdgeCoverage {

	@Test
	public void testLongestPrefixOfWithNullQuery() {
		TST<Integer> st = new TST<>();
		assertThrows(IllegalArgumentException.class, () -> {
			st.longestPrefixOf(null);
		});
	}
	
	@Test
	public void testLongestPrefixOfWithEmptyQuery() {
		TST<Integer> st = new TST<>();
		String result = st.longestPrefixOf("");
		assertEquals(null, result);
	}
	
	@Test
	public void testLongestPrefixOfWithValidQuery1() {
		TST<Integer> st = new TST<>();
		st.put("c", 0);
		st.put("a", 0);
		st.put("b", 0);
		String result = st.longestPrefixOf("b");
		String expected = "b";
		assertEquals(expected, result);
	}
	
	@Test
	public void testLongestPrefixOfWithValidQuery2() {
		TST<Integer> st = new TST<>();
		st.put("ca", 0);
		String result = st.longestPrefixOf("c");
		String expected = "";
		assertEquals(expected, result);
	}
}
