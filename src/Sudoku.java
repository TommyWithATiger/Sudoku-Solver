import java.io.IOException;
import java.util.Arrays;

public class Sudoku {

    private int[] grid = new int[81];
    private int[][] gridSolutions = new int[81][];

    public static final int ROWS = 9, COLUMNS = 9;

    private Sudoku() {
    }

    public void removeNonValidSolutionsRow(int row, int[] ints) {
        int max = (row + 1) * 9;
        for (int index = row * 9; index < max; index++) {
            if (grid[index] != 0) {
                ints[grid[index] - 1] = 0;
            }
        }
    }

    public void removeNonValidSolutionsColumn(int column, int[] ints) {
        for (int index = column; index < 81; index += 9) {
            if (grid[index] != 0) {
                ints[grid[index] - 1] = 0;
            }
        }
    }

    public void removeNonValidSolutionsSquare(int row, int column, int[] ints) {
        int minRow = (row / 3) * 27, minColumn = (column / 3) * 3;
        for (int rowIndex = minRow; rowIndex < minRow + 27; rowIndex += 9) {
            for (int index = minColumn + rowIndex; index < minColumn + 3 + rowIndex; index++) {
                if (grid[index] != 0) {
                    ints[grid[index] - 1] = 0;
                }
            }
        }
    }


    public static int[] getPossibleValues() {
        return new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
    }

    private Sudoku(Sudoku toCopy) {
        int[] gridOther = toCopy.grid;
        for (int index = 0; index < 81; index++) {
            if (gridOther[index] == 0) {
                gridSolutions[index] = Arrays.copyOf(toCopy.gridSolutions[index], toCopy.gridSolutions[index].length);
            } else {
                grid[index] = gridOther[index];
            }
        }
    }

    private void updateRow(int row, int value) {
        for (int index = row; index < row + 9; index++) {
            if (grid[index] == 0) {
                gridSolutions[index][value] = 0;
            }
        }
    }

    private void updateColumn(int column, int value) {
        for (int index = column; index < 81; index += 9) {
            if (grid[index] == 0) {
                gridSolutions[index][value] = 0;
            }
        }
    }

    private void updateSubgrid(int row, int column, int value) {
        for (int rowIndex = row; rowIndex < row + 27; rowIndex += 9) {
            for (int index = column + rowIndex; index < column + 3 + rowIndex; index++) {
                if (grid[index] == 0) {
                    gridSolutions[index][value] = 0;
                }
            }
        }
    }

    public static Sudoku fromString(String data) {
        Sudoku sudoku = new Sudoku();
        if (data.length() > 81) data = data.substring(0, 81);
        for (int index = 0; index < data.length(); index++) {
            if (data.charAt(index) > 48 && data.charAt(index) < 58) {
                sudoku.grid[index] = data.charAt(index) - 48;
            }
        }
        sudoku.setUpPossibleSolutions();
        return sudoku;
    }

    public int getValue(int row, int column) {
        return grid[row * 9 + column];
    }


    @Override
    public String toString() {
        String output = "┏━┳━┳━┳━┳━┳━┳━┳━┳━┓\n";
        for (int row = 0; row < ROWS; row++) {
            output += "┃";
            for (int column = 0; column < COLUMNS; column++) {
                if (getValue(row, column) != 0) {
                    output += getValue(row, column);
                } else {
                    output += " ";
                }
                output += "┃";
            }
            if (row < ROWS - 1) {
                output += "\n┣━╋━╋━╋━╋━╋━╋━╋━╋━┫\n";
            } else {
                output += "\n┗━┻━┻━┻━┻━┻━┻━┻━┻━┛";
            }
        }
        return output;
    }

    public Sudoku solve() {
        int bestAmount = 10;
        int bestIndex = -1;
        for (int index = 0; index < 81; index++) {
            if (grid[index] == 0) {
                int length = ArrayUtils.nonZero(gridSolutions[index]);
                if (length == 1) {
                    setValue(index, ArrayUtils.getFirstNonZero(gridSolutions[index]));
                } else if (length < bestAmount) {
                    if (length == 0) return null;
                    bestAmount = length;
                    bestIndex = index;
                }
            }
        }
        return goDeeper(bestIndex);
    }

    private Sudoku goDeeper(int bestIndex){
        if (bestIndex == -1) {
            return this;
        }

        while (ArrayUtils.nonZero(gridSolutions[bestIndex]) != 0) {
            int value = ArrayUtils.getFirstNonZero(gridSolutions[bestIndex]);
            Sudoku copy = new Sudoku(this);
            copy.setValue(bestIndex, value);
            copy = copy.solve();
            if (copy == null) {
                gridSolutions[bestIndex][value - 1] = 0;
            } else {
                return copy;
            }
        }

        return null;
    }

    private void setValue(int index, int value) {
        grid[index] = value--;
        updateRow((index / 9) * 9, value);
        updateColumn(index % 9, value);
        updateSubgrid((index / 27) * 27, ((index % 9) / 3) * 3, value);
    }

