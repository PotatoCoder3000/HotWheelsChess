package Board;

public class Tile {
	private int X, Y;
	private String type;
	private char name;
	private Piece piece;

	public Tile(int X, int Y) {
		this.X = X;
		this.Y = Y;
		this.piece = null;
		this.type = "STANDARD";
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setOccupied(Piece piece) {
		this.piece = piece;
	}

	public char getName() {
		if (type.toUpperCase().equals("STANDARD")) {
			return '-';
		}
		return '-';
	}

	public String getType() {
		return this.type;
	}

	public Piece getOccupied() {
		if (this.piece != null) {
			return this.piece;
		}
		return null;
	}
}
