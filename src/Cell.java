public class Cell {

    private final int row, column;
    private int value;
    private int[] possibleSolutions;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Cell(Cell cell){
        row = cell.row;
        column = cell.column;
        if (!cell.isSet()){
            setPossibleSolutions(cell.getPossibleSolutions());
        } else {
            value = cell.value;
        }
    }

    public void setPossibleSolutions(int[] possibleSolutions) {
        this.possibleSolutions = ArrayUtils.getCopy(possibleSolutions);
    }

    public boolean isSet() {
        //return isSet;
        return value != 0;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setValueQuiet(int value){
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int[] getPossibleSolutions() {
        return possibleSolutions;
    }

    public int possibleSolutions(){
        return possibleSolutions.length;
    }

    public void setPossibleSolutions(Sudoku sudoku) {
        int[] possible = Sudoku.getPossibleValues();
        sudoku.removeNonValidSolutionsRow(row, possible);
        sudoku.removeNonValidSolutionsColumn(column, possible);
        sudoku.removeNonValidSolutionsSquare(row, column, possible);

        possibleSolutions = ArrayUtils.clean(possible);
    }

    public void updatePossibleSolutions(int value) {
        possibleSolutions = ArrayUtils.removeIfExists(possibleSolutions, value);
    }

}
