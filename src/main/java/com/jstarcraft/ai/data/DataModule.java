package com.jstarcraft.ai.data;

import java.util.Map.Entry;

import com.jstarcraft.core.utility.KeyValue;

import it.unimi.dsi.fastutil.ints.Int2FloatSortedMap;
import it.unimi.dsi.fastutil.ints.Int2IntSortedMap;

/**
 * 数据模型
 * 
 * <pre>
 * 模型由实例组成,每个实例由离散特征和连续特征组成.
 * </pre>
 * 
 * @author Birdy
 *
 */
public interface DataModule {

	/**
	 * 获取稀疏秩
	 * 
	 * @return
	 */
	int getDiscreteOrder();

	/**
	 * 获取连续秩
	 * 
	 * @return
	 */
	int getContinuousOrder();

	/**
	 * 关联实例
	 * 
	 * @param discreteFeatures
	 * @param continuousFeatures
	 */
	void associateInstance(Int2IntSortedMap discreteFeatures, Int2FloatSortedMap continuousFeatures);

	/**
	 * 获取实例
	 * 
	 * @param cursor
	 * @return
	 */
	DataInstance getInstance(int cursor);

	/**
	 * 获取大小
	 * 
	 * @return
	 */
	int getSize();
	
	/**
	 * 获取指定外部索引的属性投影(true代表离散,false代表连续)
	 * 
	 * @param index
	 * @return
	 */
	Entry<Integer, KeyValue<String, Boolean>> getOuterKeyValue(int index);

	/**
	 * 获取指定离散属性的内部索引
	 * 
	 * @param name
	 * @return
	 */
	int getDiscreteInner(String name);

	/**
	 * 获取指定连续属性的内部索引
	 * 
	 * @param name
	 * @return
	 */
	int getContinuousInner(String name);

}
