import java.util.ArrayList;

public class IsLegalPosition {
    /*
    General thoughts/ideas:
    - to check for queens on the same diagonal of another queen, we can add and subtract the row and column
    of the queen's position to get a value for that specific diagonal. For example if there is a queen at (1, 2)
    there can not be another queen on a position where row - col = -1 OR where row + col = 3. This creates a
    simple mathematical representation of the lines y=x and y=-x with said queen as the origin.

    - only need to check top side of each queen since after you check all of them, the ones to the right
    will threaten the ones to the left that did not "see" the queen to the right of it. This halves the
    calculations needed.
     */

    public static void main(String[] args) {
        //size of the board, assuming it is square
        int n = 8;

        //positions of queens, edit this to change queen positions on board (1-indexed)
        Integer[] queenPositions = {1, 6, 8, 3, 7, 0, 0, 0};

        //initialize board
        Integer[][] board = initializeBoard(n, queenPositions);

        //print the board after initialized
        printBoard(board);

        System.out.println("Is legal position: " + isLegalPosition(board));
    }

    public static Integer[][] initializeBoard(int n, Integer[] queenPositions){
        System.out.println("Initializing board...");
        Integer[][] board = new Integer[n][n];

        //fill with 0's
        for(int i = 0; i < n; ++i){
            for(int j = 0; j < n; ++j){
                board[i][j] = 0;
            }
        }

        //loop over queenPositions array and add queens to the board
        for(int i = 0; i < queenPositions.length; ++i){
            //used to zero-index the column position of the queen
            int col = queenPositions[i] - 1;

            //if col is -1 that means there is no queen in that row
            if(col != -1){
                System.out.println("Recognized queen at 1-indexed (row, col): (" + (i + 1) + ", " + (col + 1) + ")");
                board[i][col] = 1;
            }
        }

        return board;
    }

    public static void printBoard(Integer[][] board){
        System.out.println("\nBoard:");
        for(int i = 0; i < board.length; ++i){
            for(int j = 0; j < board[0].length; ++j){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    //helper function for isLegalPosition
    public static boolean isQueenThreatened(Integer[][] board, int row, int col){
        int i, j;

        //only need to check to the top of the queen

        //check row
        for(i = 0; i < board.length; ++i){
            if(i != col && board[row][i] == 1){
                System.out.println("Queen at " + row + ", " + col + " threatened on row");
                return true;
            }
        }

        //check column
        for(i = 0; i < row; ++i){
            if(board[i][col] == 1){
                System.out.println("i = " + i);
                System.out.println("Queen at " + row + ", " + col + " threatened on col at " + i + ", " + col);
                return true;
            }
        }

        //check y = -x diagonal
        for(i = row, j = col; i >= 0 && j >= 0; --i, --j){
            if(i != row && board[i][j] == 1){
                System.out.println("Queen at " + row + ", " + col + " threatened on y = -x diagonal by " + i + ", " + j);
                return true;
            }
        }

        //check y = x diagonal
        for(i = row, j = col; j < board.length && i >= 0; --i, ++j){
            if(i != row && board[i][j] == 1){
                System.out.println("Queen at " + row + ", " + col + " threatened on y = x diagonal");
                return true;
            }
        }
        return false;
    }

    public static boolean isLegalPosition(Integer[][] board) {
        //loop over the board and see if any queens are threatened
        for(int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; ++j) {
                if (board[i][j] == 1) {
                    if (isQueenThreatened(board, i, j)) return false;
                }
            }
        }
        return true;
    }
}
