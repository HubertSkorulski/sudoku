import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SudokuRowTestSuite {

    @Test
    void rowToStringTest() {
        //Given
        SudokuRow sudokuRow = new SudokuRow();
        //When
        String expectedRow = "| | | | | | | | | |";
        //Then
        Assertions.assertEquals(expectedRow, sudokuRow.toString());
    }
}
