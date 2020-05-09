package generators;

import java.util.ArrayList;
import java.util.List;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

import sut.Pair;

public class KeyPairGenerator extends Generator<List<Pair<String, Integer>>>{

	private final int maxListSize = 20;
	private final int maxStringSize = 15;
	
	protected KeyPairGenerator(Class<List<Pair<String, Integer>>> type) {
		super(type);
	}

	@Override
	public List<Pair<String, Integer>> generate(SourceOfRandomness random, GenerationStatus status) {

		int size = 1 + random.nextInt(maxListSize-1);
		List<Pair<String, Integer>> list = new ArrayList<>();
		
		while(size-- > 0) {
			//generate string
			StringBuilder key = new StringBuilder();
			int keySize = 1 + random.nextInt(maxStringSize-1);
			while(keySize-- > 0)
				key.append(random.nextChar('a', 'z'));
			
			list.add(new Pair<String, Integer>(key.toString(), 1 + random.nextInt(maxListSize-1)));
		}
		return list;
	}
}
