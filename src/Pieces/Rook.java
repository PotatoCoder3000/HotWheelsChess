package Pieces;

import java.util.ArrayList;

import Board.Board;
import Board.Piece;
import Board.Point;
import Board.Tile;
import Game.Move;

public class Rook extends Piece {
	private final static int[] xMoves = { -1, 1 };
	private final static int[] yMoves = { -1, 1 };

	public Rook(final String team, int X, int Y) {
		super(team, 50, X, Y);
		char name;
		if (team.toUpperCase().equals("WHITE")) {
			name = 'r';
		} else {
			name = 'R';
		}
		super.setName(name);
	}

	@Override
	public ArrayList<Move> moveCalculator(Board board) {
		ArrayList<Move> legalMoves = new ArrayList();

		for (int potentialX : xMoves) {
			int toX = super.getX();
			int toY = super.getY();
			do {
				toX += potentialX;
				if (isValid(toX, toY)) {
					Tile tile = board.getTile(toX, toY);
					if (tile.getOccupied() != null) {
						if (!tile.getOccupied().getTeam().equals(super.getTeam())
								&& !(tile.getOccupied() instanceof Car)) {
							legalMoves.add(new Move(this, (new Point(toX, toY))));
						}
						break;
					}
				} else {
					break;
				}
			} while (true);
		}
		for (int potentialY : yMoves) {
			int toX = super.getX();
			int toY = super.getY();
			do {
				toY += potentialY;
				if (isValid(toX, toY)) {
					Tile tile = board.getTile(Math.abs(toX), Math.abs(toY));
					if (tile.getOccupied() != null) {
						if (!tile.getOccupied().getTeam().equals(super.getTeam())
								&& !(tile.getOccupied() instanceof Car)) {
							legalMoves.add(new Move(this, (new Point(toX, toY))));
						}
						break;
					} else if (((toY - this.getY()) > 0) && super.getTeam().equals("WHITE")) {
						legalMoves.add(new Move(this, (new Point(toX, toY))));
					} else if (((toY - this.getY()) < 0) && super.getTeam().equals("BLACK")) {
						legalMoves.add(new Move(this, (new Point(toX, toY))));
					}
				} else {
					break;
				}
			} while (true);
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