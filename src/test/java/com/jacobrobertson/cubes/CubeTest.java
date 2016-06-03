package com.jacobrobertson.cubes;

import java.util.Random;

import junit.framework.TestCase;
import static com.jacobrobertson.cubes.Face.*;

public class CubeTest extends TestCase {

	public void testKeyString() {
		Cube c = Cube.DEFAULT;
		assertNotNull(c.getKeyString());
		Cube r = c.rotate(Front, true);
		assertFalse(c.getKeyString().equals(r.getKeyString()));
	}
	public void testInit() {
		Cube c = Cube.DEFAULT;
		assertNotNull(c.getPiece(0, 0, 0));
	}
	
	public void testRotate() {
		Cube c = Cube.DEFAULT;
		Cube r = c.rotate(Front, true);
		assertNotSame(c, r);
	}
	public void testPrintString() {
		Cube c = Cube.DEFAULT;
//		System.out.println(c.toPrintString());
//		System.out.println();
		assertEquals(
						"   BBB      \n" +
						"   BBB      \n" +
						"   BBB      \n" +
						"OOOWWWRRRYYY\n" +
						"OOOWWWRRRYYY\n" +
						"OOOWWWRRRYYY\n" +
						"   GGG      \n" +
						"   GGG      \n" +
						"   GGG      "
						,
				c.toPrintString());
		Cube r = c.rotate(Front, true);
//		System.out.println(r.toPrintString());
//		System.out.println();
		assertEquals(
				"   BBB      \n" +
				"   BBB      \n" +
				"   BBB      \n" +
				"OOOWWWRRRYYY\n" +
				"OOOWWWRRRYYY\n" +
				"YYYOOOWWWRRR\n" +
				"   GGG      \n" +
				"   GGG      \n" +
				"   GGG      "
				,
				r.toPrintString());
		Cube r2 = r.rotate(Right, true);
//		System.out.println(r2.toPrintString());
//		System.out.println();
		assertEquals(
				"   BBW      \n" +
				"   BBW      \n" +
				"   BBO      \n" +
				"OOOWWGWRRBYY\n" +
				"OOOWWGWRRBYY\n" +
				"YYYOOGWRRBRR\n" +
				"   GGR      \n" +
				"   GGY      \n" +
				"   GGY      "
				,
				r2.toPrintString());
		
		Cube r3 = r.rotate(Front, false);
		assertEquals(c, r3);
		
		Cube r4 = c.rotate(Top, true);
//		System.out.println(r4.toPrintString());
//		System.out.println();
		assertEquals(
				"   BBB      \n" +
				"   BBB      \n" +
				"   OOO      \n" +
				"OOGWWWBRRYYY\n" +
				"OOGWWWBRRYYY\n" +
				"OOGWWWBRRYYY\n" +
				"   RRR      \n" +
				"   GGG      \n" +
				"   GGG      "
				,
				r4.toPrintString());
		
		Cube r5 = c.rotate(Back, true);
//		System.out.println(r5.toPrintString());
//		System.out.println();
		assertEquals(
				"   BBB      \n" +
				"   BBB      \n" +
				"   BBB      \n" +
				"WWWRRRYYYOOO\n" +
				"OOOWWWRRRYYY\n" +
				"OOOWWWRRRYYY\n" +
				"   GGG      \n" +
				"   GGG      \n" +
				"   GGG      "
				,
				r5.toPrintString());
		
		Cube r6 = c.rotate(Left, true);
//		System.out.println(r6.toPrintString());
//		System.out.println();
		assertEquals(
				"   YBB      \n" +
				"   YBB      \n" +
				"   YBB      \n" +
				"OOOBWWRRRYYG\n" +
				"OOOBWWRRRYYG\n" +
				"OOOBWWRRRYYG\n" +
				"   WGG      \n" +
				"   WGG      \n" +
				"   WGG      "
				,
				r6.toPrintString());
		
		Cube r7 = c.rotate(Under, true);
//		System.out.println(r7.toPrintString());
//		System.out.println();
		assertEquals(
				"   RRR      \n" +
				"   BBB      \n" +
				"   BBB      \n" +
				"BOOWWWRRGYYY\n" +
				"BOOWWWRRGYYY\n" +
				"BOOWWWRRGYYY\n" +
				"   GGG      \n" +
				"   GGG      \n" +
				"   OOO      "
				,
				r7.toPrintString());
	}
	public void testRotate4() {
		Cube c = Cube.DEFAULT;
		
		doTestRotate4(c);
		doTestRotate4(c.rotate(Front, true));
	}
	private void doTestRotate4(Cube orig) {
		Cube cw = orig;
		Cube ccw = orig;
		for (Face face: Face.values()) {
			for (int i = 0; i < 4; i++) {
				cw = cw.rotate(face, true);
				ccw = ccw.rotate(face, false);
			}
			assertEquals(orig.toPrintString() + "\n vs \n" + cw.toPrintString(), orig, cw);
			assertEquals(orig, ccw);
		}
	}
	public void testRotateBugs1() {
		Cube c = Cube.DEFAULT;
		c = rotate(c, Back, true);
		c = rotate(c, Top, true);
		c = rotate(c, Left, true);
	}
	public void testRotateBugs2() {
		Cube c = Cube.DEFAULT;
		System.out.println(c.toPrintString() + "\n===============");
		c = rotate(c, Right, true, true);
		c = rotate(c, Back, true, true);
	}
	public void testRotateBugs3() {
		Cube c = Cube.DEFAULT;
		c = rotate(c, Back, false);
		c = rotate(c, Under, false);
		c = rotate(c, Back, false);
	}
	public void testRotateBugs4() {
		Cube c = Cube.DEFAULT;
		c = rotate(c, Right, false);
		c = rotate(c, Top, true);
		c = rotate(c, Right, true);
	}
	public void testRotateBugs5() {
		Cube c = Cube.DEFAULT;
		c = rotate(c, Left, false);
		c = rotate(c, Back, true);
	}
	private Cube rotate(Cube c, Face f, boolean cw) {
		return rotate(c, f, cw, false);
	}
	private Cube rotate(Cube c, Face f, boolean cw, boolean print) {
		c = c.rotate(f, cw);
		if (print) {
			System.out.println(c.toPrintString() + "\n====================");
		}
		assertCubePrintOkay(c);
		return c;
	}
	public void testUncoverRotateBugs() {
		System.out.println("\tpublic void testRotateBugsX() {");
		System.out.println("\t\tCube c = Cube.DEFAULT;");
		int count = 100;
		Cube c = Cube.DEFAULT;
		Random rand = new Random();
		Face[] faces = Face.values();
		for (int i = 0; i < count; i++) {
			int facePos = rand.nextInt(faces.length);
			boolean cw = rand.nextBoolean();
			Face f = faces[facePos];
			c = c.rotate(faces[facePos], cw);
//			System.out.println(new Move(faces[facePos], cw).toString());
//			System.out.println(c.toPrintString());
//			System.out.println(i + "===========");
			System.out.println("\t\tc = rotate(c, " + f.name() + ", " + cw + ");");
			assertCubePrintOkay(c);
		}
	}
	private void assertCubePrintOkay(Cube c) {
		String s = c.toPrintString();
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			String sub = s.substring(i, i + 1).trim();
			count += sub.length();
		}
		assertEquals(54, count);
	}
	
}
