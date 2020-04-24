package sut;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import sut.TST;

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
		TST<Integer> st = new TST<>();
		String result = st.longestPrefixOf("");
		assertNull(result);
	}
	
	
	
}
