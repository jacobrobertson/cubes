package com.jacobrobertson.cubes;

import junit.framework.TestCase;

public class PieceTest extends TestCase {

	public void testRotate() {
		for (Piece p: Piece.pieces()) {
			doTestRotate(p);
		}
	}
	public void doTestRotate(Piece p) {
		Piece r = p.rotate(0, 0, 0);
		assertSame(p, r);
		
		p = p.getIdealPiece();
		assertEquals(0, p.getXRotations());
		assertEquals(0, p.getYRotations());
		assertEquals(0, p.getZRotations());
		
		r = p.rotate(1, 0, 0);
		assertNotNull(r);
		assertEquals(1, r.getXRotations());
		
		r = r.rotate(-1, 0, 0);
		assertSame(p, r);
		
		r = r.rotate(0, 0, 1);
		assertEquals(1, r.getZRotations());
		r = r.rotate(0, 0, 1);
		assertEquals(2, r.getZRotations());
		r = r.rotate(0, 0, 1);
		assertEquals(3, r.getZRotations());
		r = r.rotate(0, 0, 1);
		assertEquals(0, r.getZRotations());
	}
	public void testLookup() {
		// top, left, front, right, back, under
		doTestLookup("WOG   ");
		doTestLookup("W G   ");
		doTestLookup("  G   ");
	}
	private void doTestLookup(String s) {
		Piece p = Piece.piece(s);
		assertNotNull(p);
	}
	
}
