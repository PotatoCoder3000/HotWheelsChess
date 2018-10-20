package Pieces;

import java.util.ArrayList;

import Board.Board;
import Board.Piece;
import Board.Point;
import Board.Tile;
import Game.Move;

public class Pawn extends Piece {
	private final static int[] xMoves = { -1, 0, 1 };

	public Pawn(final String team, int X, int Y) {
		super(team, 10, X, Y);
		char name;
		if (team.toUpperCase().equals("WHITE")) {
			name = 'p';
		} else {
			name = 'P';
		}
		super.setName(name);
	}

	public ArrayList<Move> moveCalculator(final Board board) {
		ArrayList<Move> legalMoves = new ArrayList();
		int pieceDirection;

		if (super.getTeam().equals("WHITE")) {
			pieceDirection = 1;
		} else {
			pieceDirection = -1;
		}

		for (int potentialX : xMoves) {
			int toX = super.getX() + potentialX;
			int toY = super.getY() + pieceDirection;
			if (isValid(toX, toY)) {
				Tile tile = board.getTile(toX, toY);
				if ((tile.getOccupied() != null) && (Math.abs(toX - super.getX()) != 0)) {
					if (!tile.getOccupied().getTeam().equals(super.getTeam()) && !(tile.getOccupied() instanceof Car)) {
						legalMoves.add(new Move(this, (new Point(toX, toY))));
					}
				} else if ((tile.getOccupied() == null) && (Math.abs(toX - super.getX()) == 0)) {
					legalMoves.add(new Move(this, (new Point(toX, toY))));
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