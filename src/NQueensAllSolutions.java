public class NQueensAllSolutions {
    public static int solutionNumber = 1;

    public static void main(String[] args) {
        //size of the board, assuming it is square
        int n = 5;

        Integer[][] board = new Integer[n][n];

        //zeroes the board
        zeroBoard(board);

        //get the first solution for the n-queens problem with the given size
        getSolution(board);
    }


    //zeroes the board
    public static void zeroBoard(Integer[][] board){
        //fill with 0's
        for(int i = 0; i < board.length; ++i){
            for(int j = 0; j < board.length; ++j){
                board[i][j] = 0;
            }
        }
    }


    //prints the board
    public static void printBoard(Integer[][] board){
        Integer[] queens = new Integer[board.length];
        System.out.println("\nBoard " + solutionNumber++ + ":");
        for(int i = 0; i < board.length; ++i){
            for(int j = 0; j < board[0].length; ++j){
                System.out.print(board[i][j] + " ");
                if(board[i][j] == 1) queens[i] = j;
            }
            System.out.println();
        }
        System.out.print("(");
        for(int i = 0; i < queens.length; ++i){
            System.out.print((i == queens.length || i == 0 ? (queens[i] + 1) : ", " + (queens[i] + 1)));
        }
        System.out.println(")\n");
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


    //modified recursive function to solve board
    public static boolean solve(Integer[][] board, int row){
        //if all queens are placed, you are done
        if(row >= board.length){
            printBoard(board);
            return true;
        }

        //try placing queen in all cols of this row from left to right
        //added answer boolean for possible speed increase
        boolean answer = false;
        for(int i = 0; i < board.length; ++i){
            //check if move is legal
            if(!isQueenThreatened(board, row, i)){
                //place queen
                board[row][i] = 1;

                //recursion
                //result has to be true if ANY move is possible, that way you can find all solutions
                answer = (solve(board, row + 1)) || answer;

                //if placing the queen isn't valid, remove it and try again (backtracking)
                board[row][i] = 0;
            }
        }

        //if the queen can't be placed anywhere in the row, return false
        return answer;
    }


    //the main function to find the first solution of the n-queens problem
    public static boolean getSolution(Integer[][] board){
        if(solve(board, 0) == false){
            System.out.println("No solution");
            return false;
        }

        return true;

    }
}
