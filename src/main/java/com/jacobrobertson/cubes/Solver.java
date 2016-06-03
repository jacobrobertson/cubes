package com.jacobrobertson.cubes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;


public class Solver implements Comparator<History> {

	private PriorityQueue<History> q = new PriorityQueue<History>(1000, this);
	private Set<String> usedKeys = new HashSet<String>();
	
	private Cube target;
	
	public Solver() {
		this(Cube.DEFAULT);
	}
	public Solver(Cube target) {
		this.target = target;
	}
	
	public int compare(History o1, History o2) {
		int d1 = o1.getCube().distance(target);
		int d2 = o2.getCube().distance(target);
		return d1 - d2;
	}
	private List<History> generate(History parent) {
		List<History> history = new ArrayList<>();

		for (Face face: Face.values()) {
			generate(history, parent, face, true);
			generate(history, parent, face, false);
		}
		
		return history;
	}
	private void generate(List<History> history,
			History parent, Face face, boolean clockwise) {

		Move move = new Move(face, clockwise);
		Cube newCube = parent.getCube().rotate(face, clockwise);
		
		History child = new History(move, newCube, parent);
		
		history.add(child);
	}

	public History solve(Cube cube) {
		History root = new History(null, cube, null);
		q.add(root);
		return solve();
	}
	private History solve() {

		while (true) {
			
			// get next cube
			History h = q.poll();
			if (h == null) {
				return null;
			}
			
			// if it is a solution, you're done
			int distance = h.getCube().distance(target);
			if (distance == 0) {
				return h;
			}
			
			// else, generate all children, and repeat
			//	- do not add duplicate children
			List<History> children = generate(h);
			for (History child: children) {
				String key = child.getCube().getKeyString();
				if (usedKeys.add(key)) {
					q.add(child);
				}
			}
			
		}
	}
}
