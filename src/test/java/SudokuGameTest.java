import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class SudokuGameTest {


    @Test
    void guessTest() {
        //Given
        Board board = new Board();
        SudokuGame game = new SudokuGame();
        //When
        game.guess(board);
        //Then
        Assertions.assertFalse(board.getSudokuElement(1,1).isEmpty());
    }

    @Test
    void useStateBeforeTest() {
        //Given
        Board board = new Board();
        SudokuGame game = new SudokuGame();
        State state = new State();
        state.addCopyOfBoard(board);
        game.saveState(state,3,new Position(1,4));
        //When
        board = game.useStateBeforeWithoutPreviouslyGuessedValue();
        //Then
        SudokuElement previouslyGuessedElement = board.getSudokuElement(1,4);
        Assertions.assertFalse(previouslyGuessedElement.getPossibleValues().contains(3));

    }

}