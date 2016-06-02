package com.jacobrobertson.cubes;

import junit.framework.TestCase;
import static com.jacobrobertson.cubes.Face.*;

public class CubeTest extends TestCase {

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
		System.out.println(c.toPrintString());
		System.out.println();
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
		System.out.println(r.toPrintString());
		System.out.println();
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
		System.out.println(r2.toPrintString());
		System.out.println();
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
	}
	
}
