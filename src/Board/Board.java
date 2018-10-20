package Board;

import java.util.ArrayList;

import Game.Move;
import Pieces.Bishop;
import Pieces.Car;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Rook;

public class Board {
	private int WIDTH = 7;
	private int HEIGHT = 8;
	private ArrayList<Piece> whiteArray;
	private ArrayList<Piece> blackArray;
	private Tile[][] board = new Tile[WIDTH][HEIGHT];

	public Board() {
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				board[i][j] = new Tile(i, j);
			}
		}

		board[0][7].setType("BLACK");
		board[1][6].setType("BLACK");
		board[2][5].setType("BLACK");
		board[3][4].setType("BLACK");
		board[4][4].setType("BLACK");
		board[5][4].setType("BLACK");
		board[6][4].setType("BLACK");

		board[0][0].setType("WHITE");
		board[1][1].setType("WHITE");
		board[2][2].setType("WHITE");
		board[3][3].setType("WHITE");
		board[4][3].setType("WHITE");
		board[5][3].setType("WHITE");
		board[6][3].setType("WHITE");

		this.createBoardGame();
		whiteArray = this.generateTeams("WHITE");
		blackArray = this.generateTeams("BLACK");
	}

	public void createBoardGame() {
		// TEAM 1's Pieces
		this.setPiece(new Pawn("WHITE", 1, 1));
		this.setPiece(new Pawn("WHITE", 2, 2));
		this.setPiece(new Pawn("WHITE", 3, 3));
		this.setPiece(new Pawn("WHITE", 4, 3));
		this.setPiece(new Pawn("WHITE", 5, 3));
		this.setPiece(new Pawn("WHITE", 6, 3));
		this.setPiece(new Bishop("WHITE", 3, 2));
		this.setPiece(new Bishop("WHITE", 4, 2));
		this.setPiece(new Rook("WHITE", 0, 2));
		this.setPiece(new Rook("WHITE", 1, 2));
		this.setPiece(new Knight("WHITE", 0, 3));
		this.setPiece(new Knight("WHITE", 1, 3));
		this.setPiece(new Car("WHITE", 0, 0));

		// TEAM 2's Pieces
		this.setPiece(new Pawn("BLACK", 1, 6));
		this.setPiece(new Pawn("BLACK", 2, 5));
		this.setPiece(new Pawn("BLACK", 3, 4));
		this.setPiece(new Pawn("BLACK", 4, 4));
		this.setPiece(new Pawn("BLACK", 5, 4));
		this.setPiece(new Pawn("BLACK", 6, 4));
		this.setPiece(new Bishop("BLACK", 3, 5));
		this.setPiece(new Bishop("BLACK", 4, 5));
		this.setPiece(new Rook("BLACK", 0, 5));
		this.setPiece(new Rook("BLACK", 1, 5));
		this.setPiece(new Knight("BLACK", 0, 4));
		this.setPiece(new Knight("BLACK", 1, 4));
		this.setPiece(new Car("BLACK", 0, 7));
	}

	public void displayBoard() {
		Tile tile;
		for (int i = (board[WIDTH - 1].length - 1); i >= 0; i--) {
			System.out.print(i + 1 + " ");
			for (int j = 0; j < board.length; j++) {
				tile = board[j][i];
				if (tile.getOccupied() != null) {
					System.out.print(tile.getOccupied().getName() + " ");
				} else {
					System.out.print(tile.getName() + " ");
				}
			}
			System.out.println();
		}
		System.out.println("  A B C D E F G");
	}

	public void makeMove(Move move) {
		Point point = move.getPoint();
		Piece myPiece = move.getPiece();
		Piece theirPiece = board[point.getX()][point.getY()].getOccupied();
		board[myPiece.getX()][myPiece.getY()].setOccupied(null);
		board[point.getX()][point.getY()].setOccupied(myPiece);
		myPiece.setX(point.getX());
		myPiece.setY(point.getY());
		if (theirPiece != null) {
			if (theirPiece.getTeam().equals("BLACK")) {
				blackArray.remove(theirPiece);
			} else {
				whiteArray.remove(theirPiece);
			}
		}
	}

	public void setPiece(Piece piece) {
		board[piece.getX()][piece.getY()].setOccupied(piece);
	}

	public ArrayList<Piece> generateTeams(String myTeam) {
		Tile tile;
		ArrayList<Piece> pieces = new ArrayList();
		for (int i = (board[WIDTH - 1].length - 1); i >= 0; i--) {
			for (int j = 0; j < board.length; j++) {
				tile = board[j][i];
				if (tile.getOccupied() != null) {
					if (tile.getOccupied().getTeam().toUpperCase().equals(myTeam)) {
						pieces.add(tile.getOccupied());
					}
				}
			}
		}
		return pieces;
	}

	public ArrayList<Piece> setTeams(String myTeam) {
		if (myTeam.toUpperCase().equals("WHITE")) {
			return whiteArray;
		}
		return blackArray;
	}

	public ArrayList<Move> generateMoveList(String teamArray) {
		ArrayList<Piece> myPieces = new ArrayList();
		ArrayList<Move> moveList = new ArrayList();
		if (teamArray.equals("WHITE")) {
			myPieces = whiteArray;
		} else {
			myPieces = blackArray;
		}

		for (Piece myPiece : myPieces) {
			moveList.addAll(myPiece.moveCalculator(this));
		}
		if (moveList.isEmpty()) {
			int Y;
			Piece car = findCar(myPieces);

			if (car.getTeam().equals("WHITE")) {
				Y = 1;
			} else {
				Y = -1;
			}
			int TOX = car.getX() + 1;
			int TOY = car.getY() + Y;
			if ((TOX <= 6 && TOX >= 0) && (TOY <= 7 && TOY >= 0)) {
				if (board[car.getX() + 1][car.getY()].getType().equals(car.getTeam())) {
					moveList.add(new Move(car, new Point(car.getX() + 1, car.getY())));
				} else if (board[car.getX() + 1][car.getY() + Y].getType().equals(car.getTeam())) {
					moveList.add(new Move(car, new Point(car.getX() + 1, car.getY() + Y)));
				}
			}
		}
		return moveList;
	}

	public Tile getTile(int X, int Y) {
		return board[X][Y];
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