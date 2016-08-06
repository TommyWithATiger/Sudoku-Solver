import java.io.IOException;

public class Sudoku {

    public static int count = 0;
    public static int amount = 0;

    public static final int COLUMNS = 9, ROWS = 9;
    private Cell[][] cells = new Cell[ROWS][COLUMNS];

    private Sudoku() {
        initiateCells();
    }

    private void initiateCells() {
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                cells[row][column] = new Cell(row, column);
            }
        }
    }

    private void initiateCells(Sudoku sudoku) {
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                cells[row][column] = new Cell(sudoku.cells[row][column]);
            }
        }
    }

    public void removeNonValidSolutionsRow(int row, int[] ints) {
        for (int column = 0; column < COLUMNS; column++) {
            if (cells[row][column].isSet()) {
                ints[cells[row][column].getValue() - 1] = 0;
            }
        }
    }

    public void removeNonValidSolutionsColumn(int column, int[] ints) {
        for (int row = 0; row < ROWS; row++) {
            if (cells[row][column].isSet()) {
                ints[cells[row][column].getValue() - 1] = 0;
            }
        }
    }

    public void removeNonValidSolutionsSquare(int row, int column, int[] ints) {
        int minRow = (row / 3) * 3, minColumn = (column / 3) * 3;
        for (int currentRow = minRow; currentRow < minRow + 3; currentRow++) {
            for (int currentColumn = minColumn; currentColumn < minColumn + 3; currentColumn++) {
                if (cells[currentRow][currentColumn].isSet()) {
                    ints[cells[currentRow][currentColumn].getValue() - 1] = 0;
                }
            }
        }
    }


    public static int[] getPossibleValues() {
        return new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
    }

    private Sudoku(Sudoku toCopy) {
        initiateCells(toCopy);
    }


    public void updateClose(int row, int column, int value) {
        updateRow(row, value);
        updateColumn(column, value);
        updateSubgrid(row, column, value);
    }

    private void updateRow(int row, int value) {
        for (int column = 0; column < COLUMNS; column++) {
            if (!cells[row][column].isSet()) {
                cells[row][column].updatePossibleSolutions(value);
            }
        }
    }

    private void updateColumn(int column, int value) {
        for (int row = 0; row < ROWS; row++) {
            if (!cells[row][column].isSet()) {
                cells[row][column].updatePossibleSolutions(value);
            }
        }
    }

    private void updateSubgrid(int row, int column, int value) {
        int minRow = (row / 3) * 3, minColumn = (column / 3) * 3;
        for (int currentRow = minRow; currentRow < minRow + 3; currentRow++) {
            for (int currentColumn = minColumn; currentColumn < minColumn + 3; currentColumn++) {
                if (!cells[currentRow][currentColumn].isSet()) {
                    cells[currentRow][currentColumn].updatePossibleSolutions(value);
                }
            }
        }
    }

    public static Sudoku fromString(String data) {
        Sudoku sudoku = new Sudoku();
        if (data.length() > 81) data = data.substring(0, 81);
        int index = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                if (index >= data.length()) break;
                if (data.charAt(index) > 48 && data.charAt(index) < 58) {
                    sudoku.cells[row][column].setValueQuiet(data.charAt(index) - 48);
                }
                index++;
            }
        }
        sudoku.setUpPossibleSolutions();
        return sudoku;
    }

    public int getValue(int row, int column) {
        if (cells[row][column].isSet()) return cells[row][column].getValue();
        return 0;
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
        System.out.println(SudokuValidator.isValid(new Sudoku(sudoku.solve())));

        System.in.read();
        long time = System.nanoTime();
        for (int i = 0; i < 50; i++) {
            new Sudoku(sudoku.solve());
            new Sudoku(sudoku1.solve());
            new Sudoku(sudoku2.solve());
            new Sudoku(sudoku3.solve());
            new Sudoku(sudoku4.solve());
            new Sudoku(sudoku5.solve());
            new Sudoku(sudoku6.solve());
            new Sudoku(sudoku7.solve());
            new Sudoku(sudoku8.solve());
            new Sudoku(sudoku9.solve());
            new Sudoku(sudoku10.solve());
            new Sudoku(sudoku11.solve());
            new Sudoku(sudoku12.solve());
            new Sudoku(sudoku13.solve());
            new Sudoku(sudoku14.solve());
            new Sudoku(sudoku15.solve());
            new Sudoku(sudoku16.solve());
            new Sudoku(sudoku17.solve());
            new Sudoku(sudoku18.solve());
            new Sudoku(sudoku19.solve());
        }
        System.out.println("1x:  " + ((System.nanoTime() - time) / 50) / Math.pow(10, 9));
        System.out.println("50x: " + (System.nanoTime() - time) / Math.pow(10, 9));
        System.out.println(count);
        System.out.println(amount);
        System.out.println(count / (double) amount);
    }

    @Override
    public String toString() {
        String output = "┏━┳━┳━┳━┳━┳━┳━┳━┳━┓\n";
        for (int row = 0; row < ROWS; row++) {
            output += "┃";
            for (int column = 0; column < COLUMNS; column++) {
                if (cells[row][column].isSet()) {
                    output += cells[row][column].getValue();
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
        int count = 0;
        Cell best = null;
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                if (!cells[row][column].isSet()) {
                    int length = cells[row][column].possibleSolutions();
                    if (length == 1) {
                        count++;
                        setValue(row, column, cells[row][column].getPossibleSolutions()[0]);
                    } else if (length < bestAmount) {
                        bestAmount = length;
                        best = cells[row][column];
                    }
                }
            }
        }

        this.count += count;
        this.amount++;

        if (best == null) {
            return this;
        }

        best.setPossibleSolutions(this);
        for (int value : best.getPossibleSolutions()) {
            Sudoku copy = new Sudoku(this);
            copy.setValue(best.getRow(), best.getColumn(), value);
            Sudoku copySolved = copy.solve();
            if (copySolved != null) {
                return copySolved;
            }
        }

        return null;
    }

    private void setValue(int row, int column, int value){
        cells[row][column].setValue(value);
        updateClose(row, column, value);
    }

    public void setUpPossibleSolutions() {
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                if (!cells[row][column].isSet()) {
                    cells[row][column].setPossibleSolutions(this);
                }
            }
        }
    }

}
