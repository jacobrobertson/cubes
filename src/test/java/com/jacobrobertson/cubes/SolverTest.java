package com.jacobrobertson.cubes;

import junit.framework.TestCase;

public class SolverTest extends TestCase {

	public void testSimple() {
		Solver solver = new Solver();
		Cube c = Cube.DEFAULT.rotate(Face.Front, true);
		
		History h = solver.solve(c);
		assertNotNull(h);
		assertEquals(1, h.getMovesCount());
		h.printHistory();
	}
	
	public void testShuffled() {
		doTestShuffled(1);
		doTestShuffled(2);
		doTestShuffled(10);
		doTestShuffled(20);
	}
	private void doTestShuffled(int count) {
		System.out.println("doTestShuffled." + count);
		Solver solver = new Solver();
		Cube c = Cube.DEFAULT.shuffle(count);
		History h = solver.solve(c);
		assertNotNull(h);
		assertTrue(h.getMovesCount() <= count);
		h.printHistory();
	}
	
	
}
