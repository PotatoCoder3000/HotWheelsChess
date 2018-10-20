package Game;

import Board.InputReversal;
import Board.Piece;
import Board.Point;

public class Move {
	private Piece piece;
	private Point point;

	public Move(final Piece piece, final Point point) {
		this.piece = piece;
		this.point = point;
	}

	public Point getPoint() {
		return this.point;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public Piece getPiece() {
		return this.piece;
	}

	public String toString() {
		if (this.piece != null) {
			InputReversal translate = new InputReversal();
			String descString = translate.deTranslation(this.piece, this.point);
			descString += ":" + piece.getName();
			return descString;
		}
		return null;
	}
	
//	public String toString2() {
//		if (this.piece != null) {
//			InputReversal translate = new InputReversal();
//			String descString = translate.reverseMove(this.piece, this.point);
//			descString += ":" + piece.getName();
//			return descString;
//		}
//		return null;
//	}

}
