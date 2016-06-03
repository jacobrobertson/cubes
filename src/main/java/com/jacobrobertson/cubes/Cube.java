package com.jacobrobertson.cubes;

import static com.jacobrobertson.cubes.Face.*;

import java.util.Random;

public class Cube {

	public static final Cube DEFAULT = getDefaultCube();
	
	private Piece[][][] pieces = new Piece[3][3][3];
	private String keyString;

	/**
	 * Creates the default Cube.
	 */
	private Cube() {
	}
	private static Cube getDefaultCube() {
		Cube cube = new Cube();
		for (CubeSpec spec: CubeSpec.getCubeSpecs()) {
			Piece piece = Piece.piece(spec.key);
			Piece test = cube.pieces[spec.x][spec.y][spec.z];
			if (test != null) {
				throw new IllegalArgumentException("Bug in cube spec:" + test + " vs " + piece);
			}
			cube.pieces[spec.x][spec.y][spec.z] = piece;
		}
		cube.initKeyString();
		return cube;
	}
	private void initKeyString() {
		StringBuilder buf = new StringBuilder();
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				for (int z = 0; z < 3; z++) {
					Piece p = pieces[x][y][z];
					if (p != null) {
						char c = (char) p.getIdealColorKey();
						buf.append(c);
					}
				}
			}
		}
		this.keyString = buf.toString();
	}
	public Piece getPiece(int x, int y, int z) {
		return pieces[x][y][z];
	}
	public Cube rotate(Face face, boolean clockwise) {
		Cube r;
		switch (face) {
		case Back:
			r = rotateBack(clockwise);
			break;
		case Front:
			r = rotateFront(clockwise);
			break;
		case Right:
			r = rotateRight(clockwise);
			break;
		case Left:
			r = rotateLeft(clockwise);
			break;
		case Top:
			r = rotateTop(clockwise);
			break;
		case Under:
			r = rotateUnder(clockwise);
			break;
		default:
			throw new UnsupportedOperationException();
		}
		r.initKeyString();
		return r;
	}
	public Cube clone() {
		Cube c = new Cube();
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				for (int z = 0; z < 3; z++) {
					c.pieces[x][y][z] = this.pieces[x][y][z];
				}
			}
		}
		c.keyString = keyString;
		return c;
	}
	private Cube rotateFront(boolean clockwise) {
		Cube r = clone();
		
		r.set(this, 0, 0, 0, 2, 0, 0, 1, 0, 0, clockwise);
		r.set(this, 1, 0, 0, 2, 0, 1, 1, 0, 0, clockwise);
		r.set(this, 2, 0, 0, 2, 0, 2, 1, 0, 0, clockwise);

		r.set(this, 0, 0, 1, 1, 0, 0, 1, 0, 0, clockwise);
		r.set(this, 2, 0, 1, 1, 0, 2, 1, 0, 0, clockwise);

		r.set(this, 0, 0, 2, 0, 0, 0, 1, 0, 0, clockwise);
		r.set(this, 1, 0, 2, 0, 0, 1, 1, 0, 0, clockwise);
		r.set(this, 2, 0, 2, 0, 0, 2, 1, 0, 0, clockwise);
		
		return r;
	}
	private Cube rotateBack(boolean clockwise) {
		Cube r = clone();
		
		r.set(this, 0, 2, 0, 0, 2, 2, -1, 0, 0, clockwise);
		r.set(this, 1, 2, 0, 0, 2, 1, -1, 0, 0, clockwise);
		r.set(this, 2, 2, 0, 0, 2, 0, -1, 0, 0, clockwise);

		r.set(this, 0, 2, 1, 1, 2, 2, -1, 0, 0, clockwise);
		r.set(this, 2, 2, 1, 1, 2, 0, -1, 0, 0, clockwise);

		r.set(this, 0, 2, 2, 2, 2, 2, -1, 0, 0, clockwise);
		r.set(this, 1, 2, 2, 2, 2, 1, -1, 0, 0, clockwise);
		r.set(this, 2, 2, 2, 2, 2, 0, -1, 0, 0, clockwise);
		
		return r;
	}
	private Cube rotateRight(boolean clockwise) {
		Cube r = clone();
		
		r.set(this, 2, 0, 0, 2, 2, 0, 0, 1, 0, clockwise);
		r.set(this, 2, 0, 1, 2, 1, 0, 0, 1, 0, clockwise);
		r.set(this, 2, 0, 2, 2, 0, 0, 0, 1, 0, clockwise);

		r.set(this, 2, 1, 0, 2, 2, 1, 0, 1, 0, clockwise);
		r.set(this, 2, 1, 2, 2, 0, 1, 0, 1, 0, clockwise);

		r.set(this, 2, 2, 0, 2, 2, 2, 0, 1, 0, clockwise);
		r.set(this, 2, 2, 1, 2, 1, 2, 0, 1, 0, clockwise);
		r.set(this, 2, 2, 2, 2, 0, 2, 0, 1, 0, clockwise);
		
		return r;
	}
	private Cube rotateLeft(boolean clockwise) {
		Cube r = clone();
		
		r.set(this, 0, 0, 0, 0, 0, 2, 0, -1, 0, clockwise);
		r.set(this, 0, 1, 0, 0, 0, 1, 0, -1, 0, clockwise);
		r.set(this, 0, 2, 0, 0, 0, 0, 0, -1, 0, clockwise);

		r.set(this, 0, 0, 1, 0, 1, 2, 0, -1, 0, clockwise);
		r.set(this, 0, 2, 1, 0, 1, 0, 0, -1, 0, clockwise);

		r.set(this, 0, 0, 2, 0, 2, 2, 0, -1, 0, clockwise);
		r.set(this, 0, 1, 2, 0, 2, 1, 0, -1, 0, clockwise);
		r.set(this, 0, 2, 2, 0, 2, 0, 0, -1, 0, clockwise);
		
		return r;
	}
	private Cube rotateTop(boolean clockwise) {
		Cube r = clone();
		
		r.set(this, 0, 0, 0, 0, 2, 0, 0, 0, 1, clockwise);
		r.set(this, 0, 1, 0, 1, 2, 0, 0, 0, 1, clockwise);
		r.set(this, 0, 2, 0, 2, 2, 0, 0, 0, 1, clockwise);

		r.set(this, 1, 0, 0, 0, 1, 0, 0, 0, 1, clockwise);
		r.set(this, 1, 2, 0, 2, 1, 0, 0, 0, 1, clockwise);

		r.set(this, 2, 0, 0, 0, 0, 0, 0, 0, 1, clockwise);
		r.set(this, 2, 1, 0, 1, 0, 0, 0, 0, 1, clockwise);
		r.set(this, 2, 2, 0, 2, 0, 0, 0, 0, 1, clockwise);
		
		return r;
	}
	private Cube rotateUnder(boolean clockwise) {
		Cube r = clone();
		
		r.set(this, 0, 0, 2, 2, 0, 2, 0, 0, -1, clockwise);
		r.set(this, 1, 0, 2, 2, 1, 2, 0, 0, -1, clockwise);
		r.set(this, 2, 0, 2, 2, 2, 2, 0, 0, -1, clockwise);

		r.set(this, 0, 1, 2, 1, 0, 2, 0, 0, -1, clockwise);
		r.set(this, 2, 1, 2, 1, 2, 2, 0, 0, -1, clockwise);

		r.set(this, 0, 2, 2, 0, 0, 2, 0, 0, -1, clockwise);
		r.set(this, 1, 2, 2, 0, 1, 2, 0, 0, -1, clockwise);
		r.set(this, 2, 2, 2, 0, 2, 2, 0, 0, -1, clockwise);
		
		return r;
	}
	/**
	 * @param clockwise False indicates that the src/dest and rotate
	 * 					are in the opposite "direction"
	 */
	private void set(
			Cube src, int srcX, int srcY, int srcZ,
			int destX, int destY, int destZ,
			int xr, int yr, int zr, boolean clockwise) {
		if (clockwise) {
			Piece srcPiece = src.getPiece(srcX, srcY, srcZ);
			Piece rotatedPiece = srcPiece.rotate(xr, yr, zr);
			pieces[destX][destY][destZ] = rotatedPiece;
		} else {
			Piece srcPiece = src.getPiece(destX, destY, destZ);
			Piece rotatedPiece = srcPiece.rotate(-xr, -yr, -zr);
			pieces[srcX][srcY][srcZ] = rotatedPiece;
		}
	}
	private static final String PAD = "   ";
	public String toPrintString() {
		StringBuilder buf = new StringBuilder();
		
		buf.append(PAD);
		append(buf, 0, 2, 2, Back);
		append(buf, 1, 2, 2, Back);
		append(buf, 2, 2, 2, Back);
		buf.append(PAD);
		buf.append(PAD);
		buf.append("\n");
		buf.append(PAD);
		append(buf, 0, 2, 1, Back);
		append(buf, 1, 2, 1, Back);
		append(buf, 2, 2, 1, Back);
		buf.append(PAD);
		buf.append(PAD);
		buf.append("\n");
		buf.append(PAD);
		append(buf, 0, 2, 0, Back);
		append(buf, 1, 2, 0, Back);
		append(buf, 2, 2, 0, Back);
		buf.append(PAD);
		buf.append(PAD);
		buf.append("\n");
		append(buf, 0, 2, 2, Left);
		append(buf, 0, 2, 1, Left);
		append(buf, 0, 2, 0, Left);
		append(buf, 0, 2, 0, Top);
		append(buf, 1, 2, 0, Top);
		append(buf, 2, 2, 0, Top);
		append(buf, 2, 2, 0, Right);
		append(buf, 2, 2, 1, Right);
		append(buf, 2, 2, 2, Right);
		append(buf, 2, 2, 2, Under);
		append(buf, 1, 2, 2, Under);
		append(buf, 0, 2, 2, Under);
		buf.append("\n");
		append(buf, 0, 1, 2, Left);
		append(buf, 0, 1, 1, Left);
		append(buf, 0, 1, 0, Left);
		append(buf, 0, 1, 0, Top);
		append(buf, 1, 1, 0, Top);
		append(buf, 2, 1, 0, Top);
		append(buf, 2, 1, 0, Right);
		append(buf, 2, 1, 1, Right);
		append(buf, 2, 1, 2, Right);
		append(buf, 2, 1, 2, Under);
		append(buf, 1, 1, 2, Under);
		append(buf, 0, 1, 2, Under);
		buf.append("\n");
		append(buf, 0, 0, 2, Left);
		append(buf, 0, 0, 1, Left);
		append(buf, 0, 0, 0, Left);
		append(buf, 0, 0, 0, Top);
		append(buf, 1, 0, 0, Top);
		append(buf, 2, 0, 0, Top);
		append(buf, 2, 0, 0, Right);
		append(buf, 2, 0, 1, Right);
		append(buf, 2, 0, 2, Right);
		append(buf, 2, 0, 2, Under);
		append(buf, 1, 0, 2, Under);
		append(buf, 0, 0, 2, Under);
		buf.append("\n");
		buf.append(PAD);
		append(buf, 0, 0, 0, Front);
		append(buf, 1, 0, 0, Front);
		append(buf, 2, 0, 0, Front);
		buf.append(PAD);
		buf.append(PAD);
		buf.append("\n");
		buf.append(PAD);
		append(buf, 0, 0, 1, Front);
		append(buf, 1, 0, 1, Front);
		append(buf, 2, 0, 1, Front);
		buf.append(PAD);
		buf.append(PAD);
		buf.append("\n");
		buf.append(PAD);
		append(buf, 0, 0, 2, Front);
		append(buf, 1, 0, 2, Front);
		append(buf, 2, 0, 2, Front);
		buf.append(PAD);
		buf.append(PAD);
		
		return buf.toString();
	}
	private void append(StringBuilder buf, int x, int y, int z, Face face) {
		buf.append(pieces[x][y][z].getColor(face).symbol);
	}
	
	@Override
	public boolean equals(Object obj) {
		Cube that = (Cube) obj;
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				for (int z = 0; z < 3; z++) {
					Piece thisP = this.pieces[x][y][z];
					Piece thatP = that.pieces[x][y][z];
					if (thisP == thatP) {
						continue;
					}
					if (!thisP.equals(thatP)) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public String getKeyString() {
		return keyString;
	}
	
	public int distance(Cube that) {
		int distance = 0;
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				for (int z = 0; z < 3; z++) {
					Piece thisP = this.pieces[x][y][z];
					Piece thatP = that.pieces[x][y][z];
					if (thisP == thatP) {
						continue;
					}
					if (!thisP.equals(thatP)) {
						// TODO this is probably all I need to change
						distance++;
					}
				}
			}
		}
		return distance;
	}
	
	public Cube shuffle(int count) {
		Cube c = this;
		Random r = new Random();
		Face[] faces = Face.values();
		for (int i = 0; i < count; i++) {
			int facePos = r.nextInt(faces.length);
			boolean cw = r.nextBoolean();
			c = c.rotate(faces[facePos], cw);
		}
		return c;
	}
	
}
