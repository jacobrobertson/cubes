package com.jacobrobertson.cubes;

import junit.framework.TestCase;

public class PieceTest extends TestCase {

	public void testSimple() {
		// top, left, front, right, back, under
		Piece p0 = Piece.piece("W-----");
		p0 = doTestRotate(p0, 0, 0, 0, "W-----");
		//                                     TLFRBU
		Piece p1 = doTestRotate(p0, 0, 1, 0,  "----W-");
		Piece p2 = doTestRotate(p1, -1, 0, 0, "----W-");
		
		assertNotNull(p2);
	}
	public void testSimple2() {
		Piece p0 = Piece.piece("W-GR--");
		//                              TLFRBU
		p0 = doTestRotate(p0, 0, 0, 0, "W-GR--");
		p0 = doTestRotate(p0, 1, 0, 0, "--GW-R");
		p0 = doTestRotate(p0, 1, 0, 0, "-RG--W");
		p0 = doTestRotate(p0, 1, 0, 0, "RWG---");
		p0 = doTestRotate(p0, 1, 0, 0, "W-GR--");

		p0 = doTestRotate(p0, -1, 0, 0, "RWG---");
	}
	public void testSimple3() {
		Piece p0 = Piece.piece("RWG---");
		//                              TLFRBU
		p0 = doTestRotate(p0, 0, 0, 0, "W-GR--");
		p0 = doTestRotate(p0, 0, 1, 0, "GW--R-");
	}
	private Piece doTestRotate(Piece p, int x, int y, int z, String expect) {
		Piece r = p.rotate(x, y, z);
		assertEquals(expect, r.toLookupKeyString());
		return r;
	}
	
	public void testRotateBugs() {
		// top, left, front, right, back, under
		// this represents the top front right corner
		Piece p0 = Piece.piece("W-GR--");
		doTestRotate(p0, 0, 0, 0, "W-GR--");
		// this represents moving it to the top back right corner
		Piece p1 = doTestRotate(p0, 0, 1, 0, "G--RW-");
		
		// this represents moving it to the top back left corner
		Piece p2 = doTestRotate(p1, -1, 0, 0, "RG--W-");
		// actual:   "GW--R-"
		
		assertNotNull(p2);
	}
	
	public void testRotate() {
		for (Piece p: Piece.getIdealPieces()) {
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
		// these are different because of the canonical representation
		assertEquals(0, r.getXRotations());
		assertEquals(1, r.getYRotations());
		assertEquals(1, r.getZRotations());
		
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
		doTestLookup("WOG---");
		doTestLookup("W-G---");
		doTestLookup("--G---");
	}
	private void doTestLookup(String s) {
		Piece p = Piece.piece(s);
		assertNotNull(p);
	}
	
}
