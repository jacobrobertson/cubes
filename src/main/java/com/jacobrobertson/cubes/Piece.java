package com.jacobrobertson.cubes;

import java.util.HashMap;
import java.util.Map;

public class Piece {

	private static Map<Integer, Piece> uniqueByKey = new HashMap<Integer, Piece>();
	private static Map<Integer, Piece> idealByColorKeyPart = new HashMap<Integer, Piece>();
	private static Map<Integer, Piece> canonicalByRotatedColorKeyPart = new HashMap<Integer, Piece>();
	
	static {
		init();
	}

	private static void init() {
		for (CubeSpec spec: CubeSpec.getCubeSpecs()) {
			Color[] c = Color.parseColors(spec.key);
			init(c[0], c[1], c[2], c[3], c[4], c[5]);
		}
	}
	private static void init(Color top, Color left, Color front, Color right, Color back, Color under) {
		Piece ideal = init(top, left, front, right, back, under, null, 0, 0, 0);
		Piece px = ideal;
		for (int x = 1; x < 4; x++) {
			//           top,     left,     front,    right,  back,    under
			px = init(px.left, px.under, px.front, px.top, px.back, px.right, ideal, x, 0, 0);
			Piece py = px;
			for (int y = 1; y < 4; y++) {
				//           top,      left,    front,    right,    back,   under
				py = init(py.front, py.left, py.under, py.right, py.top, py.back, ideal, py.xRotations, y, 0);
				Piece pz = py;
				for (int z = 1; z < 4; z++) {
					//           top,    left,     front,    right,   back,    under
					pz = init(pz.top, pz.front, pz.right, pz.back, pz.left, pz.under, ideal, pz.xRotations, pz.yRotations, z);
				}
			}
		}
	}
	private static Piece init(Color top, Color left, Color front, Color right, Color back, Color under,
			Piece idealPiece, 
			int xRotations, int yRotations, int zRotations) {

		int rotatedColorKey = buildColorKey(true, top, left, front, right, back, under);
		Piece p = new Piece(top, left, front, right, back, under, idealPiece, rotatedColorKey, xRotations, yRotations, zRotations);

		if (p.toLookupKeyString().equals("GW--R-")) {
			System.out.println("here");
		}
		
		Piece existing = uniqueByKey.put(p.uniqueKey, p);
		if (existing != null) {
			throw new IllegalArgumentException("Bug in init logic");
		}
		
		if (xRotations == 0 && yRotations == 0 && zRotations == 0) {
			idealByColorKeyPart.put(p.idealColorKey, p);
		}
		
		// add to canonical if it is the first
		if (!canonicalByRotatedColorKeyPart.containsKey(rotatedColorKey)) {
			canonicalByRotatedColorKeyPart.put(rotatedColorKey, p);
		}
		
		System.out.println(p.toString());
		
		return p;
	}
	
	private Color top;
	private Color left;
	private Color front;
	private Color right;
	private Color back;
	private Color under;

	private int xRotations;
	private int yRotations;
	private int zRotations;
	
	// used to construct a unique search key during rotation
	// can also be used to lookup the "ideal" piece
	private int idealColorKey; 
	private Integer uniqueKey; // identifies this piece only
	
	// color key in the correct rotation
	private Integer rotatedColorKey;
	
