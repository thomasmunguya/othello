
package othello;


public class OthelloModel {
    
    private static OthelloModel othelloModel;
    private final int BOARD_SIZE = 8;
    private final int[][] BOARD = new int[BOARD_SIZE][BOARD_SIZE];
    public final static int PLAYER_1 = 1;
    public final static int PLAYER_2= 2;
    private int lastRowDelta = 0;
    private int lastColDelta = 0;
    private int lastX = 0;
    private int lastY = 0;
    private int opponent = 0;
    
    private OthelloModel() {
        prepareBoard(0);
    }
    
    /**
     * Returns a copy of the current board to prevent other classes from
     * making changes to the actual board directly
     * @return the board clone
     */
    public int[][] getBoard() {
        return BOARD.clone();
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
           if(BOARD[row + rowdelta][col + coldelta] == opponent) {
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
                    if(BOARD[x][y] == 0) {
                        break;
                    }
                    
                    /*  If the square has a player counter */
                    /*  then we have a valid move          */
                    if(BOARD[x][y] == player){
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
            while(BOARD[lastX -= lastRowDelta][lastY -= lastColDelta] == opponent) {
                /* Opponent? */
               BOARD[lastX][lastY] = player;    /* Yes, change it */
               chipsCaptured++;
            }
        
        }
        return chipsCaptured;
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
                if(BOARD[row][col] == player)
                    chipCount++;
            }   
        }
        return chipCount;
    }
    
   
}
