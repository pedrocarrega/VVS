package sut;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class AllUsesCoverageTests {

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
		assertNull(result);
	}
	
	@Test
	public void testLongestPrefixOfWithEmptyTST() {
		TST<Integer> st = new TST<>();
		String result = st.longestPrefixOf("string");
		String expected = "";
		assertEquals(expected,result);
	}
	
	@Test
	public void testLongestPrefixOfValid1() {
		TST<Integer> st = new TST<>();
		st.put("c", 0);
		st.put("a", 0);
		st.put("b", 0);
		String result = st.longestPrefixOf("b");
		String expected = "b";
		assertEquals(expected,result);
	}
	
	@Test
	public void testLongestPrefixOfValid2() {
		TST<Integer> st = new TST<>();
		st.put("lisboa", 0);
		String result = st.longestPrefixOf("listar");
		String expected = "";
		assertEquals(expected,result);
	}
	
	@Test
	public void testLongestPrefixOfValid3() {
		TST<Integer> st = new TST<>();
		st.put("a", 0);
		String result = st.longestPrefixOf("lazy");
		String expected = "";
		assertEquals(expected,result);
	}
	
	@Test
	public void testLongestPrefixOfValid4() {
		TST<Integer> st = new TST<>();
		st.put("l", 0);
		st.put("lis", 0);
		st.put("la", 0);
		st.put("laght", 0);
		st.put("last", 0);
		st.put("lazy", 0);
		st.put("lazulis", 0);
		
		String result = st.longestPrefixOf("lazer");
		String expected = "la";
		assertEquals(expected,result);
	}
	
	@Test
	public void testLongestPrefixOfValid5() {
		TST<Integer> st = new TST<>();
		st.put("lisbon", 0);
		st.put("word", 0);
		st.put("po", 0);
		st.put("poste", 0);
		
		String result = st.longestPrefixOf("post");
		String expected = "po";
		assertEquals(expected,result);
	}
}
