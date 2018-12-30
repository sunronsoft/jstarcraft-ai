package com.jstarcraft.ai.data.attribute;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Assert;

import com.jstarcraft.core.utility.RandomUtility;

public abstract class DiscreteAttributeTestCase extends DataAttributeTestCase {

	abstract protected DiscreteAttribute<Float> getDiscreteAttribute();

	@Override
	public void testConvertValue() {
		int size = 1000;
		float[] datas = new float[size];
		for (int index = 0; index < size; index++) {
			datas[index] = index;
		}
		RandomUtility.shuffle(datas);
		DiscreteAttribute<Float> attribute = getDiscreteAttribute();
		for (int index = 0; index < size; index++) {
			Assert.assertEquals(index, attribute.convertValue(datas[index]));
		}
		Assert.assertEquals(size, attribute.getSize());
		for (int index = 0; index < size; index++) {
			Assert.assertEquals(index, attribute.convertValue(datas[index]));
		}
		Assert.assertEquals(size, attribute.getSize());
	}

	@Override
	public void testConcurrent() throws Exception {
		int size = 1000;
		float[] datas = new float[size];
		for (int index = 0; index < size; index++) {
			datas[index] = index;
		}
		RandomUtility.shuffle(datas);

		final int numberOfThread = 10;
		ExecutorService executor = Executors.newFixedThreadPool(numberOfThread);
		final CyclicBarrier barrier = new CyclicBarrier(numberOfThread + 1);
		DiscreteAttribute<Float> attribute = getDiscreteAttribute();
		for (int thread = 0; thread < numberOfThread; thread++) {
			executor.submit(() -> {
				try {
					barrier.await();
					for (int index = 0; index < size; index++) {
						Assert.assertEquals(index, attribute.convertValue(datas[index]));
					}
					Assert.assertEquals(size, attribute.getSize());
					barrier.await();
				} catch (Exception exception) {
					exception.printStackTrace();
					Assert.fail();
				}
			});
		}
		// 等待所有线程开始
		barrier.await();
		// 等待所有线程结束
		barrier.await();
	}

}
