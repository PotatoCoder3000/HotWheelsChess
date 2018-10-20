package Game;

import java.util.ArrayList;
import java.util.Scanner;

import Board.Board;
import Board.InputReversal;
import Board.Piece;
import Pieces.Car;

public class Game {
	private Scanner console = new Scanner(System.in);
	private boolean gameOver, swapTurns = false;
	private String playerInput;
	private InputReversal translate;
	private Board board;
	private ArrayList<Piece> playerPieces = new ArrayList();
	private ArrayList<Move> moveList = new ArrayList();

	public Game() {
		translate = new InputReversal();
		board = new Board();
		board.displayBoard();

		System.out.println("Is the User 'First' or 'Second'?");
		if (console.nextLine().toUpperCase().equals("FIRST")) {
			swapTurns = true;
		}

		while (!gameOver) {
			if (swapTurns) {
				moveList = board.generateMoveList("WHITE");
			} else {
				moveList = board.generateMoveList("BLACK");
			}
			
			for (Move move : moveList) {
				InputReversal reverse = new InputReversal();
				String m = reverse.reverseMove(move.getPiece(), move.getPoint().getX(), move.getPoint().getY());
				System.out.print(m.toString() + " ");
			}

			if (swapTurns) {
				System.out.print("\nWhite Team's Move: ");
				playerInput = console.next();
				Move translatedInput = translate.moveTranslation(playerInput, board);
				if (translatedInput != null) {
					for (Move move : moveList) {
						if (translatedInput.toString().equals(move.toString())) {
							board.makeMove(move);
							swapTurns = !swapTurns;
						}
					}
				} else {
					System.out.println("Failed Move");
				}
				if (playerInput.equals("y")) {
					gameOver = true;
				}
			} else {
				System.out.print("\nBlack Team's Move: \n");
				MinMax minimax = new MinMax();
				Move move = minimax.execute(board);
                System.out.println(move + "\n");
				board.makeMove(move);
				swapTurns = !swapTurns;
			}
			moveList.clear();
			board.displayBoard();
			if (board.getTile(6, 3).getOccupied() instanceof Car || board.getTile(6, 4).getOccupied() instanceof Car) {
				System.out.println("\n  GAME OVER");
				gameOver = true;
			}
		}
	}

	public Piece findPiece(ArrayList<Piece> teamArray, Piece movePiece) {
		for (Piece piece : teamArray) {
			if (piece.equals(movePiece)) {
				return piece;
			}
		}
		return null;
	}
}