    public void setUpPossibleSolutions() {
        for (int index = 0; index < 81; index++) {
            if (grid[index] == 0) {
                setPossibleSolutions(index);
            }
        }
    }

    private void setPossibleSolutions(int index) {
        int[] possible = Sudoku.getPossibleValues();
        removeNonValidSolutionsRow(index / 9, possible);
        removeNonValidSolutionsColumn(index % 9, possible);
        removeNonValidSolutionsSquare(index / 9, index % 9, possible);

        gridSolutions[index] = possible;
    }

    public static void main(String[] args) throws IOException {
        Sudoku sudoku = fromString("..............3.85..1.2.......5.7.....4...1...9.......5......73..2.1........4...9");
        Sudoku sudoku1 = fromString(".......12........3..23..4....18....5.6..7.8.......9.....85.....9...4.5..47...6...");
        Sudoku sudoku2 = fromString(".2..5.7..4..1....68....3...2....8..3.4..2.5.....6...1...2.9.....9......57.4...9..");
        Sudoku sudoku3 = fromString("........3..1..56...9..4..7......9.5.7.......8.5.4.2....8..2..9...35..1..6........");
        Sudoku sudoku4 = fromString("12.3....435....1....4........54..2..6...7.........8.9...31..5.......9.7.....6...8");
        Sudoku sudoku5 = fromString("1.......2.9.4...5...6...7...5.9.3.......7.......85..4.7.....6...3...9.8...2.....1");
        Sudoku sudoku6 = fromString(".......39.....1..5..3.5.8....8.9...6.7...2...1..4.......9.8..5..2....6..4..7.....");
        Sudoku sudoku7 = fromString("12.3.....4.....3....3.5......42..5......8...9.6...5.7...15..2......9..6......7..8");
        Sudoku sudoku8 = fromString("..3..6.8....1..2......7...4..9..8.6..3..4...1.7.2.....3....5.....5...6..98.....5.");
        Sudoku sudoku9 = fromString("1.......9..67...2..8....4......75.3...5..2....6.3......9....8..6...4...1..25...6.");
        Sudoku sudoku10 = fromString("..9...4...7.3...2.8...6...71..8....6....1..7.....56...3....5..1.4.....9...2...7..");
        Sudoku sudoku11 = fromString("....9..5..1.....3...23..7....45...7.8.....2.......64...9..1.....8..6......54....7");
        Sudoku sudoku12 = fromString("4...3.......6..8..........1....5..9..8....6...7.2........1.27..5.3....4.9........");
        Sudoku sudoku13 = fromString("7.8...3.....2.1...5.........4.....263...8.......1...9..9.6....4....7.5...........");
        Sudoku sudoku14 = fromString("3.7.4...........918........4.....7.....16.......25..........38..9....5...2.6.....");
        Sudoku sudoku15 = fromString("........8..3...4...9..2..6.....79.......612...6.5.2.7...8...5...1.....2.4.5.....3");
        Sudoku sudoku16 = fromString(".......1.4.........2...........5.4.7..8...3....1.9....3..4..2...5.1........8.6...");
        Sudoku sudoku17 = fromString(".......12....35......6...7.7.....3.....4..8..1...........12.....8.....4..5....6..");
        Sudoku sudoku18 = fromString("1.......2.9.4...5...6...7...5.3.4.......6........58.4...2...6...3...9.8.7.......1");
        Sudoku sudoku19 = fromString(".....1.2.3...4.5.....6....7..2.....1.8..9..3.4.....8..5....2....9..3.4....67.....");
        System.out.println(SudokuValidator.isValid(new Sudoku(sudoku).solve()));

        System.in.read();
        long time = System.nanoTime();
        for (int i = 0; i < 50; i++) {
            new Sudoku(sudoku).solve();
            new Sudoku(sudoku1).solve();
            new Sudoku(sudoku2).solve();
            new Sudoku(sudoku3).solve();
            new Sudoku(sudoku4).solve();
            new Sudoku(sudoku5).solve();
            new Sudoku(sudoku6).solve();
            new Sudoku(sudoku7).solve();
            new Sudoku(sudoku8).solve();
            new Sudoku(sudoku9).solve();
            new Sudoku(sudoku10).solve();
            new Sudoku(sudoku11).solve();
            new Sudoku(sudoku12).solve();
            new Sudoku(sudoku13).solve();
            new Sudoku(sudoku14).solve();
            new Sudoku(sudoku15).solve();
            new Sudoku(sudoku16).solve();
            new Sudoku(sudoku17).solve();
            new Sudoku(sudoku18).solve();
            new Sudoku(sudoku19).solve();
        }
        System.out.println("1x:  " + ((System.nanoTime() - time) / 50) / Math.pow(10, 9));
        System.out.println("50x: " + (System.nanoTime() - time) / Math.pow(10, 9));
    }


}
