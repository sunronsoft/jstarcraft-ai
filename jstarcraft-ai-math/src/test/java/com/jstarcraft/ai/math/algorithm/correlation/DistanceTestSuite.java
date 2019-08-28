package com.jstarcraft.ai.math.algorithm.correlation;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        // 距离测试集
        AngularDistanceTestCase.class,

        ChebychevDistanceTestCase.class,

        EuclideanDistanceTestCase.class,

        LevensteinDistanceTestCase.class,

        ManhattanDistanceTestCase.class,

        MSDDistanceTestCase.class,

        MSEDistanceTestCase.class,

        NormDistanceTestCase.class,

        SpearmanFootruleDistanceTestCase.class })
public class DistanceTestSuite {

}