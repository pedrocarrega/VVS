package sut;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import sut.TST;

/*
 * It was choosen GACC instead of CC or PC because of the while predicate
 * The GACC was choosen instead of CACC or RACC because in the while predicate
 * 		the value will have no effect in the sense that when the clause is
 * 		deterministic, and its value is true then the condition will be true,
 * 		when the value is false then the predicate will be false also, so this
 * 		would not have any effect on the while predicate so that was why the GACC
 * 		was choosen. The other predicates are only composed by one clause so the
 * 		GACC would be the correct for them too
 * 
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