	private Piece(Color top, Color left, Color front, Color right, Color back, Color under, 
			Piece idealPiece, int rotatedColorKey, 
			int xRotations, int yRotations, int zRotations) {
		this.top = top;
		this.left = left;
		this.front = front;
		this.right = right;
		this.back = back;
		this.under = under;
		this.xRotations = xRotations;
		this.yRotations = yRotations;
		this.zRotations = zRotations;
		
		if (idealPiece == null) {
			// we are configuring an ideal piece, so we need the color key
			this.idealColorKey = buildColorKey(false, this);
		} else {
			// the color key is not unique - it points to the ideal piece's key
			this.idealColorKey = idealPiece.idealColorKey;
		}
		
		int rotationKey = buildRotationKey(xRotations, yRotations, zRotations);
		this.uniqueKey = rotationKey + idealColorKey;
		this.rotatedColorKey = rotatedColorKey;
	}
	/**
	 * @return the "ideal" pieces only
	 */
	public static Iterable<Piece> getIdealPieces() {
		return idealByColorKeyPart.values();
	}
	/**
	 * parse key in the order of top, left, front, right, back, under
	 * For example, "WO B  "
	 */
	public static Piece piece(String parseKey) {
		Color[] colors = Color.parseColors(parseKey);
		int colorKey = buildColorKey(true, colors);
		return canonicalByRotatedColorKeyPart.get(colorKey);
	}
	private static int buildRotationKey(int x, int y, int z) {
		// XYZ123456
		return
			100000000 * x + 
			10000000 * y + 
			1000000 * z;
	}
	private static int buildColorKey(boolean includeBlank, Piece p) {
		return buildColorKey(includeBlank, p.top, p.left, p.front, p.right, p.back, p.under);
	}
	private static int buildColorKey(boolean includeBlank, Color... c) {
		int key = 0;
		int pos = 0;
		for (int i = 0; i < c.length; i++) {
			if (!includeBlank && c[i] == Color.Blank) {
				continue;
			}
			key += getColorKeyPart(c[i], pos);
			pos++;
		}
		return key;
	}
	private static int getColorKeyPart(Color c, int pos) {
		int factor = (int) Math.pow(10, pos);
		return factor * c.ordinal();
	}

	/**
	 * Only one arg can be non-zero and only valid values are -1, 0, 1
	 */
	public Piece rotate(int x, int y, int z) {
		int rotationKey = buildRotationKey(
				incr(x, xRotations), 
				incr(y, yRotations), 
				incr(z, zRotations));
		int uniqueSearchKey = rotationKey + idealColorKey;
		Piece rotated = uniqueByKey.get(uniqueSearchKey);
		if (rotated == null) {
			throw new IllegalStateException("Bug in rotate: " + uniqueSearchKey);
		}
		Piece canonical = canonicalByRotatedColorKeyPart.get(rotated.rotatedColorKey);
		return canonical;
	}
	private int incr(int n, int e) {
		n = n + e;
		if (n == -1) {
			n = 3;
		} else if (n == 4) {
			n = 0;
		}
		return n;
	}
	
	public Color getTop() {
		return top;
	}

	public Color getUnder() {
		return under;
	}

	public Color getBack() {
		return back;
	}

	public Color getLeft() {
		return left;
	}

	public Color getRight() {
		return right;
	}

	public Color getFront() {
		return front;
	}

	public int getXRotations() {
		return xRotations;
	}

	public int getYRotations() {
		return yRotations;
	}

	public int getZRotations() {
		return zRotations;
	}
	
	public int getIdealColorKey() {
		return idealColorKey;
	}
	public Piece getIdealPiece() {
		return idealByColorKeyPart.get(idealColorKey);
	}
	
	public Color getColor(Face face) {
		switch (face) {
		case Back:
			return getBack();
		case Top:
			return getTop();
		case Front:
			return getFront();
		case Under:
			return getUnder();
		case Left:
			return getLeft();
		case Right:
			return getRight();
		}
		throw new IllegalArgumentException();
	}

	@Override
	public boolean equals(Object obj) {
		// we are using flyweight, so we never will have
		// anything but exact matches
		return this == obj; 
	}

	
	
	@Override
	public String toString() {
		return "Piece (" + toLookupKeyString() + ") [uniqueKey=" + uniqueKey + ", top=" + top.symbol + ", left="
				+ left.symbol + ", front=" + front.symbol + ", right=" + right.symbol + ", back="
				+ back.symbol + ", under=" + under.symbol + ", xyz(" + xRotations
				+ "," + yRotations + "," + zRotations
				+ "), idealColorKey=" + idealColorKey + ", rotatedColorKey="
				+ rotatedColorKey + "]";
	}
	public int getUniqueKey() {
		return uniqueKey;
	}
	/**
	 * Represents the 6 sides uniquely without respect to the x/y/z rotation
	 * used to get there.
	 */
	public Integer getRotatedColorKey() {
		return rotatedColorKey;
	}
	public String toLookupKeyString() {
		// top, left, front, right, back, under
		return new String(new char[] {
			top.symbol, left.symbol, front.symbol, right.symbol, back.symbol, under.symbol
			});
	}
}
