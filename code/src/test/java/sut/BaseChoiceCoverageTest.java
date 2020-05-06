package sut;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

public class BaseChoiceCoverageTest {

	/**
	 * Input State Partitioning
	 * 
	 * Vamos assumir que todos as características são do tipo Bool
	 * Trie is empty => Empty or !Empty => True || False
	 * Trie contains elem => Contains or !Contains => True || False
	 * Trie already includes some new key prefix ==> Includes or !Includes ==> true || false
	 * Sendo assim, podemos pegar num caso base [False,False, False] -> fazível
	 * [True, False, False] -> fazível
	 * [True, True, False] -> Não é fazível, em casos onde a primeira condição se verifica verdade podermos testar
	 * qualquer uma das seguintes como tu, uma trie sem elementos nunca pode conter um elemento que estamos a querer pesquisar/dar overwrite
	 * [True, True, True] -> Para além do descrito acima, uma trie vazia não pode ter um key prefix 
	 *	TODO restantes y/n? 
	 */
	@Test
	public void testPutAlreadyExistingKey() {
		TST<Integer> st = new TST<>();
		st.put("a", 0);
		st.put("a", 1);
		String expected = "1";
		String result = st.get("a").toString();
		assertEquals(expected, result);
	}
	
	@Test
	public void testPutAlreadyExistingKey2() {
		TST<Integer> st = new TST<>();//empty trie
		st.put("a", 0);
		st.put("b", 1);
		String expected = "0";
		String result = st.get("a").toString();
		assertEquals(expected, result);
	}
	
	@Test
	public void testPutAlreadyExistingKey3() {
		TST<Integer> st = new TST<>();//empty trie
		st.put("a", 0);
		st.put("a", 2);
		st.put("a", 0);
		st.put("b", 2);
		st.put("a", 4);
		st.put("a", 0);
		st.put("b", 3);
		st.put("a", 7);
		st.put("b", 1);
		String expected = "7";
		String result = st.get("a").toString();
		assertEquals(expected, result);
	}
	
	
	@Test
	public void testKeyPrefixWithEmptyTST() {
		TST<Integer> st = new TST<>();
		String result = st.longestPrefixOf("abcd");
		String expected = "";
		assertEquals(expected,result);
	}
	
	@Test
	void testPutOnEmptyTrie() {
		TST<Integer> st = new TST<>();
		int expected = 0;
		int size1 = st.size();
		assertEquals(expected, size1);
	}

}
