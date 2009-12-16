/*
    This file is part of Sarasvati.

    Sarasvati is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    Sarasvati is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with Sarasvati.  If not, see <http://www.gnu.org/licenses/>.

    Copyright 2009 Paul Lorenz
*/

package com.googlecode.sarasvati.unittest.joinlang;

import junit.framework.Assert;

import org.junit.Test;

import com.googlecode.sarasvati.Graph;
import com.googlecode.sarasvati.GraphProcess;
import com.googlecode.sarasvati.unittest.framework.ExecutionTest;
import com.googlecode.sarasvati.unittest.framework.TestProcess;

public class AtLeastArcsRequiredRequiredTest extends ExecutionTest
{
  private static final String TEST_GRAPH_NAME = "joinlang-at-least-arcs-required";

  @Test public void testOne() throws Exception
  {
    Graph g = ensureLoaded( TEST_GRAPH_NAME );
    GraphProcess p = engine.startProcess( g );

    String state =
      "[1 nodeA I F]" +
      "[2 nodeB I F]" +
      "[3 nodeC I F]";
    TestProcess.validate( p, state );

    completeToken( p, "nodeA");

    state =
      "[1 nodeA C F]" +
      "  (I F nodeD)" +
      "[2 nodeB I F]" +
      "[3 nodeC I F]";
    TestProcess.validate( p, state );

    completeToken( p, "nodeB");

    state =
      "[1 nodeA C F]" +
      "  (C F 4)" +
      "[2 nodeB C F]" +
      "  (C F 4)" +
      "[3 nodeC I F]" +
      "[4 nodeD I F]";
    TestProcess.validate( p, state );

    completeToken( p, "nodeC");

    state =
      "[1 nodeA C F]" +
      "  (C F 4)" +
      "[2 nodeB C F]" +
      "  (C F 4)" +
      "[3 nodeC C F]" +
      "  (C F 4)" +
      "[4 nodeD I F]";
    TestProcess.validate( p, state );

    completeToken( p, "nodeD");

    state =
      "[1 nodeA C F]" +
      "  (C F 4)" +
      "[2 nodeB C F]" +
      "  (C F 4)" +
      "[3 nodeC C F]" +
      "  (C F 4)" +
      "[4 nodeD C F]";
    TestProcess.validate( p, state );

    Assert.assertTrue( "Process should be complete", p.isComplete() );
  }

  public void testTwo() throws Exception
  {
    Graph g = ensureLoaded( TEST_GRAPH_NAME );
    GraphProcess p = engine.startProcess( g );

    String state =
      "[1 nodeA I F]" +
      "[2 nodeB I F]" +
      "[3 nodeC I F]";
    TestProcess.validate( p, state );

    completeToken( p, "nodeA");

    state =
      "[1 nodeA C F]" +
      "  (I F nodeD)" +
      "[2 nodeB I F]" +
      "[3 nodeC I F]";
    TestProcess.validate( p, state );

    completeToken( p, "nodeC");

    state =
      "[1 nodeA C F]" +
      "  (I F 4)" +
      "[2 nodeB I F]" +
      "[3 nodeC C F]" +
      "  (I F 4)" +
      "[4 nodeD I F]";
    TestProcess.validate( p, state );

    completeToken( p, "nodeB");

    state =
      "[1 nodeA C F]" +
      "  (C F 4)" +
      "[2 nodeB C F]" +
      "  (C F 4)" +
      "[3 nodeC C F]" +
      "  (C F 4)" +
      "[4 nodeD I F]";
    TestProcess.validate( p, state );

    completeToken( p, "nodeD");

    state =
      "[1 nodeA C F]" +
      "  (C F 4)" +
      "[2 nodeB C F]" +
      "  (C F 4)" +
      "[3 nodeC C F]" +
      "  (C F 4)" +
      "[4 nodeD C F]";
    TestProcess.validate( p, state );

    Assert.assertTrue( "Process should be complete", p.isComplete() );
  }

  public void testThree() throws Exception
  {
    Graph g = ensureLoaded( TEST_GRAPH_NAME );
    GraphProcess p = engine.startProcess( g );

    String state =
      "[1 nodeA I F]" +
      "[2 nodeB I F]" +
      "[3 nodeC I F]";
    TestProcess.validate( p, state );

    completeToken( p, "nodeB");

    state =
      "[1 nodeA I F]" +
      "[2 nodeB C F]" +
      "  (I F nodeD)" +
      "[3 nodeC I F]";
    TestProcess.validate( p, state );

    completeToken( p, "nodeC");

    state =
      "[1 nodeA I F]" +
      "[2 nodeB C F]" +
      "  (I F 4)" +
      "[3 nodeC C F]" +
      "  (I F 4)" +
      "[4 nodeD I F]";
    TestProcess.validate( p, state );

    completeToken( p, "nodeD");

    state =
      "[1 nodeA I F]" +
      "[2 nodeB C F]" +
      "  (C F 4)" +
      "[3 nodeC C F]" +
      "  (C F 4)" +
      "[4 nodeD C F]";
    TestProcess.validate( p, state );

    completeToken( p, "nodeA");

    state =
      "[1 nodeA C F]" +
      "  (C F 4)" +
      "[2 nodeB C F]" +
      "  (C F 4)" +
      "[3 nodeC C F]" +
      "  (C F 4)" +
      "[4 nodeD C F]";
    TestProcess.validate( p, state );

    Assert.assertTrue( "Process should be complete", p.isComplete() );
  }
}
