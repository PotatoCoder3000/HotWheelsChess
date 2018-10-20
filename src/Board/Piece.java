package Board;

import java.util.ArrayList;

import Game.Move;

public abstract class Piece {
	private int X, Y;
	private int value;
	private char name;
	private String team;

	public Piece(final String team, int value, int X, int Y) {
		this.team = team;
		this.value = value;
		this.X = X;
		this.Y = Y;
	}

	public abstract ArrayList<Move> moveCalculator(Board board);

	public void setX(int X) {
		this.X = X;
	}

	public void setY(int Y) {
		this.Y = Y;
	}

	public int getX() {
		return this.X;
	}

	public int getY() {
		return this.Y;
	}

	public void setName(char name) {
		this.name = name;
	}

	public char getName() {
		return name;
	}

	public String getTeam() {
		return this.team;
	}

	public int getValue() {
		return this.value;
	}
}