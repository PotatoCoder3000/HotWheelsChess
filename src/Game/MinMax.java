package Game;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;

import Board.Board;
import Game.Move;
import Board.Tile;
import Pieces.Car;
import Pieces.Knight;
import Board.Piece;
import Board.Point;

public class MinMax {
	private int MAX_DEPTH = 1;
	private int count = 0;
	private int time = Integer.MIN_VALUE;
	private long startTime;
	private long endTime;
	private final long TIME_LIMIT = 4999;

	public Move execute(final Board board) {
		ArrayList<Move> myMoves = new ArrayList();
		Move bestMove = null;
		Move goodMove = null;
		int currentScore = 0;
		int bestScore = 0;
		int depth;

		startTime = System.currentTimeMillis();
		endTime = startTime + TIME_LIMIT;

		while (System.currentTimeMillis() < endTime) {
			myMoves = board.generateMoveList("BLACK");
			bestMove = null;
			currentScore = 0;
			bestScore = Integer.MIN_VALUE;
			depth = 0;

			for (Move move : myMoves) {
				count++;
				int X = move.getPiece().getX();
				int Y = move.getPiece().getY();
				Piece theirPiece = board.getTile(move.getPoint().getX(), move.getPoint().getY()).getOccupied();
				board.makeMove(move);
				// currentScore = Min(board, depth + 1);
				currentScore = minimax(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
				// System.out.println("Current Score: " + currentScore + ";
				// Move: " + move.toString());
				if (currentScore > bestScore) {
					bestMove = move;
					bestScore = currentScore;
				}
				move.setPiece(undoMove(board, move.getPiece(), theirPiece, new Point(X, Y)));
				if (theirPiece != null) {
					board.setPiece(theirPiece);
				}
				if (System.currentTimeMillis() > endTime || currentScore == time) {
					break;
				}
			}

			if (System.currentTimeMillis() < endTime) {
				goodMove = bestMove;
			}
			try {
				System.out.println("@" + MAX_DEPTH + " Considering... " + bestMove.toString() + "; Current Time: "
						+ ((float) (System.currentTimeMillis() - startTime) / 1000));
			} catch (Exception e) {
			}
			MAX_DEPTH++;
			if (MAX_DEPTH == 10) {
				break;
			}
		}
		System.out.println("Number of Nodes Explored: " + count);
		System.out.println("Elapsed Time:  " + ((float) (System.currentTimeMillis() - startTime) / 1000));
		return goodMove;
	}

	public int minimax(final Board board, int depth, int alpha, int beta, boolean isComputer) {
		count++;
		if (weHaveAWinner(board, depth) != -1) {
			return weHaveAWinner(board, depth);
		}
		if (depth == MAX_DEPTH) {
			return evaluate(board, depth);
		}
		ArrayList<Move> myMoves;
		int currentScore;
		if (!isComputer) {
			myMoves = board.generateMoveList("WHITE");
		} else {
			myMoves = board.generateMoveList("BLACK");
		}

		if (isComputer) {
			for (Move move : myMoves) {
				int X = move.getPiece().getX();
				int Y = move.getPiece().getY();
				Piece theirPiece = board.getTile(move.getPoint().getX(), move.getPoint().getY()).getOccupied();
				board.makeMove(move);
				currentScore = minimax(board, depth + 1, alpha, beta, false);
				undoMove(board, move.getPiece(), theirPiece, new Point(X, Y));
				if (theirPiece != null) {
					board.setPiece(theirPiece);
				}
				if (System.currentTimeMillis() > endTime || currentScore == time) {
					return time;
				}
				if (currentScore > alpha) {
					alpha = currentScore;
				}
				if (alpha >= beta) {
					break;
				}
			}
			return alpha;
		} else {
			for (Move move : myMoves) {
				int X = move.getPiece().getX();
				int Y = move.getPiece().getY();
				Piece theirPiece = board.getTile(move.getPoint().getX(), move.getPoint().getY()).getOccupied();
				board.makeMove(move);
				currentScore = minimax(board, depth + 1, alpha, beta, true);
				undoMove(board, move.getPiece(), theirPiece, new Point(X, Y));
				if (theirPiece != null) {
					board.setPiece(theirPiece);
				}
				if (System.currentTimeMillis() > endTime || currentScore == time) {
					return time;
				}
				if (currentScore < beta) {
					beta = currentScore;
				}
				if (alpha >= beta) {
					break;
				}
			}
			return beta;
		}
	}

	public int evaluate(Board board, int depth) {
		int evaluation = 0;

		ArrayList<Piece> myPieces = board.setTeams("BLACK");
		ArrayList<Piece> theirPieces = board.setTeams("WHITE");

		evaluation = evaluate2(board, myPieces);
		evaluation -= evaluate2(board, theirPieces);

		return evaluation;
	}

	public int evaluate2(Board board, ArrayList<Piece> teamArray) {
		int evaluation2 = 0;
		int distance;

		for (Piece piece : teamArray) {
			Tile tile = board.getTile(piece.getX(), piece.getY());
			distance = 6 - piece.getX();
			if (piece instanceof Car) {
				evaluation2 += (1 - distance / 6) * 2500000;
			} else if (!tile.getType().toUpperCase().equals("STANDARD")) {
				if (!tile.getType().equals(piece.getTeam())) {
					if (piece.getX() > findCar(teamArray).getX()) {
						evaluation2 += (1 - distance / 6) * 500;
					}
				}
			}
			evaluation2 += piece.getValue();
		}
		return evaluation2;
	}

	public int Min(final Board board, int depth) {
		count++;
		if (weHaveAWinner(board, depth) != -1) {
			return weHaveAWinner(board, depth);
		}
		if (depth == MAX_DEPTH) {
			return evaluate(board, depth);
		}

		ArrayList<Move> myMoves = board.generateMoveList("WHITE");
		int currentScore = 0;
		int bestScore = Integer.MAX_VALUE;
		for (Move move : myMoves) {
			int X = move.getPiece().getX();
			int Y = move.getPiece().getY();
			Piece theirPiece = board.getTile(move.getPoint().getX(), move.getPoint().getY()).getOccupied();
			board.makeMove(move);
			currentScore = Max(board, depth + 1);
			if (currentScore < bestScore) {
				bestScore = currentScore;
			}
			undoMove(board, move.getPiece(), theirPiece, new Point(X, Y));
			if (theirPiece != null) {
				board.setPiece(theirPiece);
			}
		}
		return bestScore;
	}

	public int Max(final Board board, int depth) {
		count++;
		if (weHaveAWinner(board, depth) != -1) {
			return weHaveAWinner(board, depth);
		}
		if (depth == MAX_DEPTH) {
			return evaluate(board, depth);
		}

		ArrayList<Move> myMoves = board.generateMoveList("BLACK");
		int currentScore = 0;
		int bestScore = Integer.MIN_VALUE;

		for (Move move : myMoves) {
			int X = move.getPiece().getX();
			int Y = move.getPiece().getY();
			Piece theirPiece = board.getTile(move.getPoint().getX(), move.getPoint().getY()).getOccupied();
			board.makeMove(move);
			currentScore = Min(board, depth + 1);
			if (currentScore > bestScore) {
				bestScore = currentScore;
			}
			undoMove(board, move.getPiece(), theirPiece, new Point(X, Y));
			if (theirPiece != null) {
				board.setPiece(theirPiece);
			}
		}

		return bestScore;
	}

	public int weHaveAWinner(Board board, int depth) {
		if (board.getTile(6, 3).getOccupied() instanceof Car) {
			return (-50000 + (depth * 100));
		} else if (board.getTile(6, 4).getOccupied() instanceof Car) {
			return 50000 - (depth * 100);
		}
		return -1;
	}

	public Piece undoMove(Board board, Piece myPiece, Piece theirPiece, Point point) {
		if (theirPiece != null) {
			board.setTeams(theirPiece.getTeam()).add(theirPiece);
		}
		board.getTile(myPiece.getX(), myPiece.getY()).setOccupied(null);
		myPiece.setX(point.getX());
		myPiece.setY(point.getY());
		board.setPiece(myPiece);
		return myPiece;
	}

	public Piece findCar(ArrayList<Piece> teamArray) {
		for (Piece piece : teamArray) {
			if (piece instanceof Car) {
				return piece;
			}
		}
		return null;
	}
}
