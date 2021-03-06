/*
 * Copyright (C) 2015 Edward Raff <Raff.Edward@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.jstarcraft.ai.jsat.linear.vectorcollection;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jstarcraft.ai.jsat.linear.DenseVector;
import com.jstarcraft.ai.jsat.linear.Vec;
import com.jstarcraft.ai.jsat.linear.distancemetrics.EuclideanDistance;
import com.jstarcraft.ai.jsat.utils.random.RandomUtil;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.ints.IntArrayList;

/**
 *
 * @author Edward Raff <Raff.Edward@gmail.com>
 */
public class BallTreeTest {

    public BallTreeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSearch_Vec_double() {
        System.out.println("search");
        Random rand = RandomUtil.getRandom();

        VectorArray<Vec> vecCol = new VectorArray<>(new EuclideanDistance());
        for (int i = 0; i < 250; i++)
            vecCol.add(DenseVector.random(3, rand));

        for (int leaf_size : new int[] { 2, 10, 40 })
            for (BallTree.PivotSelection pm : BallTree.PivotSelection.values())
                for (BallTree.ConstructionMethod cm : BallTree.ConstructionMethod.values())
                    for (boolean parallel : new boolean[] { true, false }) {

                        BallTree<Vec> collection0 = new BallTree<>(new EuclideanDistance(), cm, pm);
                        collection0.setLeafSize(leaf_size);
                        collection0 = collection0.clone();
                        collection0.build(parallel, vecCol);
                        collection0 = collection0.clone();

                        IntArrayList trueNN = new IntArrayList();
                        DoubleArrayList trueNN_dists = new DoubleArrayList();

                        IntArrayList foundNN = new IntArrayList();
                        DoubleArrayList foundNN_dists = new DoubleArrayList();

                        for (int iters = 0; iters < 10; iters++)
                            for (double range : new double[] { 0.25, 0.5, 0.75, 2.0 }) {
                                int randIndex = rand.nextInt(vecCol.size());
                                Vec query = vecCol.get(randIndex);

                                vecCol.search(query, range, trueNN, trueNN_dists);
                                collection0.search(query, range, foundNN, foundNN_dists);

                                assertEquals(trueNN.size(), foundNN.size());
                                assertEquals(trueNN_dists.size(), foundNN_dists.size());

                                for (int i = 0; i < trueNN.size(); i++) {
                                    assertEquals(trueNN.get(i), foundNN.get(i));
                                    assertEquals(trueNN_dists.get(i), trueNN_dists.get(i), 0.0);
                                }

                            }
                    }

    }

    @Test
    public void testSearch_Vec_int() {
        System.out.println("search");
        Random rand = RandomUtil.getRandom();

        VectorArray<Vec> vecCol = new VectorArray<>(new EuclideanDistance());
        for (int i = 0; i < 250; i++)
            vecCol.add(DenseVector.random(3, rand));

        for (int leaf_size : new int[] { 2, 10, 40 })
            for (BallTree.PivotSelection pm : BallTree.PivotSelection.values())
                for (BallTree.ConstructionMethod cm : BallTree.ConstructionMethod.values())
                    for (boolean parallel : new boolean[] { true, false }) {

                        BallTree<Vec> collection0 = new BallTree<>(new EuclideanDistance(), cm, pm);
                        collection0.setLeafSize(leaf_size);
                        collection0 = collection0.clone();
                        collection0.build(parallel, vecCol);
                        collection0 = collection0.clone();

                        IntArrayList trueNN = new IntArrayList();
                        DoubleArrayList trueNN_dists = new DoubleArrayList();

                        IntArrayList foundNN = new IntArrayList();
                        DoubleArrayList foundNN_dists = new DoubleArrayList();

                        for (int iters = 0; iters < 10; iters++)
                            for (int neighbours : new int[] { 1, 2, 4, 10, 20 }) {
                                int randIndex = rand.nextInt(vecCol.size());

                                Vec query = vecCol.get(randIndex);

                                vecCol.search(query, neighbours, trueNN, trueNN_dists);
                                collection0.search(query, neighbours, foundNN, foundNN_dists);

                                assertEquals(trueNN.size(), foundNN.size());
                                assertEquals(trueNN_dists.size(), foundNN_dists.size());

                                for (int i = 0; i < trueNN.size(); i++) {
                                    assertEquals(trueNN.get(i), foundNN.get(i));
                                    assertEquals(trueNN_dists.get(i), trueNN_dists.get(i), 0.0);
                                }
                            }
                    }

    }

    @Test
    public void testSearch_Vec_int_incramental() {
        System.out.println("search");
        Random rand = RandomUtil.getRandom();

        VectorArray<Vec> vecCol = new VectorArray<>(new EuclideanDistance());
        for (int i = 0; i < 1000; i++)
            vecCol.add(DenseVector.random(3, rand));

        for (int leaf_size : new int[] { 2, 10, 40 })
            for (BallTree.PivotSelection pm : BallTree.PivotSelection.values())
                for (BallTree.ConstructionMethod cm : BallTree.ConstructionMethod.values()) {
                    BallTree<Vec> collection0 = new BallTree(new EuclideanDistance(), cm, pm);
                    collection0.setLeafSize(leaf_size);
                    for (Vec v : vecCol)
                        collection0.insert(v);

                    IntArrayList trueNN = new IntArrayList();
                    DoubleArrayList trueNN_dists = new DoubleArrayList();

                    IntArrayList foundNN = new IntArrayList();
                    DoubleArrayList foundNN_dists = new DoubleArrayList();
                    for (int iters = 0; iters < 10; iters++)
                        for (int neighbours : new int[] { 1, 2, 5, 10, 20 }) {
                            int randIndex = rand.nextInt(vecCol.size());

                            Vec query = vecCol.get(randIndex);

                            vecCol.search(query, neighbours, trueNN, trueNN_dists);
                            collection0.search(query, neighbours, foundNN, foundNN_dists);

                            assertEquals(trueNN.size(), foundNN.size());
                            assertEquals(trueNN_dists.size(), foundNN_dists.size());

                            for (int i = 0; i < trueNN.size(); i++) {
                                assertEquals(trueNN.get(i), foundNN.get(i));
                                assertEquals(trueNN_dists.get(i), trueNN_dists.get(i), 0.0);
                            }
                        }
                }
    }
}
