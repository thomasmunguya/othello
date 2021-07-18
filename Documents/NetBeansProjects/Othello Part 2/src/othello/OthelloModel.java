
package othello;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
   
    private int[][] board;
  
    //a variable to keep track of the opposing player for the current player
    private int opponent = 0;
    
    public OthelloModel() {
        prepareBoard(NORMAL);
    }
    
    /**
     * Returns a clone of the current board to prevent other classes from
     * making changes to the actual board
     * @return the board clone
     */
    public int[][] getBoard() {
        return board.clone();
    }
    
    /**
     * Returns the contents of a square
     * @param row the row index of the square
     * @param col the column index of the square
     * @return the contents of a given square (0 for empty, 1 for black, 2 for white)
     */
    public int getSquare(int row, int col) {
        return board[col][row];
    }
    
    /**
     * This method prepares a board layout based on the mode
     * @param mode the mode in which to prepare the board layout
     */
    public void prepareBoard(int mode) {
        
        switch (mode) {
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
     * at a particular square. 
     * @param row the row index of the square at which the player would like to move
     * @param col the col index of the square at which the player would like to move
     * @param player the player that wants to make a move
     * @return true if yes, false if no.
     */
    public boolean canMove(int row, int col, int player) {
        
        int rowdelta = 0;     // Row increment around a square    
        int coldelta = 0;     // Column increment around a square
        int x = 0;            // Row index when searching         
        int y = 0;            // Column index when searching     
  
        // Set the opponent           
        opponent = (player == BLACK) ? WHITE : BLACK;
        
        // Check all the squares around the blank square   
        // for the opponents counter                      
        for(rowdelta = -1; rowdelta <= 1; rowdelta++)
            for(coldelta = -1; coldelta <= 1; coldelta++) { 
            // Don't check outside the array, or the current square 
            if(row + rowdelta < 0 || row + rowdelta >= OthelloViewController.BOARD_SIZE ||
                col + coldelta < 0 || col + coldelta >= OthelloViewController.BOARD_SIZE || 
                                       (rowdelta==0 && coldelta==0)) continue;
           // Now check the square
           if(board[row + rowdelta][col + coldelta] == opponent) {
                //If we find the opponent, move in the delta direction  
                //over opponent pieces searching for a player piece 
                x = row + rowdelta;                // Move to          
                y = col + coldelta;                // opponent square  

                //Look for a player square in the delta direction
                while(true){
                    x += rowdelta;                  // Go to next square 
                    y += coldelta;                  // in delta direction

                    //If we move outside the array, give up
                    if(x < 0 || x >= OthelloViewController.BOARD_SIZE || y < 0 || y >= OthelloViewController.BOARD_SIZE) {
                        break;
                    }
                     
                    // If we find a blank square, give up
                    if(board[x][y] == 0) {
                        break;
                    }
                    
                    //If the square has a player piece
                    //then we have a valid move
                    if(board[x][y] == player){
                        while(true) {
                            if((board[x -= rowdelta][y -= coldelta] == opponent)) {
                                return true;
                            }
                        }
                        
                    }
                } 
           } 
         }  
       return false;
    }
    
    
    /**
     * Attempts to make a move and updates the state of the board model if the move is legal
     * @param row the row index at which to attempt the move
     * @param col the col index at which to attempt the move
     * @param player the player
     * @return the number of chips captured; or 0 if the move is illegal,
     * flipping all appropriate chips to the new colour
     */
    public int tryMove(int row, int col, int player) {
        
        int chipsCaptured = 0;
        int rowDelta = 0; // Row increment
        int coldelta = 0; // Column increment
        int x = 0; // Row index for searching
        int y = 0; // Column index for searching
        
        
        opponent = (player == BLACK) ? WHITE : BLACK; // Identify opponent
        board[row][col] = player; // Place the player piece
        
        // Check all squares around this square for opponents piece
        for(rowDelta = -1 ; rowDelta <= 1 ; ++rowDelta) {
            
            for(coldelta = -1; coldelta <= 1; ++coldelta) {
                
                // Don't check off the board, or the current square
                if((row == 0 && rowDelta == -1) || row + rowDelta >= OthelloViewController.BOARD_SIZE ||
                    (col == 0 && coldelta == -1) || col + coldelta >= OthelloViewController.BOARD_SIZE ||
                    (rowDelta == 0 && coldelta == 0)) {
                    continue;
                }
                
                // Now check the square
                if(board[row + rowDelta][col + coldelta] == opponent) { 
                    // Found opponent so search in same direction for player piece
                    x = row + rowDelta; // Move to opponent
                    y = col + coldelta; // square
                
                    while(true) {
                    
                        x += rowDelta; // Move to the
                        y += coldelta; // next square
                    
                        if(x >= OthelloViewController.BOARD_SIZE || y >= OthelloViewController.BOARD_SIZE || board[x][y] == EMPTY)// If blank square or off board...
                            break; // ...give up
                    
                        // If we find the player piece, go backward from here
                        // changing all the opponents pieces to player
                        if(board[x][y] == player) {
                            while(board[x -= rowDelta][y -= coldelta] == opponent) {
                                board[x][y] = player; // If its an opponent piece change it
                                chipsCaptured++; //increment the number of chips captured
                            } 
                            break; // We are done
                        }
                    }
                }
            }
        }
        return chipsCaptured;
    }
    
    /**
     * Checks if the given player has a valid move they can do at all, anywhere on the 
     * board.
     * @param player the player to test the move for
     * @return {@code true} if the given player has a valid move they can do at all, anywhere on the 
     * board, and {@code false} otherwise
     */
    public boolean moveTest(int player) {
        for(int row = 0; row < OthelloViewController.BOARD_SIZE; row++) {
            for(int col = 0; col < OthelloViewController.BOARD_SIZE; col++) {
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
        for(int row = 0; row < OthelloViewController.BOARD_SIZE; row++) {
            for(int col = 0; col < OthelloViewController.BOARD_SIZE; col++) {
                if(board[row][col] == player)
                    chipCount++;
            }   
        }
        return chipCount;
    }
   
}
