package sut;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import generators.TrieGenerator;

@RunWith(JUnitQuickcheck.class)
public class PropertyGeneratorTest {

	private final int maxIntSize = 50;
	private final int maxStringSize = 15;

	@Property
	public void testOrder(@From(TrieGenerator.class) TST<Integer> tst) {

		Random r = new Random();
		TST<Integer> temp = new TST<>();
		List<String> keys = new ArrayList<>();
		Iterable<String> tempKeys = tst.keys();

		for(String key : tempKeys)
			keys.add(key);

		while(!keys.isEmpty()) {
			int index = r.nextInt(keys.size());
			temp.put(keys.get(index), tst.get(keys.get(index)));
			keys.remove(index);
		}

		assertTrue(tst.equals(temp));
	}


	@Property
	public void testRemove(@From(TrieGenerator.class) TST<Integer> tst) {

		Iterable<String> keys = tst.keys();

		for(String key : keys)
			tst.delete(key);

		assertEquals(0, tst.size());
	}


	@Property
	public void testInsertDelete(@From(TrieGenerator.class) TST<Integer> tst) {

		TST<Integer> temp = new TST<>();
		Random r = new Random();
		Iterable<String> keys = tst.keys();

		for(String key : keys)
			temp.put(key, tst.get(key));

		String key = r.ints(97, 123)
				.limit(maxStringSize)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();

		temp.put(key, r.nextInt(maxIntSize));
		temp.delete(key);

		assertTrue(tst.equals(temp));
	}

	
	@Property
	public void testKeysWithPrefix(@From(TrieGenerator.class) TST<Integer> tst) {

		Iterable<String> keys = tst.keys();
		List<String> list = new ArrayList<String>();
		
		Random r = new Random();
		for(String s: keys) {
			list.add(s);
		}
		//Object randomValue = values[generator.nextInt(values.length)];
		int ran = r.nextInt(list.size()-1);
		//key random escolhida
		String convert = list.get(ran);
		
		tst.keysWithPrefix(convert);
		//Remover alguns chars para testar output
		String menor;
		if(convert.length() > 2) {
			menor = convert.substring(0, convert.length()-(convert.length()-1));
		}
		else {
			menor = convert;
		}
		boolean expected = false;
		List<String> big = (List<String>) tst.keysWithPrefix(convert);
		List<String> small = (List<String>) tst.keysWithPrefix(menor);
		System.out.println(big.size() + " ->big");
		System.out.println(small.size() + " ->small");
		if(big.size() <= small.size()) {
			expected = true;
		}
		assertEquals(expected, true);
	}
	 

}
