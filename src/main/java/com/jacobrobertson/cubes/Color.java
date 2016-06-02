package com.jacobrobertson.cubes;

public enum Color {

	
	White('W'),
	Green('G'),
	Blue('B'),
	Red('R'),
	Yellow('Y'),
	Orange('O'),
	Blank(' '),
	Gray('.'),
	
	;
	
	final char symbol;

	private Color(char symbol) {
		this.symbol = symbol;
	}
	public static Color forSymbol(char s) {
		for (Color color: values()) {
			if (color.symbol == s) {
				return color;
			}
		}
		throw new IllegalArgumentException("Invalid symbol " + s);
	}
	public static Color[] parseColors(String parseKey) {
		Color[] colors = new Color[6];
		for (int i = 0; i < 6; i++) {
			char c = parseKey.charAt(i);
			colors[i] = Color.forSymbol(c);
		}
		return colors;
	}
	
}
