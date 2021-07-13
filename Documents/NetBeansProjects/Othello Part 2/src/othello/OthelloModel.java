
package othello;


public class OthelloModel {
    
    private static OthelloModel othelloModel;
    private final int BOARD_SIZE = 8;
    private final int[][] BOARD = new int[BOARD_SIZE][BOARD_SIZE];
    
    private OthelloModel() {
        
    }
    
    /**
     * Returns an instance of OthelloModel
     * This method is used to implement ensure that the game has exactly one
     * model
     * @return an OthelloModel instance 
     */
    public static OthelloModel getInstance() {
        if(othelloModel == null) {
            othelloModel = new OthelloModel();
            return othelloModel;
        }
        return othelloModel;
    }
    
    /**
     * Returns the contents of a square
     * @param row the row number of the square
     * @param col the column number of the square
     * @return the contents of a given square (0 for empty, 1 for black, 2 for white)
     */
    public int getSquare(int row, int col) {
        return BOARD[col][row];
    }
    
    /**
     * This method prepares a board layout based on the mode
     * @param mode the mode for which to prepare the board layout
     */
    public void prepareBoard(int mode) {
        switch(mode) {
            //Normal game layout
            case 0: {
            BOARD[3][3] = 2;
            BOARD[4][4] = 2;
            BOARD[3][4] = 1;
            BOARD[4][3] = 1;
            break;
            }
            
            //Corner test layout
            case 1: {
            BOARD[3][3] = 2;
            BOARD[4][4] = 2;
            BOARD[3][4] = 1;
            BOARD[4][3] = 1;
            break;
            }
            
        }
       
    }
    
    /**
     * checks if a player (1 for black, 2 for white) may make a valid move
     * at that particular square. 
     * @param row
     * @param col
     * @return true if yes, false if no.
     */
    public boolean canMove(int row, int col, int player) {
        // Initialize boolean legal as false
        boolean legal = false;
		
        // If the cell is empty, begin the search
	// If the cell is not empty there is no need to check anything 
	// so the algorithm returns boolean legal as is
        if (BOARD[row][col] == 0) {
            // Initialize variables
            int posX;
            int posY;
            boolean found;
            int current;
			
            // Searches in each direction
            // x and y describe a given direction in 9 directions
            // 0, 0 is redundant and will break in the first check
            for (int x = -1; x <= 1; x++) {
		for (int y = -1; y <= 1; y++) {
                    // Variables to keep track of where the algorithm is and
                    // whether it has found a valid move
                    posX = col + x;
                    posY = row + y;
                    found = false;
                    current = BOARD[posY][posX];
					
                    // Check the first cell in the direction specified by x and y
                    // If the cell is empty, out of bounds or contains the same color
                    // skip the rest of the algorithm to begin checking another direction
                    if (current == -1 || current == 0 || current == player) {
			continue;
                    }
					
                    // Otherwise, check along that direction
                    while (!found) {
			posX += x;
			posY += y;
			current = BOARD[posY][posX];
						
			// If the algorithm finds another piece of the same color along a direction
			// end the loop to check a new direction, and set legal to true
			if (current == player) {
                            found = true;
                            legal = true;
			
			}
			// If the algorithm reaches an out of bounds area or an empty space
			// end the loop to check a new direction, but do not set legal to true yet
			else if (current == -1 || current == 0) {
                            found = true;
			}
                    }
		}
            }
	}

        return legal;
    }
    
    /**
     * attempts to make a move. Returns , flipping all appropriate chips to the new colour
     * It will also update its internal board model if the move is legal
     * @param row the row index at which the piece is
     * @param col the col index at which the piece is
     * @param player the player
     * @return the number of chips captured; or 0 if the move is illegal.   
     */
    public int tryMove(int row, int col, int player) {
        if(canMove(row, col, player)) {
            
        }
        return 0;
    }
    
    /**
     * Returns true if the given player has a valid move they can do at all, anywhere on the 
     * board.
     * @param player the player to test move for
     * @return true if the given player has a valid move they can do at all, anywhere on the 
     * board, and false otherwise
     */
    public boolean moveTest(int player) {
        for(int row = 0; row < BOARD_SIZE; row++) {
            for(int col = 0; col < BOARD_SIZE; col++) {
                if(canMove(row, col, player)) return true;
            }   
        }
        return false;
    }
    
    /**
     * Counts the total number of pieces the specified player has on the board
     * @param player the player whose chip count to return
     * @return the total number of pieces the specified player has on the board
     */
    public int chipCount(int player) {
        int chipCount = 0;
        for(int row = 0; row < BOARD_SIZE; row++) {
            for(int col = 0; col < BOARD_SIZE; col++) {
                if(BOARD[row][col] == player)
                    chipCount++;
            }   
        }
        return chipCount;
    }
    
   
}
