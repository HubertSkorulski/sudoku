import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SudokuElementTest {

    @Test
    void randomValueTest() {
        //Given
        SudokuElement sudokuElement = new SudokuElement();
        //When
        sudokuElement.guessValue();
        //Then
        Assertions.assertNotEquals(-1,sudokuElement.getValue());
    }

    @Test
    void removePossibleValue() {
        //Given
        SudokuElement sudokuElement = new SudokuElement();
        //When
        sudokuElement.removeFromPossibleValues(3);
        //Then
        Assertions.assertFalse(sudokuElement.getPossibleValues().contains(3));
    }

    @Test
    void guessValueTest() {
        //Given
        SudokuElement sudokuElement = new SudokuElement();
        //Then

        //When


    }
}