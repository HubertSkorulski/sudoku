public class StateBeforeGuessing {
    private Board board;
    private SudokuElement sudokuElement;
    private Position position;

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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
