package com.jacobrobertson.cubes;

public class Cube {

	private Piece[][][] pieces = new Piece[3][3][3];
	
	/**
	 * Creates the default Cube.
	 */
	public Cube() {
		for (CubeSpec spec: CubeSpec.getCubeSpecs()) {
			Piece piece = Piece.piece(spec.key);
			Piece test = pieces[spec.x][spec.y][spec.z];
			if (test != null) {
				throw new IllegalArgumentException("Bug in cube spec:" + test + " vs " + piece);
			}
			pieces[spec.x][spec.y][spec.z] = piece;
		}
	}
	
	public Piece getPiece(int x, int y, int z) {
		return pieces[x][y][z];
	}
	
}
