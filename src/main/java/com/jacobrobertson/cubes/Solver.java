package com.jacobrobertson.cubes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;


public class Solver implements Comparator<Solver.History> {

	private PriorityQueue<History> q = new PriorityQueue<History>(1000, this);
	private Set<String> usedKeys = new HashSet<String>();
	
	public int compare(History o1, History o2) {
		return 0;
	}
	private List<Cube> generate(Cube cube) {
		return null;
	}

	public void solve() {
		
		// get next cube
		
		// if it is a solution, you're done
		
		// else, generate all children, and repeat
		//	- do not add duplicate children
		
	}
	private class Move {
		Face face;
		boolean clockwise;
	}
	public static class History {
		List<Move> moves = new ArrayList<Move>();
		Cube current;
	}
}
