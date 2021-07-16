
package othello;

import java.util.Arrays;


public class OthelloModel {
    
    public static final int NORMAL=0;
    public static final int CORNER_TEST=1;
    public static final int OUTER_TEST=2;
    public static final int TEST_CAPTURE=3;
    public static final int TEST_CAPTURE2=4;
    public static final int UNWINNABLE=5;
    public static final int INNER_TEST=6;
    public static final int ARROW=7;
    public static final int EMPTY=0;
    public static final int BLACK=1;
    public static final int WHITE=2;
    private final int BOARD_SIZE = 8;
    private int[][] board;
    public final static int PLAYER_1 = 1;
    public final static int PLAYER_2= 2;
    private int lastRowDelta = 0;
    private int lastColDelta = 0;
    private int lastX = 0;
    private int lastY = 0;
    private int opponent = 0;
    
    public OthelloModel() {
        prepareBoard(NORMAL);
    }
    
    /**
     * Returns a c of the current board to prevent other classes from
     * making changes to the actual board directly
     * @return the board clone
     */
    public int[][] getBoard() {
        
        
        return board.clone();
    }
    
    /**
     * Returns the contents of a square
     * @param row the row number of the square
     * @param col the column number of the square
     * @return the contents of a given square (0 for empty, 1 for black, 2 for white)
     */
    public int getSquare(int row, int col) {
        return board[col][row];
    }
    
    /**
     * This method prepares a board layout based on the mode
     * @param mode the mode for which to prepare the board layout
     */
    public void prepareBoard(int mode)
	{
		switch (mode)
		{
		case CORNER_TEST: 
			board = new int[][]{
				{2, 0, 0, 0, 0, 0, 0, 1},
				{0, 1, 0, 0, 0, 0, 2, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 1, 0, 0, 0, 0, 1, 0},
                                {2, 0, 0, 0, 0, 0, 0, 2}};
                                break;
                
		case OUTER_TEST:
			board = new int[][] {
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 2, 2, 2, 2, 2, 2, 0},
				{0, 2, 1, 1, 1, 1, 2, 0},
				{0, 2, 1, 0, 0, 1, 2, 0},
				{0, 2, 1, 0, 0, 1, 2, 0},
				{0, 2, 1, 1, 1, 1, 2, 0},
				{0, 2, 2, 2, 2, 2, 2, 0},
				{0, 0, 0, 0, 0, 0, 0, 0}};
			break;
                
		case INNER_TEST:
			board = new int[][] {
				{2, 2, 2, 2, 2, 2, 2, 2},
				{2, 0, 0, 0, 0, 0, 0, 2},
				{2, 0, 2, 2, 2, 2, 0, 2},
				{2, 0, 2, 1, 1, 2, 0, 2},
				{2, 0, 2, 1, 1, 2, 0, 2},
				{2, 0, 2, 2, 2, 2, 0, 2},
				{2, 0, 0, 0, 0, 0, 0, 2},
				{2, 2, 2, 2, 2, 2, 2, 2}};
			break;
                
		case UNWINNABLE:
			board = new int[][] {
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0}};
			break;
                
