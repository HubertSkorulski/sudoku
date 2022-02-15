public class BeforeGuessing {
    private Board board;
    private SudokuElement sudokuElement;
    private String coordinates;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public SudokuElement getGuessedElement() {
        return sudokuElement;
    }

    public void setGuessedElement(SudokuElement sudokuElement) {
        this.sudokuElement = sudokuElement;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
