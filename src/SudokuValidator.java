import java.util.ArrayList;
import java.util.List;

public class SudokuValidator {

    public static boolean isValid(Sudoku sudoku){
        for (int row = 0; row < Sudoku.ROWS; row++){
            if (!checkRow(row, sudoku)) return false;
        }

        for (int column = 0; column < Sudoku.COLUMNS; column++){
            if (!checkColumn(column, sudoku)) return false;
        }

        for (int row = 0; row < 9; row += 3){
            for (int column = 0; column < 9; column += 3){
                if (!checkSquare(row, column, sudoku)) return false;
            }
        }
        return true;
    }

    private static boolean checkRow(int row, Sudoku sudoku){
        List<Integer> found = new ArrayList<>();
        for (int column = 0; column < Sudoku.COLUMNS; column++){
            if (!checkCell(row, column, found, sudoku)) return false;
        }
        return true;
    }

    private static boolean checkColumn(int column, Sudoku sudoku){
        List<Integer> found = new ArrayList<>();
        for (int row = 0; row < Sudoku.ROWS; row++){
            if (!checkCell(row, column, found, sudoku)) return false;
        }
        return true;
    }

    private static boolean checkSquare(int row, int column, Sudoku sudoku){
        List<Integer> found = new ArrayList<>();
        for (int currentRow = row; currentRow < row + 3; currentRow++){
            for (int currentColumn = column; currentColumn < column + 3; currentColumn++){
                if (!checkCell(currentRow, currentColumn, found, sudoku)) return false;
            }
        }
        return true;
    }

    private static boolean checkCell(int row, int column, List<Integer> ints, Sudoku sudoku){
        Integer value = sudoku.getValue(row, column);
        if (value != 0){
            if (ints.contains(value)) return false;
            ints.add(value);
        }
        return true;
    }
}
