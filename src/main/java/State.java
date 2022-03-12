public class State {
    private Board board;
    private int guessedValue;
    private Position position;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setGuessedValue(int guessedValue) {
        this.guessedValue = guessedValue;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void addCopyOfBoard(Board board) {
        try {
            Board boardToSave = board.deepCopy();
            setBoard(boardToSave);
        } catch (CloneNotSupportedException e) {
            System.out.println("Deep copy not executed correctly");
        }
    }

    public Board getBoardWithoutPreviouslyGuessedValue() {
        Board board = getBoard();
        int horizontalPos = position.getHorizontal();
        int verticalPos = position.getVertical();
        SudokuElement sudokuElement = board.getSudokuElement(horizontalPos, verticalPos);
        sudokuElement.removeFromPossibleValues(guessedValue);
        return board;
    }


}
