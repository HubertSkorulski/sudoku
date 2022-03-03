import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class StateTest {

    @Test
    void getBoardWithoutPreviouslyGuessedValue() {
        //Given
        State state = new State();
        Board board = new Board();
        Position position = new Position(2,1);
        int guessedValue = 3;
        state.setGuessedValue(guessedValue);
        state.addCopyOfBoard(board);
        state.setPosition(position);
        SudokuElement guessedElement = board.getSudokuElement(2,1);
        guessedElement.setValue(guessedValue);

        //When
        board = state.getBoardWithoutPreviouslyGuessedValue();

        //Then
        SudokuElement elementWithoutPreviouslyGuessedValue = board.getSudokuElement(2,1);
        Assertions.assertFalse(elementWithoutPreviouslyGuessedValue
                .getPossibleValues().contains(guessedValue));
    }

}