package Board;

import Game.Move;

public class InputReversal {

	public Move moveTranslation(String input, Board board) {
		boolean isValid = true;
		int[] indexInput = new int[4];
		Point point;
		if (input.length() == 4) {
			for (int i = 0; i < input.length(); i++) {
				switch (input.charAt(i)) {
				case '1':
					indexInput[i] = 0;
					break;
				case '2':
					indexInput[i] = 1;
					break;
				case '3':
					indexInput[i] = 2;
					break;
				case '4':
					indexInput[i] = 3;
					break;
				case '5':
					indexInput[i] = 4;
					break;
				case '6':
					indexInput[i] = 5;
					break;
				case '7':
					indexInput[i] = 6;
					break;
				case '8':
					indexInput[i] = 7;
					break;
				case 'A':
					indexInput[i] = 0;
					break;
				case 'B':
					indexInput[i] = 1;
					break;
				case 'C':
					indexInput[i] = 2;
					break;
				case 'D':
					indexInput[i] = 3;
					break;
				case 'E':
					indexInput[i] = 4;
					break;
				case 'F':
					indexInput[i] = 5;
					break;
				case 'G':
					indexInput[i] = 6;
					break;
				default:
					isValid = false;
					break;
				}
			}
		}
		if (isValid) {
			Piece piece = board.getTile(indexInput[0], indexInput[1]).getOccupied();
			point = new Point(indexInput[2], indexInput[3]);
			return new Move(piece, point);
		}
		return null;
	}

	public String deTranslation(Piece piece, Point point) {
		int[] indexInput = { piece.getX(), piece.getY(), point.getX(), point.getY() };
		String reversedString = "";
		for (int i = 0; i < indexInput.length; i++) {
			if ((i % 2) != 0) {
				switch (indexInput[i]) {
				case 0:
					reversedString += "8";
					break;
				case 1:
					reversedString += "7";
					break;
				case 2:
					reversedString += "6";
					break;
				case 3:
					reversedString += "5";
					break;
				case 4:
					reversedString += "4";
					break;
				case 5:
					reversedString += "3";
					break;
				case 6:
					reversedString += "2";
					break;
				case 7:
					reversedString += "1";
					break;
				}
			} else {
				switch (indexInput[i]) {
				case 0:
					reversedString += "A";
					break;
				case 1:
					reversedString += "B";
					break;
				case 2:
					reversedString += "C";
					break;
				case 3:
					reversedString += "D";
					break;
				case 4:
					reversedString += "E";
					break;
				case 5:
					reversedString += "F";
					break;
				case 6:
					reversedString += "G";
					break;
				}
			}
		}
		return reversedString;
	}

	public String reverseMove(Piece p, int x, int y) {
		int Y = (7 - y);
		int pY = (7 - p.getY());
		int[] index = { p.getX(), pY, x, Y };
		String output = "";
		for (int n = 0; n < index.length; n++) {
			if ((n % 2) == 0) {
				switch (index[n]) {
				case 0:
					output += "A";
					break;
				case 1:
					output += "B";
					break;
				case 2:
					output += "C";
					break;
				case 3:
					output += "D";
					break;
				case 4:
					output += "E";
					break;
				case 5:
					output += "F";
					break;
				case 6:
					output += "G";
					break;
				}
			} else {
				switch (index[n]) {
				case 0:
					output += "8";
					break;
				case 1:
					output += "7";
					break;
				case 2:
					output += "6";
					break;
				case 3:
					output += "5";
					break;
				case 4:
					output += "4";
					break;
				case 5:
					output += "3";
					break;
				case 6:
					output += "2";
					break;
				case 7:
					output += "1";
					break;
				}
			}
		}
		return output;
	}
}
