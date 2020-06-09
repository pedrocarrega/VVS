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


	@SuppressWarnings("unused")
	@Property
	public void testKeysWithPrefix(@From(TrieGenerator.class) TST<Integer> tst) {

		Iterable<String> keys = tst.keys();
		List<String> list = new ArrayList<String>();
		boolean result = true;

		Random r = new Random();
		for(String s: keys) {
			list.add(s);
		}

		//escolher 1 random key
		String convert = "";

		do {
			convert = list.get(r.nextInt(list.size()));
		}while(convert.length() < 2);

		String test = convert;
		do {
			int count = 0;
			int countStrict = 0;

			Iterable<String> iterator = tst.keysWithPrefix(test);

			for(String key : iterator)
				count++;

			test = test.substring(0, test.length()-1);

			iterator = tst.keysWithPrefix(test);

			for(String key : iterator)
				countStrict++;

			if(count > countStrict) {
				result = false;
				break;
			}

		}while(test.length() > 1);

		assertTrue(result);
	}


}
