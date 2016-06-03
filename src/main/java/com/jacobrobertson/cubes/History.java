package com.jacobrobertson.cubes;

public class History {
	private Move move;
	private Cube cube;
	private History parent;
	
	public History(Move move, Cube cube, History parent) {
		this.move = move;
		this.cube = cube;
		this.parent = parent;
	}
	public Move getMove() {
		return move;
	}
	public Cube getCube() {
		return cube;
	}
	public History getParent() {
		return parent;
	}
	public int getMovesCount() {
		if (parent == null) {
			return 0;
		}
		return 1 + parent.getMovesCount();
	}
	public void printHistory() {
		if (parent != null) {
			parent.printHistory();
			System.out.print(move.toString());
			System.out.println(" ==> results in this new cube:");
		} else {
			System.out.println("We start with this cube:");
		}
		System.out.println(cube.toPrintString());
	}
	
}