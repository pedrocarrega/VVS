package sut;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

/*
 * Test Requirements:
 * 		p1: query == null
 * 			query != null
 * 		p2: query.length == 0
 * 			query.length != 0
 * 		p3: x != null && i < query.length
 * 			x == null && i < query.length
 * 			i < query.length && x != null
 * 			i >= query.length && x != null
 * 		p4: c < x.c
 * 			c >= x.c
 * 		p5: c > x.c
 * 			c <= x.c
 * 		p6: x.val != null
 * 			x.val == null
 */

public class GeneralActiveClauseCoverageTests {
	
	/*
	 * Covers:
	 * 	p1: query == null 	
	 */
	@Test
	public void testLongestPrefixOfWithNullQuery() {
		TST<Integer> st = new TST<>();
		assertThrows(IllegalArgumentException.class, () -> {
			st.longestPrefixOf(null);
		});
	}
	
	/*
	 * Covers:
	 * 	p1: query != null
	 * 	p2: query.length == 0 	
	 */
	@Test
	public void testLongestPrefixOfWithEmptyQuery() {
		TST<Integer> st = new TST<>();
		String result = st.longestPrefixOf("");
		assertNull(result);
	}
	
	/*
	 * Covers:
	 *  p1: query != null
	 * 	p2: query.length != 0
	 * 	p2: query.length != 0 	
	 * 	p3: x == null && i < query.length
	 */
	@Test
	public void testLongestPrefixOfWithEmptyTST() {
		TST<Integer> st = new TST<>();
		String result = st.longestPrefixOf("a");
		String expected = "";
		assertEquals(expected,result);
	}
	
	/*
	 * Covers:
	 *  p1: query != null
	 * 	p2: query.length != 0
	 * 	p3: x != null && i < query.length
	 * 	p3: i < query.length && x != null
	 *  p3: x == null && i < query.length
	 * 	p4: c < x.c
	 */
	@Test
	public void testLongestPrefixOfWithNoMatches1() {
		TST<Integer> st = new TST<>();
		st.put("string", 0);
		String result = st.longestPrefixOf("a");
		String expected = "";
		assertEquals(expected,result);
	}
	
	/*
	 * Covers:
	 * 	p1: query != null
	 * 	p2: query.length != 0
	 *  p3: x != null && i < query.length
	 * 	p3: i < query.length && x != null 	
	 * 	p3: i >= query.length && x != null
	 * 	p4: c >= x.c
	 * 	p5: c <= x.c
	 * 	p6: x.val == null
	 */
	@Test
	public void testLongestPrefixOfWithNoMatches2() {
		TST<Integer> st = new TST<>();
		st.put("string", 0);
		String result = st.longestPrefixOf("s");
		String expected = "";
		assertEquals(expected,result);
	}
	
	
	/*
	 * Covers:
	 * 	p1: query != null
	 * 	p2: query.length != 0 	
	 *  p3: x != null && i < query.length
	 * 	p3: i < query.length && x != null
	 * 	p4: c >= x.c
	 *  p5: c > x.c
	 *  p5:	c <= x.c
	 * 	p6: x.val != null
	 */
	@Test
	public void testLongestPrefixOfWithMatch() {
		TST<Integer> st = new TST<>();
		st.put("s", 0);
		st.put("z", 1);
		String result = st.longestPrefixOf("z");
		String expected = "z";
		assertEquals(expected,result);
	}
}
