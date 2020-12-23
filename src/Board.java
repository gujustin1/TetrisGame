import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Board {

	private Tile[][] board;

	public Board(int r, int c) {
		board = new Tile[r][c];
		for (int row = 0; row < r; row++) {
			for (int col = 0; col < c; col++) {
				board[row][col] = new Tile(col, row);
			}
		}
	}
	
	public void resetBoard() {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				board[row][col].reset();
			}
		}
	}

	public void draw(Graphics g) {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				board[row][col].draw(g);
			}
		}
	}

	public void draw2(Graphics g) {

		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				board[row][col].draw2(g);
			}
		}

	}

	public ArrayList<Integer> isClear() {
		ArrayList<Integer> linesCleared = new ArrayList<Integer>();
		for (int r = board.length - 1; r >= 0; r--) {
			boolean clear = true;
			for (int c = 0; c < board[0].length; c++) {
				if (!board[r][c].isSolid()) {
					clear = false;
					break;
				}
			}
			if (clear) {
				linesCleared.add(r);
			}
		}
		return linesCleared;
	}

	public void clearLines() {
		ArrayList<Integer> linesCleared = isClear();
		for (int clear : linesCleared) {
			for (int c = 0; c < board[0].length; c++) {
				board[clear][c].reset();
			}
		}
		for (int r = board.length - 1; r >= 0; r--) {
			int below = 0;
			for (int a : linesCleared) {
				if (a > r) {
					below++;
				}
			}
			if (below > 0) {
				for (int c = 0; c < board[0].length; c++) {
					if (board[r][c].isSolid()) {
						board[r + below][c].setColor(board[r][c].getColor());
						board[r + below][c].setSolid(true);
						board[r][c].reset();
					}
				}
			}
		}
	}

	public boolean lose() {
		if (board[1][4].isSolid() || board[1][5].isSolid()) {
			return true;
		}
		return false;
	}

	public void moveRight() {
		if (canMoveRight()) {
			for (int c = board[0].length - 1; c >= 0; c--) {
				for (int r = 0; r < board.length; r++) {
					if (board[r][c].isTetromino()) {
						board[r][c + 1].setColor(board[r][c].getColor());
						board[r][c + 1].setTetromino(true);
						if (board[r][c].isCenter()) {
							board[r][c + 1].setCenter(true);
						}
						board[r][c].reset();
					}
				}
			}
		}
	}

	public boolean canMoveRight() {
		for (int c = board[0].length - 1; c >= 0; c--) {
			for (int r = 0; r < board.length; r++) {
				if (board[r][c].isTetromino() && c == board[0].length - 1) {
					return false;
				}
				if (board[r][c].isTetromino() && board[r][c + 1].isSolid()) { // Checks to see if tiles to the right of
																				// a tetromino are clear
					return false;
				}
			}
		}
		return true;
	}

	public void moveLeft() {
		if (canMoveLeft()) {
			for (int c = 0; c < board[0].length; c++) {
				for (int r = 0; r < board.length; r++) {
					if (board[r][c].isTetromino()) {
						board[r][c - 1].setColor(board[r][c].getColor());
						board[r][c - 1].setTetromino(true);
						if (board[r][c].isCenter()) {
							board[r][c - 1].setCenter(true);
						}
						board[r][c].reset();
					}
				}
			}
		}
	}

	public boolean canMoveLeft() {
		for (int c = 0; c < board[0].length; c++) {
			for (int r = 0; r < board.length; r++) {
				if (board[r][c].isTetromino() && c == 0) {
					return false;
				}
				if (board[r][c].isTetromino() && board[r][c - 1].isSolid()) { // Checks to see if tiles to the right of
																				// a tetromino are clear
					return false;
				}
			}
		}
		return true;
	}

	public void rotate() {

		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c].isCenter()) {

					if (board[r][c].getColor() == Color.yellow) {// square does not rotate

					}
					else if (board[r][c].getColor() == Color.pink) {// X does not rotate

					}
					
					else if (board[r][c].getColor() == Color.white) {// U piece
						if (c == 0 || board[r][c-1].isSolid()) {
							moveRight();
							c++;
						}
						if (c == board[0].length - 1 || board[r][c+1].isSolid()) {
							moveLeft();
							c--;
						}
						if(board[r-1][c-1].isTetromino() && board[r-1][c+1].isTetromino()) {
							board[r - 1][c].setColor(board[r][c].getColor());
							board[r - 1][c].setTetromino(true);
							board[r + 1][c].setColor(board[r][c].getColor());
							board[r + 1][c].setTetromino(true);
							board[r + 1][c+1].setColor(board[r][c].getColor());
							board[r + 1][c+1].setTetromino(true);
							board[r-1][c - 1].reset();
							board[r][c - 1].reset();
							board[r][c + 1].reset();
						}

						else if(board[r-1][c+1].isTetromino() && board[r+1][c+1].isTetromino()) {
							board[r][c - 1].setColor(board[r][c].getColor());
							board[r][c - 1].setTetromino(true);
							board[r][c + 1].setColor(board[r][c].getColor());
							board[r][c + 1].setTetromino(true);
							board[r + 1][c - 1].setColor(board[r][c].getColor());
							board[r + 1][c - 1].setTetromino(true);
							board[r-1][c + 1].reset();
							board[r-1][c].reset();
							board[r+1][c].reset();
						}
						else if(board[r+1][c+1].isTetromino() && board[r+1][c-1].isTetromino()) {
							board[r - 1][c - 1].setColor(board[r][c].getColor());
							board[r - 1][c - 1].setTetromino(true);
							board[r - 1][c].setColor(board[r][c].getColor());
							board[r - 1][c].setTetromino(true);
							board[r + 1][c].setColor(board[r][c].getColor());
							board[r + 1][c].setTetromino(true);
							board[r][c + 1].reset();
							board[r][c - 1].reset();
							board[r+1][c + 1].reset();
						}
						else if(board[r+1][c-1].isTetromino() && board[r-1][c-1].isTetromino()) {
							board[r][c - 1].setColor(board[r][c].getColor());
							board[r][c - 1].setTetromino(true);
							board[r][c + 1].setColor(board[r][c].getColor());
							board[r][c + 1].setTetromino(true);
							board[r - 1][c + 1].setColor(board[r][c].getColor());
							board[r - 1][c + 1].setTetromino(true);
							board[r+1][c -1].reset();
							board[r + 1][c].reset();
							board[r-1][c].reset();
						}
					}
					

					else if (board[r][c].getColor() == Color.cyan) {// line piece
						
						
						if(c==0 || board[r][c-1].isSolid()) {
							moveRight();
							c++;
						}
						if(c==board[c].length-1 || board[r][c+1].isSolid()) {
							moveLeft();
							moveLeft();
							c-=2;
						}
						if(c==board[c].length-2 || board[r][c+2].isSolid()) {
							moveLeft();
							c--;
						}
						if(r-1 >= 0 ) {
                            if (!board[r - 1][c].isTetromino()) {// if it is horizontal
                                if (board[r + 2][c].isSolid()) {//if there is a solid piece 2 below
                                    board[r - 2][c].setTetromino(true);
                                    board[r - 2][c].setColor(Color.cyan);
                                    board[r - 1][c].setTetromino(true);
                                    board[r - 1][c].setColor(Color.cyan);
                                    board[r + 1][c].setTetromino(true);
                                    board[r + 1][c].setColor(Color.cyan);
                                    board[r][c - 1].reset();
                                    board[r][c + 1].reset();
                                    board[r][c + 2].reset();
                                    return;
                                } else {
                                    board[r - 1][c].setTetromino(true);
                                    board[r - 1][c].setColor(Color.cyan);
                                    board[r + 1][c].setTetromino(true);
                                    board[r + 1][c].setColor(Color.cyan);
                                    board[r + 2][c].setTetromino(true);
                                    board[r + 2][c].setColor(Color.cyan);
                                    board[r][c - 1].reset();
                                    board[r][c + 1].reset();
                                    board[r][c + 2].reset();
                                    return;
                                }
                            } else {// if it is vertical
                                board[r][c - 1].setTetromino(true);
                                board[r][c - 1].setColor(Color.cyan);
                                board[r][c + 1].setTetromino(true);
                                board[r][c + 1].setColor(Color.cyan);
                                board[r][c + 2].setTetromino(true);
                                board[r][c + 2].setColor(Color.cyan);
                                board[r - 1][c].reset();
                                board[r + 1][c].reset();
                                board[r + 2][c].reset();

                                return;
                            }
                        }
					}
					else if (board[r][c].getColor() == Color.MAGENTA && r + 1 < board.length) {// t piece
						if (c == 0 || board[r][c-1].isSolid()) {
							moveRight();
							c++;
						}
						if (c == board[0].length - 1 || board[r][c+1].isSolid()) {
							moveLeft();
							c--;
						}
						if (!board[r + 1][c].isTetromino()) {
							board[r + 1][c].setColor(board[r][c].getColor());
							board[r + 1][c].setTetromino(true);
							board[r][c - 1].reset();

						} else if (!board[r][c - 1].isTetromino()) {
							board[r][c - 1].setColor(board[r][c].getColor());
							board[r][c - 1].setTetromino(true);
							board[r - 1][c].reset();
						} else if (!board[r - 1][c].isTetromino()) {
							board[r - 1][c].setColor(board[r][c].getColor());
							board[r - 1][c].setTetromino(true);
							board[r][c + 1].reset();
						} else if (!board[r][c + 1].isTetromino()) {
							board[r][c + 1].setColor(board[r][c].getColor());
							board[r][c + 1].setTetromino(true);
							board[r + 1][c].reset();
						}

					} else if (board[r][c].getColor() == Color.green) {// S piece
						if (!board[r + 1][c].isTetromino()) {// if it looks like s
							board[r - 1][c - 1].setColor(board[r][c].getColor());
							board[r - 1][c - 1].setTetromino(true);
							board[r + 1][c].setColor(board[r][c].getColor());
							board[r + 1][c].setTetromino(true);
							board[r - 1][c].reset();
							board[r - 1][c + 1].reset();
						} else {// vertical S
							if (c == board[c].length - 1 || board[r][c + 1].isSolid()) {
								moveLeft();
								c--;
							}
							board[r - 1][c].setColor(board[r][c].getColor());
							board[r - 1][c].setTetromino(true);
							board[r - 1][c + 1].setColor(board[r][c].getColor());
							board[r - 1][c + 1].setTetromino(true);
							board[r - 1][c - 1].reset();
							board[r + 1][c].reset();
						}
					} else if (board[r][c].getColor() == Color.red) {// Z piece
						if (!board[r + 1][c].isTetromino()) {// if it looks like Z
							board[r - 1][c + 1].setColor(board[r][c].getColor());
							board[r - 1][c + 1].setTetromino(true);
							board[r + 1][c].setColor(board[r][c].getColor());
							board[r + 1][c].setTetromino(true);
							board[r - 1][c - 1].reset();
							board[r - 1][c].reset();
						} else {// vertical Z
							if (c == 0 || board[r][c - 1].isSolid()) {
								moveRight();
								c++;
							}
							board[r - 1][c].setColor(board[r][c].getColor());
							board[r - 1][c].setTetromino(true);
							board[r - 1][c - 1].setColor(board[r][c].getColor());
							board[r - 1][c - 1].setTetromino(true);
							board[r - 1][c + 1].reset();
							board[r + 1][c].reset();
						}
					}

					else {// L and J
						if (r - 1 > 0 && r + 1 < board.length) {
							if (c == board[0].length - 1 || board[r][c + 1].isSolid()) {
								moveLeft();
								c--;
							}
							if (c == 0 || board[r][c - 1].isSolid()) {
								moveRight();
								c++;
							}

							if (board[r - 1][c - 1].isTetromino() && board[r - 1][c - 1].getRotate()) {
								board[r - 1][c + 1].setColor(board[r][c].getColor());
								board[r - 1][c + 1].setTetromino(true);
								board[r - 1][c + 1].setRotate(false);
								board[r - 1][c - 1].reset();
							}
							if (board[r][c - 1].isTetromino() && board[r][c - 1].getRotate()) {
								board[r - 1][c].setColor(board[r][c].getColor());
								board[r - 1][c].setTetromino(true);
								board[r - 1][c].setRotate(false);
								board[r][c - 1].reset();
							}
							if (board[r + 1][c - 1].isTetromino() && board[r + 1][c - 1].getRotate()) {
								board[r - 1][c - 1].setColor(board[r][c].getColor());
								board[r - 1][c - 1].setTetromino(true);
								board[r - 1][c - 1].setRotate(false);
								board[r + 1][c - 1].reset();
							}
							if (board[r + 1][c].isTetromino() && board[r + 1][c].getRotate()) {
								board[r][c - 1].setColor(board[r][c].getColor());
								board[r][c - 1].setTetromino(true);
								board[r][c - 1].setRotate(false);
								board[r + 1][c].reset();
							}
							if (board[r + 1][c + 1].isTetromino() && board[r + 1][c + 1].getRotate()) {
								board[r + 1][c - 1].setColor(board[r][c].getColor());
								board[r + 1][c - 1].setTetromino(true);
								board[r + 1][c - 1].setRotate(false);
								board[r + 1][c + 1].reset();
							}
							if (board[r][c + 1].isTetromino() && board[r][c + 1].getRotate()) {
								board[r + 1][c].setColor(board[r][c].getColor());
								board[r + 1][c].setTetromino(true);
								board[r + 1][c].setRotate(false);
								board[r][c + 1].reset();
							}
							if (board[r - 1][c + 1].isTetromino() && board[r - 1][c + 1].getRotate()) {
								board[r + 1][c + 1].setColor(board[r][c].getColor());
								board[r + 1][c + 1].setTetromino(true);
								board[r + 1][c + 1].setRotate(false);
								board[r - 1][c + 1].reset();
							}
							if (board[r - 1][c].isTetromino() && board[r - 1][c].getRotate()) {
								board[r][c + 1].setColor(board[r][c].getColor());
								board[r][c + 1].setTetromino(true);
								board[r][c + 1].setRotate(false);
								board[r - 1][c].reset();
							}
							board[r - 1][c - 1].setRotate(true);
							board[r][c - 1].setRotate(true);
							board[r + 1][c - 1].setRotate(true);
							board[r + 1][c].setRotate(true);
							board[r + 1][c + 1].setRotate(true);
							board[r][c + 1].setRotate(true);
							board[r - 1][c + 1].setRotate(true);
							board[r - 1][c].setRotate(true);
						}
					}

				}
			}
		}

	}

	public void updateDrop() {
		if (isFalling()) { // if all clear, drop tiles
			for (int r = board.length - 1; r >= 0; r--) {
				for (int c = 0; c < board[0].length; c++) {
					if (board[r][c].isTetromino()) {
						board[r + 1][c].setColor(board[r][c].getColor());
						board[r + 1][c].setTetromino(true);
						if (board[r][c].isCenter()) {
							board[r + 1][c].setCenter(true);
						}
						board[r][c].reset();

					}
				}
			}
		}
	}

	public boolean isFalling() { // checks if tiles can fall

		for (int r = board.length - 1; r >= 0; r--) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c].isTetromino() && r == board.length - 1) {
					return false;
				}
				if (board[r][c].isTetromino() && board[r + 1][c].isSolid()) { // Checks to see if all tiles under a
																				// tetromino are not clear
					return false;
				}
			}
		}
		return true;
	}

	public void solidify() {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c].isTetromino()) {
					board[r][c].setSolid(true);
					board[r][c].setTetromino(false);
					board[r][c].setCenter(false);
				}
			}
		}
	}

	public void add(Tetromino t) {
		for (int r = 0; r < t.getShape().length; r++) {
			for (int c = 0; c < t.getShape()[0].length; c++) {
				if (t.getShape()[r][c].getColor() != Color.black) {
					board[r][c + 4] = t.getShape()[r][c];
				}
			}
		}
	}
}