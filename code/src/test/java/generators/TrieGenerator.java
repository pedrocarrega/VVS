package generators;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

import sut.TST;

public class TrieGenerator extends Generator<TST<Integer>>{

	private final int maxNodeSize = 100;
	private final int maxStringSize = 25;
	
	public TrieGenerator(Class<TST<Integer>> type) {
		super(type);
	}

	@Override
	public TST<Integer> generate(SourceOfRandomness random, GenerationStatus status) {

		int size = 1 + random.nextInt(maxNodeSize-1);
		TST<Integer> tst = new TST<>();
		
		while(size-- > 0) {
			//generate string
			StringBuilder key = new StringBuilder();
			int keySize = 1 + random.nextInt(maxStringSize-1);
			while(keySize-- > 0)
				key.append(random.nextChar('a', 'z'));
			
			tst.put(key.toString(), random.nextInt(maxNodeSize));
		}
		return tst;
	}
}
