package com.jacobrobertson.cubes;

import java.util.HashMap;
import java.util.Map;

import static com.jacobrobertson.cubes.Color.*;

public class Piece {

	private static Map<Integer, Piece> flyweight = new HashMap<Integer, Piece>();
	
	static {
		init();
	}

	// TODO this init should go elsewhere so we can assign the position at the same time and don't have 
	//		to duplicate the color layout
	private static void init() {
		
		// generate all base pieces
		//   top,   left,   front, right, back,  under
		init(White, Orange, Green, Blank, Blank, Blank);
		init(White, Blank,  Green, Blank, Blank, Blank);
		init(White, Blank,  Green, Red,   Blank, Blank);
		
		init(White, Orange, Blank, Blank, Blank, Blank);
		init(White, Blank,  Blank, Blank, Blank, Blank);
		init(White, Blank,  Blank, Red,   Blank, Blank);
		
		init(White, Orange, Blank, Blank, Blue,  Blank);
		init(White, Blank,  Blank, Blank, Blue,  Blank);
		init(White, Blank,  Blank, Red,   Blue,  Blank);
		
		init(Blank, Orange, Green, Blank, Blank, Blank);
		init(Blank, Blank,  Green, Blank, Blank, Blank);
		init(Blank, Blank,  Green, Red,   Blank, Blank);

		init(Blank, Orange, Blank, Blank, Blank, Blank);
		init(Blank, Blank,  Blank, Red,   Blank, Blank);
		
		init(Blank, Orange, Blank, Blank, Blue,  Blank);
		init(Blank, Blank,  Blank, Blank, Blue,  Blank);
		init(Blank, Blank,  Blank, Red,   Blue,  Blank);
		
		init(Blank, Orange, Green, Blank, Blank, Yellow);
		init(Blank, Blank,  Green, Blank, Blank, Yellow);
		init(Blank, Blank,  Green, Red, Blank,   Yellow);
		
		init(Blank, Orange, Blank, Blank, Blank, Yellow);
		init(Blank, Blank,  Blank, Red,   Blank, Yellow);
		
		init(Blank, Orange, Blank, Blank, Blue,  Yellow);
		init(Blank, Blank,  Blank, Blank, Blue,  Yellow);
		init(Blank, Blank,  Blank, Red,   Blue,  Yellow);

		init(Blank, Blank,  Blank, Blank, Blank,  Yellow);
	}
	
	private static void init(Color top, Color left, Color front, Color right, Color back, Color under) {
		
		Piece p = new Piece(top, left, front, right, back, under);
//		flyweight.put(p.key, p);
		
		// TODO generate each "child"
		
		for (int x = 0; x < 3; x++) {
			Piece px = init(p.left, p.under, p.front, p.top, p.back, p.right, p, x, 0, 0);
			flyweight.put(px.key, px);
			for (int y = 0; y < 3; y++) {
				Piece py = init(px.left, px.under, px.front, px.top, px.back, px.right, px, px.xRotations, y, 0);
				flyweight.put(py.key, py);
				for (int z = 0; z < 3; z++) {
					init(p.left, p.under, p.front, p.top, p.back, p.right, p, x, 0, 0);
				}
			}
			p = px;
		}
		
		//   top, left, front, right, back, under
//		p = init(p.left, p.under, p.front, p.top, p.back, p.right, p, 1, 0, 0);
//		p = init(p.left, p.under, p.front, p.top, p.back, p.right, p, 2, 0, 0);
//		p = init(p.left, p.under, p.front, p.top, p.back, p.right, p, 3, 0, 0);
		
	}
	private static Piece init(Color top, Color left, Color front, Color right, Color back, Color under,
			Piece idealPiece, int xRotations, int yRotations, int zRotations) {
		Piece p = new Piece(top, left, front, right, back, under, idealPiece, xRotations, yRotations, zRotations);
		flyweight.put(p.key, p);
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
	private Integer key;
	
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
		this.yRotations = zRotations;
		
		key = getRotationKey(xRotations, yRotations, zRotations);
		if (idealPiece != null) {
			this.colorKey = idealPiece.colorKey;
			key += idealPiece.colorKey;
		} else {
			this.colorKey = getColorKey();
			key += colorKey;
		}
	}
	public static Piece any() {
		Integer key = flyweight.keySet().iterator().next();
		return flyweight.get(key);
	}
	private static int getRotationKey(int x, int y, int z) {
		return
			1000000 * x + 
			10000000 * y + 
			100000000 * z;
	}
	private int getColorKey() {
		return 
			1 * top.ordinal() + 
			10 * left.ordinal() + 
			100 * front.ordinal() + 
			1000 * right.ordinal() + 
			10000 * back.ordinal() + 
			100000 * under.ordinal(); 
	}

	/**
	 * Only one arg can be non-zero and only valid values are -1, 0, 1
	 */
	public Piece rotate(int x, int y, int z) {
		int testKey = colorKey + getRotationKey(
				incr(x, xRotations), 
				incr(y, yRotations), 
				incr(z, zRotations));
		return flyweight.get(testKey);
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
	
}
