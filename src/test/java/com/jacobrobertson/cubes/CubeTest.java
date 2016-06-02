package com.jacobrobertson.cubes;

import junit.framework.TestCase;

public class CubeTest extends TestCase {

	public void testInit() {
		Cube c = new Cube();
		assertNotNull(c.getPiece(0, 0, 0));
	}
	
}
