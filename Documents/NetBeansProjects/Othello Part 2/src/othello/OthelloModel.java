
package othello;


public class OthelloModel {
    
    private static OthelloModel othelloModel;
    private final int BOARD_SIZE = 8;
    private final int[][] BOARD = new int[BOARD_SIZE][BOARD_SIZE];
    
    private OthelloModel() {
        prepareBoard(0);
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
    
    public boolean isLegalMove(int row, int col, int player) {
        boolean isValidMove = false;
        int[][] adjactantFields = {
            {-1, -1},
            {0, -1}, 
            {1, -1},
            {-1, 0},
            {1, 0}, 
            {-1, 1}, 
            {0, 1},
            {1, 1}};
        
        //if the square is not on the board, return false because a move
        //cannot be made to that position
        if(!isPositionOnBoard(row, col)) {
            return false;
        }
        
        for(int[] square: adjactantFields) {
            int tempRow = row + square[0];
            int tempCol = col + square[1];
            System.out.println(tempRow + "," + tempCol);
            if(!isPositionOnBoard(tempRow, tempCol)) {
                continue;
            }
            if(isPositionOnBoard(tempRow, tempCol) && getSquare(tempRow, tempCol) == 0) {
                isValidMove = true;
            }
        }
        if(getSquare(row, col) != 0) {
            isValidMove = false;
        }
        return isValidMove;
    }
    
    public boolean isValidMove(int row, int col, int player) {
        int rowdelta = 0;     /* Row increment around a square    */
        int coldelta = 0;     /* Column increment around a square */
        int x = 0;            /* Row index when searching         */
        int y = 0;            /* Column index when searching      */
   

        /* Set the opponent            */
        int opponent = (player == 1)? 2 : 1; 
        /* Check all the squares around the blank square  */ 
       /* for the opponents counter                      */
       for(rowdelta = -1; rowdelta <= 1; rowdelta++)
         for(coldelta = -1; coldelta <= 1; coldelta++)
         { 
           /* Don't check outside the array, or the current square */
           if(row + rowdelta < 0 || row + rowdelta >= BOARD_SIZE ||
              col + coldelta < 0 || col + coldelta >= BOARD_SIZE || 
                                       (rowdelta==0 && coldelta==0))
             continue;

           /* Now check the square */
           if(BOARD[row + rowdelta][col + coldelta] == opponent)
           {
             /* If we find the opponent, move in the delta direction  */
             /* over opponent counters searching for a player counter */
             x = row + rowdelta;                /* Move to          */
             y = col + coldelta;                /* opponent square  */

             /* Look for a player square in the delta direction */
             for(;;)
             {
               x += rowdelta;                  /* Go to next square */
               y += coldelta;                  /* in delta direction*/

               /* If we move outside the array, give up */
               if(x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE)
                 break;

               /* If we find a blank square, give up */ 
               if(BOARD[x][y] == 0)
                 break;
                /*  If the square has a player counter */
                /*  then we have a valid move          */
               if(BOARD[x][y] == player)
               {
                  return true;
               }
             } 
           } 
         }  
       return false;
    }
    
    /**
     * Checks if a position is on the board
     * @param row the column index of the position
     * @param col the row index of the position
     * @return true if the position is on the board, and false otherwise
     */
    private boolean isPositionOnBoard(int row, int col) {
        return (row < 8 && row > -1) && (col < 8 && col > -1);
    }
    
    /**
     * checks if a player (1 for black, 2 for white) may make a valid move
     * at that particular square. 
     * @param row the row index of the square at which the player would like to move
     * @param col the col index of the square at which the player would like to move
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
                    System.out.println("posY = " + posY + ", PosX = " + posX);
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
     * @param player the player to test the move for
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
