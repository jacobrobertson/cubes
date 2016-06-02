package com.jacobrobertson.cubes;

import java.util.HashMap;
import java.util.Map;

public class Piece {

	private static Map<Integer, Piece> flyweight = new HashMap<Integer, Piece>();
	
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
		
		Piece ideal = new Piece(top, left, front, right, back, under);
		flyweight.put(ideal.rotationKey, ideal);
		Piece px = null;
		
		for (int x = 0; x < 4; x++) {
			if (px == null) {
				px = ideal;
			} else {
				//           top,     left,     front,    right,  back,    under
				px = init(px.left, px.under, px.front, px.top, px.back, px.right, ideal, x, 0, 0);
			}
			Piece py = null;
			for (int y = 0; y < 4; y++) {
				if (py == null) {
					py = px;
				} else {
					//           top,      left,    front,    right,    back,   under
					py = init(py.front, py.left, py.under, py.right, py.top, py.back, ideal, py.xRotations, y, 0);
				}
				Piece pz = null;
				for (int z = 0; z < 4; z++) {
					if (pz == null) {
						pz = py;
					} else {
						//           top,    left,     front,    right,   back,    under
						pz = init(pz.top, pz.front, pz.right, pz.back, pz.left, pz.under, ideal, pz.xRotations, pz.yRotations, z);
					}
				}
			}
		}
	}
	private static Piece init(Color top, Color left, Color front, Color right, Color back, Color under,
			Piece idealPiece, int xRotations, int yRotations, int zRotations) {
		Piece p = new Piece(top, left, front, right, back, under, idealPiece, xRotations, yRotations, zRotations);
//		System.out.println(p.key);

		// always put in by rotation key for lookup, but that key should only exist once
		Piece existing = flyweight.put(p.rotationKey, p);
		if (existing != null) {
			throw new IllegalArgumentException("Bug in init logic");
		}
		
		// check if it exists for true color key
		existing = flyweight.get(p.colorKey);
		if (existing == null) {
			flyweight.put(p.colorKey, p);
		}
		
		return p;
	}
	
	private Color top;
	private Color left;
	private Color front;
	private Color right;
	private Color back;
	private Color under;

	private Piece idealPiece;
	
	private int xRotations;
	private int yRotations;
	private int zRotations;
	
	private int colorKey;
	private Integer rotationKey;
	
	private Piece(
			Color top, Color left, Color front, Color right, Color back, Color under) {
		this(top, left, front, right, back, under, null, 0, 0, 0);
	}
	
	private Piece(Color top, Color left, Color front, Color right, Color back, Color under, 
			Piece idealPiece, int xRotations, int yRotations, int zRotations) {
		this.top = top;
		this.left = left;
		this.front = front;
		this.right = right;
		this.back = back;
		this.under = under;
		this.idealPiece = idealPiece;
		this.xRotations = xRotations;
		this.yRotations = yRotations;
		this.zRotations = zRotations;
		
		rotationKey = getRotationKey(xRotations, yRotations, zRotations);
		colorKey = getColorKey();
		if (idealPiece != null) {
			rotationKey += idealPiece.colorKey;
		} else {
			rotationKey += colorKey;
			this.idealPiece = this;
		}
	}
	/**
	 * DO NOT ALTER.
	 */
	public static Iterable<Piece> pieces() {
		return flyweight.values();
	}
	/**
	 * parse key in the order of top, left, front, right, back, under
	 * For example, "WO B  "
	 */
	public static Piece piece(String parseKey) {
		Color[] colors = Color.parseColors(parseKey);
		int colorKey = getColorKey(colors);
		return flyweight.get(colorKey);
	}
	private static int getRotationKey(int x, int y, int z) {
		return
			1000000 * x + 
			10000000 * y + 
			100000000 * z;
	}
	private int getColorKey() {
		return getColorKey(top, left, front, right, back, under);
	}
	private static int getColorKey(Color... colors) {
		return 
			1 * colors[0].ordinal() + 
			10 * colors[1].ordinal() + 
			100 * colors[2].ordinal() + 
			1000 * colors[3].ordinal() + 
			10000 * colors[4].ordinal() + 
			100000 * colors[5].ordinal(); 
	}

	/**
	 * Only one arg can be non-zero and only valid values are -1, 0, 1
	 */
	public Piece rotate(int x, int y, int z) {
		int findKey = idealPiece.colorKey + getRotationKey(
				incr(x, xRotations), 
				incr(y, yRotations), 
				incr(z, zRotations));
		return flyweight.get(findKey);
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

	public Piece getIdealPiece() {
		return idealPiece;
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
		Piece that = (Piece) obj;
		return this.rotationKey.equals(that.rotationKey);
	}
	
	@Override
	public String toString() {
		return "Piece [top=" + top + ", left=" + left + ", front=" + front
				+ ", right=" + right + ", back=" + back + ", under=" + under
				+ ", xRotations=" + xRotations + ", yRotations=" + yRotations
				+ ", zRotations=" + zRotations + "]";
	}
	
	
}
