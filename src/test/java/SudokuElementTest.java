import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SudokuElementTest {

    @Test
    void randomValueTest() {
        //Given
        SudokuElement sudokuElement = new SudokuElement();
        //When
        sudokuElement.guessTheValueInElement();
        //Then
        Assertions.assertNotEquals(-1,sudokuElement.getValue());
    }

}