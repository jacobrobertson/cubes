package com.jacobrobertson.cubes;

public class Piece {

	private char top;
	private char left;
	private char front;
	private char right;
	private char back;
	private char under;

	private Piece idealPiece;
	
	private int xRotations;
	private int yRotations;
	private int zRotations;
	
	public Piece(
			char top, char left, char front, char right, char back, char under) {
		this.top = top;
		this.left = left;
		this.front = front;
		this.right = right;
		this.back = back;
		this.under = under;
	}
	
	public Piece(char top, char left, char front, char right, char back,
			char under, Piece idealPiece, int xRotations, int yRotations,
			int zRotations) {
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
	}

	public char getTop() {
		return top;
	}

	public char getUnder() {
		return under;
	}

	public char getBack() {
		return back;
	}

	public char getLeft() {
		return left;
	}

	public char getRight() {
		return right;
	}

	public char getFront() {
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
