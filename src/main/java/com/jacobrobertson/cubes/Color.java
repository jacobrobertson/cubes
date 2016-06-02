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
	
	
}
