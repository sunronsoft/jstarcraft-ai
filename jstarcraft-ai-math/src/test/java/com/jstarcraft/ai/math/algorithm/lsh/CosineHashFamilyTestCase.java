package com.jstarcraft.ai.math.algorithm.lsh;

import com.jstarcraft.ai.math.algorithm.lsh.CosineHashFamily;
import com.jstarcraft.ai.math.algorithm.lsh.LshHashFamily;
import com.jstarcraft.core.utility.RandomUtility;

public class CosineHashFamilyTestCase extends LshHashFamilyTestCase {

    @Override
    protected LshHashFamily getHashFamily(int dimensions) {
        return new CosineHashFamily(dimensions);
    }

    @Override
    protected float getRandomData() {
        return RandomUtility.randomFloat(1F);
    }

}
