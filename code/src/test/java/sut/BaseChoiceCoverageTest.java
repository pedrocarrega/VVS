package sut;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

public class BaseChoiceCoverageTest {

	/**
	 * Input State Partitioning
	 * 
	 * Caracteristics:
	 * 	1- Trie is empty
	 * 	2- Trie already includes the new key
	 *  3- Trie already includes some new key prefix
	 *  
	 *  With this characteristics we can verify that all of them have equal blocks, that are [true,false]
	 *  So starting with the following base choice:
	 *  [true,true,true] 		TR1
	 *  [false,true,true]		TR2
	 *  [false,false,true]		TR3
	 *  [false,false,false]		TR4
	 *  
	 *  With this 4 Test Requirements we can cover all possible values of each characteristic
	 *  One important note is that TR1 is infeasible since we can not have an empty Trie were the Trie already included the new key
	 *  All the other Test Requirements will be feasible and be verified, TR1 is never going to be verified
	 */
	
	/*
	 * -Covers TR1
	 * Infeasible Test Requirement
	 */
	@Test
	public void testPutWithEmptyTrie() {
		TST<Integer> st = new TST<>();
		String input = "a";
		
		//This covers the property 1- Trie is empty
		boolean is_empty = st.size() == 0;
		assertTrue(is_empty);
		
		//This covers the property 2- Trie already includes the new key
		boolean contains_key = st.contains(input);
		assertTrue(contains_key);
		
		//This covers the property 3- Trie already includes some new key prefix
		String prefix_found = st.longestPrefixOf(input);
		boolean contains_prefix = !prefix_found.equals("");
		assertTrue(contains_prefix);	
				
		st.put(input,0);
	}
	
	/*
	 * -Covers TR2
	 */
	@Test
	public void testPutNonEmptyTrieWithKeyAndPrefix() {
		TST<Integer> st = new TST<>();
		st.put("a", 0);
		st.put("ab", 1);
		String input = "a";
		
		//This covers the property 1- Trie is empty
		boolean is_empty = st.size() == 0;
		assertFalse(is_empty);
		
		//This covers the property 2- Trie already includes the new key
		boolean contains_key = st.contains(input);
		assertTrue(contains_key);
		
		//This covers the property 3- Trie already includes some new key prefix
		String prefix_found = st.longestPrefixOf(input);
		boolean contains_prefix = !prefix_found.equals("");
		assertTrue(contains_prefix);
		
		st.put(input, 0);
	}
	
	/*
	 * -Covers TR3
	 */
	@Test
	public void testPutNonEmptyTrieWithPrefix() {
		TST<Integer> st = new TST<>();
		String input = "ab";
		
		st.put("a", 0);
		
		//This covers the property 1- Trie is empty
		boolean is_empty = st.size() == 0;
		assertFalse(is_empty);
		
		//This covers the property 2- Trie already includes the new key
		boolean contains_key = st.contains(input);
		assertFalse(contains_key);
		
		//This covers the property 3- Trie already includes some new key prefix
		String prefix_found = st.longestPrefixOf(input);
		boolean contains_prefix = !prefix_found.equals("");
		assertTrue(contains_prefix);
	}
	
	/*
	 * -Covers TR4
	 */
	@Test
	public void testPutNonEmptyTrieWithoutKeyOrPrefix() {
		TST<Integer> st = new TST<>();
		String input = "b";
		st.put("a", 0);
		
		//This covers the property 1- Trie is empty
		boolean is_empty = st.size() == 0;
		assertFalse(is_empty);
		
		//This covers the property 2- Trie already includes the new key
		boolean contains_key = st.contains(input);
		assertFalse(contains_key);
		
		//This covers the property 3- Trie already includes some new key prefix
		String prefix_found = st.longestPrefixOf(input);
		boolean contains_prefix = !prefix_found.equals("");
		assertFalse(contains_prefix);
				
		st.put(input, 1);
	}
}