		case TEST_CAPTURE:
			board  = new int[][]{
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 1, 1, 1, 1, 1, 1, 0},
				{0, 1, 1, 1, 1, 1, 1, 0},
				{0, 1, 2, 2, 2, 1, 1, 0},
				{0, 1, 2, 0, 2, 1, 1, 0},
				{0, 1, 2, 2, 2, 1, 1, 0},
				{0, 1, 1, 1, 1, 1, 1, 0},
				{0, 0, 0, 0, 0, 0, 0, 0}};
				break;
				
		case TEST_CAPTURE2:
			board = new int[][]{
				{1, 1, 1, 1, 1, 1, 1, 1},
				{1, 1, 1, 1, 1, 1, 1, 1},
				{1, 2, 2, 2, 1, 2, 1, 1},
				{1, 2, 2, 2, 2, 2, 1, 1},
				{1, 2, 2, 0, 2, 2, 1, 1},
				{1, 2, 2, 2, 2, 1, 1, 1},
				{1, 2, 1, 2, 2, 2, 1, 1},
				{1, 1, 1, 1, 1, 1, 1, 1}};
				break;
                
                case ARROW:
                        board = new int[][]{
                                {0, 0, 0, 1, 1, 0, 0, 0},
                                {0, 0, 1, 1, 1, 1, 0, 0},
                                {0, 1, 0, 1, 1, 0, 1, 0},
                                {1, 0, 0, 1, 1, 0, 0, 1},
                                {0, 0, 0, 1, 1, 0, 0, 0},
                                {0, 0, 0, 1, 1, 0, 0, 0},
                                {0, 0, 0, 1, 1, 0, 0, 0},
                                {0, 0, 0, 1, 1, 0, 0, 0}};
                break;
                
		default:
			board = new int[][]{
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 2, 1, 0, 0, 0},
				{0, 0, 0, 1, 2, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0}};
		}	
	}
    
    /**
     * checks if a player (1 for black, 2 for white) may make a valid move
     * at that particular square. 
     * @param row the row index of the square at which the player would like to move
     * @param col the col index of the square at which the player would like to move
     * @return true if yes, false if no.
     */
    public boolean canMove(int row, int col, int player) {
        
        int rowdelta = 0;     /* Row increment around a square    */
        int coldelta = 0;     /* Column increment around a square */
        int x = 0;            /* Row index when searching         */
        int y = 0;            /* Column index when searching      */
  
        /* Set the opponent            */
        opponent = (player == 1)? 2 : 1; 
        /* Check all the squares around the blank square  */ 
        /* for the opponents counter                      */
        for(rowdelta = -1; rowdelta <= 1; rowdelta++)
            for(coldelta = -1; coldelta <= 1; coldelta++) { 
            /* Don't check outside the array, or the current square */
            if(row + rowdelta < 0 || row + rowdelta >= BOARD_SIZE ||
                col + coldelta < 0 || col + coldelta >= BOARD_SIZE || 
                                       (rowdelta==0 && coldelta==0)) continue;
           /* Now check the square */
           if(board[row + rowdelta][col + coldelta] == opponent) {
                /* If we find the opponent, move in the delta direction  */
                /* over opponent counters searching for a player counter */
                x = row + rowdelta;                /* Move to          */
                y = col + coldelta;                /* opponent square  */

                /* Look for a player square in the delta direction */
                while(true){
                    x += rowdelta;                  /* Go to next square */
                    y += coldelta;                  /* in delta direction*/

                    /* If we move outside the array, give up */
                    if(x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE) {
                        break;
                    }
                     
                    /* If we find a blank square, give up */ 
                    if(board[x][y] == 0) {
                        break;
                    }
                    
                    /*  If the square has a player counter */
                    /*  then we have a valid move          */
                    if(board[x][y] == player){
                        lastRowDelta = rowdelta;
                        lastColDelta = coldelta;
                        lastX = x;
                        lastY = y;
                        return true;
                    }
                } 
           } 
         }  
       return false;
    }
    
    
    /**
     * attempts to make a move.
     * It will also update its internal board model if the move is legal
     * @param row the row index at which to attempt the move
     * @param col the col index at which to attempt the move
     * @param player the player
     * @return the number of chips captured; or 0 if the move is illegal,
     * flipping all appropriate chips to the new colour
     */
    public int tryMove(int row, int col, int player) {
        int chipsCaptured = 0;
        if(canMove(row, col, player)) {
            board[row][col] = player;
            while(board[lastX -= lastRowDelta][lastY -= lastColDelta] == opponent) {
                /* Opponent? */
               board[lastX][lastY] = player;    /* Yes, change it */
               chipsCaptured++;
            }
            
        }
        return chipsCaptured;
    }
    
  public boolean validMove(int r, int c, int color) 
	{
		// Initialize boolean legal as false
		boolean legal = false;
		
		// If the cell is empty, begin the search
		// If the cell is not empty there is no need to check anything 
		// so the algorithm returns boolean legal as is
		if (board[r][c] == EMPTY)
		{
			// Initialize variables
			int posX;
			int posY;
			boolean found;
			int current;
			
			// Searches in each direction
			// x and y describe a given direction in 9 directions
			// 0, 0 is redundant and will break in the first check
			for (int x = -1; x <= 1; x++)
			{
				for (int y = -1; y <= 1; y++)
				{
					// Variables to keep track of where the algorithm is and
					// whether it has found a valid move
					posX = c + x;
					posY = r + y;
					found = false;
					current = board[posY][posX];
					
					// Check the first cell in the direction specified by x and y
					// If the cell is empty, out of bounds or contains the same color
					// skip the rest of the algorithm to begin checking another direction
					if (current == -1 || current == 0 || current == color)
					{
						continue;
					}
					
					// Otherwise, check along that direction
					while (!found)
					{
						posX += x;
						posY += y;
						current = board[posY][posX];
						
						// If the algorithm finds another piece of the same color along a direction
						// end the loop to check a new direction, and set legal to true
						if (current == color)
						{
							found = true;
							legal = true;
							
						
						}
						// If the algorithm reaches an out of bounds area or an empty space
						// end the loop to check a new direction, but do not set legal to true yet
						else if (current == -1 || current == 0)
						{
							found = true;
						}
					}
				}
			}
		}

        return legal;
    }
    
    /**
     * Checks if the given player has a valid move they can do at all, anywhere on the 
     * board.
     * @param player the player to test the move for
     * @return {@code true} if the given player has a valid move they can do at all, anywhere on the 
     * board, and false otherwise
     */
    public boolean moveTest(int player) {
        for(int row = 0; row < BOARD_SIZE; row++) {
            for(int col = 0; col < BOARD_SIZE; col++) {
                if(canMove(row, col, player)) 
                    return true;
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
                if(board[row][col] == player)
                    chipCount++;
            }   
        }
        System.out.println("Player" + player + " chip count: " + chipCount);
        return chipCount;
    }
    
   
}
