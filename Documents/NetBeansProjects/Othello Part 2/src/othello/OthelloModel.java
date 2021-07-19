
package othello;

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
    
    //An array of the directions to traverse in search for a valid move
    private final int arrDirections[][] = { 
        {0 , -1} , {0 , 1}, {-1 ,0} , {1 , 0} ,
        {-1 ,-1} , {1 , -1} ,{-1 ,1}, {1 , 1}};
    
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
        
        int opponent = (player == BLACK) ? WHITE: BLACK;
        
        int x = row;
        int y = col;
        
        //Check if the field is blank
        if(board[x][y] != 0) {
            return false;
        }
        else {
            //If the field is blank
            //get the directions
            for(int i = 0; i < arrDirections.length; i++) {
                //The direction is stored for use...
                int[] direction = arrDirections[i];
                //Getting direction
                int xDir = direction[0];
                int yDir = direction[1];
                int jump = 2;
                
                //Check in every direction, if there's an opponent
                if((y + yDir) > -1 
                     && (y + yDir) < 8 
                     && (x + xDir) < 8
                     && (x + xDir) > -1) {
                    //if an opponent is found
                    if(board[x + xDir][y + yDir] == opponent) {
                        //Search while inside the board frame
                        while((y + (jump * yDir)) > -1 
                        && (y + (jump * yDir)) < 8 
                        && (x + (jump * xDir)) < 8 
                        && (x + (jump * xDir)) > -1) {
                            //looking for a piece of the current player
                            //If an empty space is found, stop
                            if(board[x + (jump * xDir)][y + (jump * yDir)] == 0) {
                                break;
                            }
                            //If a piece of the current player is found its a valid move
                            if(board[x + (jump * xDir)][y + (jump * yDir)] == player) {
                                return true;
                            }   
                            jump++;
                        }
                        
                    }
                }
               
                
           }
        }
        return false;
    }
    
    
    public int tryMove(int row, int col, int player) {
        
        int piecesCaptured = 0;
        int enemy = (player == BLACK) ? WHITE: BLACK;
        
        int x = row;
        int y = col;
        
        for(int i = 0; i < arrDirections.length; i++) {
            int[] direction = arrDirections[i];
            int xDir = direction[0];
            int yDir = direction[1];
            boolean potential = false;
            
            //if inside the board
            if((y + yDir) > -1 
                && (y + yDir) < 8 
                && (x + xDir) < 8
                && (x + xDir) > -1) {
                //if an opponent is next to the current player's piece in the direction we are headed
                if(board[x + xDir][y + yDir] == enemy) {
                    //then the direction has potential
                    potential = true;
                }
            }
            if(potential) {
                int jump = 2;
                while((y + (jump * yDir)) > -1 
                    && (y + (jump * yDir)) < OthelloViewController.BOARD_SIZE 
                    && (x + (jump * xDir)) < OthelloViewController.BOARD_SIZE 
                    && (x + (jump * xDir)) > -1) {
                    
                    //Search for a piece of the current player
                     if(board[x + (jump * xDir)][y + (jump * yDir)] == 0) {
                        break;
                     }
                     //found a piece? It's a legal move
                     if(board[x + (jump * xDir)][y + (jump * yDir)] == player) {
                           //A piece is found, everything between (X, Y) and
                           //(x + jump * xDir, y + jump * yDir) belongs to the current player!
                           //K = 1 since 0 is our own button
                        for(int k = 0; k < jump; k++) {
                            board[x + (k * xDir)][y + (k * yDir)] = player;
                        }
                        piecesCaptured++;
                        break;
                     }
                    jump++;
                }
            }
        }
        return piecesCaptured;
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
   
