package Pieces;

import java.util.ArrayList;

import Board.Board;
import Board.Piece;
import Board.Point;
import Board.Tile;
import Game.Move;

public class Car extends Piece {
	private final static int[] xMoves = { -1, 0, 1 };
	private final static int[] yMoves = { -1, 0, 1 };
	private String trackType;

	public Car(final String team, int X, int Y) {
		super(team, 0, X, Y);
		char name;
		if (team.toUpperCase().equals("WHITE")) {
			name = 'c';
			trackType = "WHITE";
		} else {
			name = 'C';
			trackType = "BLACK";
		}
		super.setName(name);
	}

	@Override
	public ArrayList<Move> moveCalculator(Board board) {
		ArrayList<Move> legalMoves = new ArrayList();

		for (int potentialX : xMoves) {
			for (int potentialY : yMoves) {
				int toX = super.getX() + potentialX;
				int toY = super.getY() + potentialY;
				if (isValid(toX, toY)) {
					Tile tile = board.getTile(toX, toY);
					if (tile.getType().toUpperCase().equals(trackType) && tile.getOccupied() == null) {
						if (((toY - super.getY()) >= 0) && ((toX - super.getX()) > 0)
								&& (super.getTeam().equals("WHITE"))) {
							legalMoves.add(new Move(this, (new Point(toX, toY))));
						} else if (((toY - super.getY()) <= 0) && ((toX - super.getX()) > 0)
								&& (super.getTeam().equals("BLACK"))) {
							legalMoves.add(new Move(this, (new Point(toX, toY))));
						}
					}
				}
				toX = super.getX();
				toY = super.getY();
			}
		}
		return legalMoves;
	}

	public boolean isValid(int X, int Y) {
		if ((X <= 6 && X >= 0) && (Y <= 7) && (Y >= 0)) {
			return true;
		}
		return false;
	}
}