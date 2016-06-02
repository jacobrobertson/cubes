package com.jacobrobertson.cubes;

import junit.framework.TestCase;

public class PieceTest extends TestCase {

	public void testRotate() {
		Piece p = Piece.any();
		Piece r = p.rotate(0, 0, 0);
		assertSame(p, r);
		
		p = p.getIdealPiece();
		r = p.rotate(1, 0, 0);
		assertNotNull(r);
		
		assertEquals(1, r.getXRotations());
	}
	
}
