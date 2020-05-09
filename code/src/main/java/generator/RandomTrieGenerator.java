package generator;


import java.util.*;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

import sut.TST;

public class RandomTrieGenerator extends Generator<TST> {

    private static final int MAX_VALUE = 100;
    private static final GenerationStatus.Key<Integer> PREVIOUS_KEY = new GenerationStatus.Key<>("previous", Integer.class);

    @SuppressWarnings("unchecked")
    public RandomTrieGenerator() {
        super((Class<TST>) (Class) List.class);
    }

    @SuppressWarnings("unchecked")
	public TST<Integer> generate(SourceOfRandomness sourceOfRandomness, GenerationStatus generationStatus) {
        TST<Integer> result = new TST<>();

        int previous = generationStatus.valueOf(PREVIOUS_KEY).orElse(MAX_VALUE);
        int current = sourceOfRandomness.nextInt(previous);

        if (current > 0) {
            result.put("a", current);

            generationStatus.setValue(PREVIOUS_KEY, current);
            Generator<TST> listGen = gen().make(RandomTrieGenerator.class);
            result.put(listGen.generate(sourceOfRandomness, generationStatus));
            generationStatus.setValue(PREVIOUS_KEY, null);
        }

        return result;
    }
}