package com.jacobrobertson.cubes;

public class Move {
	
	public final Face face;
	public final boolean clockwise;
	
	public Move(Face face, boolean clockwise) {
		this.face = face;
		this.clockwise = clockwise;
	}
	public String toString() {
		return face.name() + " " + (clockwise ? "cw" : "ccw");
	}
}