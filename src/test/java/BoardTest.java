import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class BoardTest {


    @Test
    void findElementTest() {
        //Given
        Board board = new Board();
        int horizontalPos = 2;
        int verticalPos = 3;
        int horIndex = horizontalPos - 1;
        int verIndex = verticalPos - 1;
        SudokuElement sudokuElement = board.getSudokuBoard().get(verIndex).getElement(horIndex);
        //When
        Position position = board.getElementPosition(sudokuElement);
        //Then
        Assertions.assertEquals(verticalPos,position.getVertical());
        Assertions.assertEquals(horizontalPos,position.getHorizontal());
    }

    @Test
    void handleTheInputTest() {
        //Given
        Board board = new Board();
        String input = "1,2,3";
        //When
        board.handleTheInput(new Input(input));
        //Then
        int setValue = board.getSudokuElement(2,1).getValue();
        System.out.println(board);
        Assertions.assertEquals(3,setValue);
    }

    @Test
    void handleTheInput2Test() {
        //Given
        Board board = new Board();
        String input = "1,2,e";
        //When
        board.handleTheInput(new Input(input));
        //Then
        int setValue = board.getSudokuElement(2,1).getValue();
        System.out.println(board);
        Assertions.assertEquals(-1,setValue);
    }

    @Test
    void hasWrongElementsTest() {
        //Given
        Board board = new Board();
        //When
        boolean check = board.hasWrongElements();
        //Then
        Assertions.assertFalse(check);
    }

    @Test
    void hasWrongElements2Test() {
        //Given
        Board board = new Board();
        SudokuElement sudokuElement = board.getSudokuElement(1,1);
        sudokuElement.removeFromPossibleValues(1);
        sudokuElement.removeFromPossibleValues(2);
        sudokuElement.removeFromPossibleValues(3);
        sudokuElement.removeFromPossibleValues(4);
        sudokuElement.removeFromPossibleValues(5);
        sudokuElement.removeFromPossibleValues(6);
        sudokuElement.removeFromPossibleValues(7);
        sudokuElement.removeFromPossibleValues(8);
        sudokuElement.removeFromPossibleValues(9);
        //When
        boolean check = board.hasWrongElements();
        //Then
        Assertions.assertTrue(check);
    }

}