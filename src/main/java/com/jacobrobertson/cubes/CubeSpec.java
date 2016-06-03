package com.jacobrobertson.cubes;

import java.util.ArrayList;
import java.util.List;

public class CubeSpec {

	private static List<CubeSpec> cubeSpecs = new ArrayList<CubeSpec>();
	static {
		
		// Top face
		add("WOG---", 0, 0, 0);
		add("W-G---", 1, 0, 0);
		add("W-GR--", 2, 0, 0);
		
		add("WO----", 0, 1, 0);
		add("W-----", 1, 1, 0);
		add("W--R--", 2, 1, 0);
		
		add("WO--B-", 0, 2, 0);
		add("W---B-", 1, 2, 0);
		add("W--RB-", 2, 2, 0);
		
		// Front face
		add("-OG---", 0, 0, 1);
		add("--G---", 1, 0, 1);
		add("--GR--", 2, 0, 1);

		add("-OG--Y", 0, 0, 2);
		add("--G--Y", 1, 0, 2);
		add("--GR-Y", 2, 0, 2);

		// Left face
		add("-O----", 0, 1, 1);
		add("-O---Y", 0, 1, 2);
		add("-O--B-", 0, 2, 1);
		add("-O--BY", 0, 2, 2);

		// Right face
		add("---R--", 2, 1, 1);
		add("---RB-", 2, 2, 1);
		add("---RBY", 2, 2, 2);
		add("---R-Y", 2, 1, 2);

		// Back face
		add("----BY", 1, 2, 2);
		add("----B-", 1, 2, 1);

		// bottom face
		add("-----Y", 1, 1, 2);

	}
	private static void add(String key, int x, int y, int z) {
		CubeSpec c = new CubeSpec(x, y, z, key);
		cubeSpecs.add(c);
	}
	public static List<CubeSpec> getCubeSpecs() {
		return cubeSpecs;
	}
	
	
	public final int x;
	public final int y;
	public final int z;
	public final String key;
	public CubeSpec(int x, int y, int z, String key) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.key = key;
	}
	
}
