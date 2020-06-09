package sut;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class EdgeCoverageTests {
	
	/*
	 * Test Requirements:
	 * TR(EC) = {[1],[2],[3],[4],[5],[6],[7],[8],[9],[10],[11],[12],[13],[1,2],[1,3],[3,4],
	 *  [3,5],[5,6],[6,7],[6,13],[7,8],[7,9],[7,10],[8,6],[9,6],[10,12],[10,11][11,12],[12,6]}
	 */

	/*
	 * Test Path:
	 * [1,2]
	 */
	@Test
	public void testLongestPrefixOfWithNullQuery() {
		TST<Integer> st = new TST<>();
		assertThrows(IllegalArgumentException.class, () -> {
			st.longestPrefixOf(null);
		});
	}
	
	/*
	 * Test Path:
	 * [1,3,4]
	 */
	@Test
	public void testLongestPrefixOfWithEmptyQuery() {
		TST<Integer> st = new TST<>();
		String result = st.longestPrefixOf("");
		assertEquals(null, result);
	}
	
	/*
	 * Test Path:
	 * [1,3,5,6,7,8,6,7,9,6,7,10,11,12,6,13]
	 */
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
	
	/*
	 * Test Path:
	 * [1,3,5,6,7,10,12,6,13]
	 */
	@Test
	public void testLongestPrefixOfWithValidQuery2() {
		TST<Integer> st = new TST<>();
		st.put("ca", 0);
		String result = st.longestPrefixOf("c");
		String expected = "";
		assertEquals(expected, result);
	}
}
