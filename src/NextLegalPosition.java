import java.util.Arrays;

public class NextLegalPosition {
    /*
    Thoughts:
    - The next legal position on an already legal board will not always be to add a queen since there can be
    multiple legal positions where the last added queen can be in different columns
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

        System.out.println("Next row = " + getNextRow(board, board.length - 1));

        //get next legal position of board
        System.out.println("Getting next legal position of board...");
        nextLegalPosition(board, getNextRow(board, board.length - 1));

        //print the next legal position of the board,
        System.out.println("Next legal position: ");
        printBoard(board);

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
            if(i != col && board[row][i] == 1) return true;
        }

        //check column
        for(i = 0; i < row; ++i){
            if(board[i][col] == 1) return true;

        }

        //check y = -x diagonal
        for(i = row, j = col; i >= 0 && j >= 0; --i, --j){
            if(i != row && board[i][j] == 1) return true;

        }

        //check y = x diagonal
        for(i = row, j = col; j < board.length && i >= 0; --i, ++j){
            if(i != row && board[i][j] == 1) return true;

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

    //gets the next row with a queen in it from the bottom
    public static int getNextRow(Integer[][] board, int row){
        int answer = row;
        int col = -1;

        //check to see if there is a queen in this row
        for(int i = 0; i < board.length; ++i){
            if(board[row][i] == 1){
                col = i;
            }
        }

        //if not, move up a row and repeat until one is found
        if(col == -1){
            answer = getNextRow(board, row - 1);
        }

        //if there is a queen in this row, return the row it is in
        return answer;
    }

    //returns the column of a queen in a specific row, -1 if one does not exist
    public static int getCol(Integer[][] board, int row){
        for(int i = 0; i < board.length; ++i){
            if(board[row][i] == 1){
                return i;
            }
        }
        return -1;
    }

    //function used to differentiate between a full legal board, and a not full legal board
    //calls getNextLegalPosition which is the main recursion function
    public static boolean nextLegalPosition(Integer[][] board, int row){
        int nextRow = getNextRow(board, board.length - 1);

        //if the board is legal and not full
        if(nextRow < board.length - 1 && isLegalPosition(board)){
            //call the function on a new row
            return getNextLegalPosition(board, nextRow + 1);
        }

        //otherwise, call the function on the initial row
        //this means the board is either illegal, or legal and full
        return getNextLegalPosition(board, nextRow);
    }

    //returns true if a solution exists (and manipulates board), or false if a solution does not exist
    public static boolean getNextLegalPosition(Integer[][] board, int row){
        //gets first row with a queen in it from bottom to top

        //check if row is empty
        int col = -1;
        for(int i = 0; i < board.length; ++i){
            if(board[row][i] == 1) col = i;
        }

        //if row is empty
        if(col == -1){
            //try placing a queen in each col in this row
            for(int i = 0; i < board.length; ++i){
                if(!isQueenThreatened(board, row, i)){
                    //place the queen
                    board[row][i] = 1;
                    return true;
                }
            }

            //if the queen could not be placed, remove the previous queen and try again with the row 2 rows up
            int previousRow = getNextRow(board, board.length - 1);
            int previousCol = getCol(board, previousRow);
            board[previousRow][previousCol] = 0;
            return getNextLegalPosition(board, row - 1);
        }

        //record the position of the queen in this row then remove it from a copy of the board
        int qRow = row;
        int qCol = getCol(board, row);
        board[qRow][qCol] = 0;

        //try placing a queen in each col in this row
        for (int i = 0; i < board.length; ++i) {
            //check if queen can be placed on square, and if there is not already a queen there
            if (!isQueenThreatened(board, row, i)) {

                //place the queen in board[row][i] if it's not the position it was already in
                if(i != qCol){
                    board[row][i] = 1;
                    return true;
                }
            }
        }

        //if the queen can not be placed in any col in this row, try the row above it
        return getNextLegalPosition(board, row - 1);
    }
}
