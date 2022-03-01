import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class BoardTest {


    @Test
    void findElementTest() {
        //Given
        Board board = new Board();
        SudokuElement sudokuElement = board.getSudokuBoard().get(1).getElement(2);
        //When
        Position position = board.findElement(sudokuElement);
        //Then
        Assertions.assertEquals(1,position.getVertical());
        Assertions.assertEquals(2,position.getHorizontal());
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
}