package Pieces;

import java.util.ArrayList;

import Board.Board;
import Board.Piece;
import Board.Point;
import Board.Tile;
import Game.Move;

public class Knight extends Piece {
	private final static int[] xMoves = { -2, -1, 1, 2 };
	private final static int[] yMoves = { -2, -1, 1, 2 };

	public Knight(final String team, int X, int Y) {
		super(team, 100, X, Y);
		char name;
		if (team.toUpperCase().equals("WHITE")) {
			name = 'n';
		} else {
			name = 'N';
		}
		super.setName(name);
	}

	public ArrayList<Move> moveCalculator(final Board board) {
		ArrayList<Move> legalMoves = new ArrayList();

		for (int potentialX : xMoves) {
			for (int potentialY : yMoves) {
				int toX = super.getX() + potentialX;
				int toY = super.getY() + potentialY;
				if ((Math.abs(potentialX) != Math.abs(potentialY)) && isValid(toX, toY)) {
					Tile tile = board.getTile(toX, toY);
					if (tile.getOccupied() != null) {
						if (!tile.getOccupied().getTeam().equals(super.getTeam())
								&& !(tile.getOccupied() instanceof Car)) {
							legalMoves.add(new Move(this, (new Point(toX, toY))));
						}
					} else if (((toY - this.getY()) > 0) && (this.getTeam().equals("WHITE"))) {
						legalMoves.add(new Move(this, (new Point(toX, toY))));
					} else if (((toY - this.getY()) < 0) && (this.getTeam().equals("BLACK"))) {
						legalMoves.add(new Move(this, (new Point(toX, toY))));
					}
				}
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