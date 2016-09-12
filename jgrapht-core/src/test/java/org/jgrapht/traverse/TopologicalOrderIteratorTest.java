/*
 * (C) Copyright 2005-2016, by John V Sichi and Contributors.
 *
 * JGraphT : a free Java graph-theory library
 *
 * This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */
package org.jgrapht.traverse;

import java.util.*;

import org.jgrapht.*;
import org.jgrapht.graph.*;


/**
 * Tests for TopologicalOrderIterator.
 *
 * @author John V. Sichi
 * @since Apr 25, 2005
 */
public class TopologicalOrderIteratorTest
    extends EnhancedTestCase
{
    //~ Methods ----------------------------------------------------------------

    /**
     * .
     */
    public void testRecipe()
    {
        DirectedGraph<String, DefaultEdge> graph =
                new DefaultDirectedGraph<>(
                        DefaultEdge.class);

        String [] v = new String[9];

        v[0] = "preheat oven";
        v[1] = "sift dry ingredients";
        v[2] = "stir wet ingredients";
        v[3] = "mix wet and dry ingredients";
        v[4] = "spoon onto pan";
        v[5] = "bake";
        v[6] = "cool";
        v[7] = "frost";
        v[8] = "eat";

        // add in mixed up order
        graph.addVertex(v[4]);
        graph.addVertex(v[8]);
        graph.addVertex(v[1]);
        graph.addVertex(v[3]);
        graph.addVertex(v[7]);
        graph.addVertex(v[6]);
        graph.addVertex(v[0]);
        graph.addVertex(v[2]);
        graph.addVertex(v[5]);

        // specify enough edges to guarantee deterministic total order
        graph.addEdge(v[0], v[1]);
        graph.addEdge(v[1], v[2]);
        graph.addEdge(v[0], v[2]);
        graph.addEdge(v[1], v[3]);
        graph.addEdge(v[2], v[3]);
        graph.addEdge(v[3], v[4]);
        graph.addEdge(v[4], v[5]);
        graph.addEdge(v[5], v[6]);
        graph.addEdge(v[6], v[7]);
        graph.addEdge(v[7], v[8]);
        graph.addEdge(v[6], v[8]);

        Iterator<String> iter =
                new TopologicalOrderIterator<>(graph);
        int i = 0;

        while (iter.hasNext()) {
            assertEquals(v[i], iter.next());
            ++i;
        }

        // Test with a reversed view
        DirectedGraph<String, DefaultEdge> reversed =
                new EdgeReversedGraph<>(graph);

        iter = new TopologicalOrderIterator<>(reversed);
        i = v.length - 1;

        while (iter.hasNext()) {
            assertEquals(v[i], iter.next());
            --i;
        }
    }

    /**
     * .
     */
    public void testEmptyGraph()
    {
        DirectedGraph<String, DefaultEdge> graph =
                new DefaultDirectedGraph<>(
                        DefaultEdge.class);
        Iterator<String> iter =
                new TopologicalOrderIterator<>(graph);
        assertFalse(iter.hasNext());
    }
}

// End TopologicalOrderIteratorTest.java
