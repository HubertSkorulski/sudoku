import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


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
    void handleTheWrongInputTest() {
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
    void squareToListTest() {
        //Given
        Board board = new Board();
        //SudokuElementsSetter sudokuElementsSetter = new SudokuElementsSetter();
        board.handleTheInput(new Input("1,1,1"));
        board.handleTheInput(new Input("1,2,2"));
        board.handleTheInput(new Input("1,3,3"));
        board.handleTheInput(new Input("2,1,4"));
        board.handleTheInput(new Input("2,2,5"));
        board.handleTheInput(new Input("2,3,6"));
        board.handleTheInput(new Input("3,1,7"));
        board.handleTheInput(new Input("3,2,8"));
        board.handleTheInput(new Input("3,3,9"));
        //When
        List<SudokuElement> sudokuElements = board.squareToList(1, 1);
        List<Integer> expectedList = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        //Then
        List <Integer> valuesList = sudokuElements.stream()
                .map(SudokuElement::getValue)
                .collect(Collectors.toList());
        Assertions.assertEquals(expectedList,valuesList);
    }

    @Test
    void squareToList2Test() {
        //Given
        Board board = new Board();
        //SudokuElementsSetter sudokuElementsSetter = new SudokuElementsSetter(board);
        board.handleTheInput(new Input("4,1,1"));
        board.handleTheInput(new Input("4,2,2"));
        board.handleTheInput(new Input("4,3,3"));
        board.handleTheInput(new Input("5,1,4"));
        board.handleTheInput(new Input("5,2,5"));
        board.handleTheInput(new Input("5,3,6"));
        board.handleTheInput(new Input("6,1,9"));
        board.handleTheInput(new Input("6,2,8"));
        board.handleTheInput(new Input("6,3,7"));
        //When
        List<SudokuElement> sudokuElements = board.squareToList(4, 1);
        List<Integer> expectedList = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,9,8,7));
        //Then
        List <Integer> valuesList = sudokuElements.stream()
                .map(SudokuElement::getValue)
                .collect(Collectors.toList());
        Assertions.assertEquals(expectedList,valuesList);
    }



    @Test
    void hasWrongElementsTest() {
        //Given
        Board board = new Board();
        //When
        boolean check = board.hasImpossibleToSetElements();
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
        boolean check = board.hasImpossibleToSetElements();
        //Then
        Assertions.assertTrue(check);
    }

    @Test
    void correctBoardDuplicatesInRowTest() {
        //Given
        Board board = new Board();
        board.handleTheInput(new Input("1,1,1"));
        board.handleTheInput(new Input("1,2,1"));
        //When
        boolean check = board.isCorrect();
        //Then
        Assertions.assertFalse(check);
    }

    @Test
    void correctBoardDuplicatesInColumnTest() {
        //Given
        Board board = new Board();
        board.handleTheInput(new Input("1,1,1"));
        board.handleTheInput(new Input("7,1,1"));
        //When
        boolean check = board.isCorrect();
        //Then
        Assertions.assertFalse(check);
    }

    @Test
    void correctBoardDuplicatesInSquareTest() {
        //Given
        Board board = new Board();
        board.handleTheInput(new Input("4,8,3"));
        board.handleTheInput(new Input("5,7,3"));
        //When
        boolean check = board.isCorrect();
        //Then
        Assertions.assertFalse(check);
    }

    @Test
    void hasDuplicatesTest() {
        //Given
        Board board = new Board();
        List<Integer> testList = new ArrayList<>(Arrays.asList(1,2,3,1));
        //When
        boolean check = board.hasDuplicates(testList);
        //Then
        Assertions.assertTrue(check);
    }

    @Test
    void hasDuplicates2Test() {
        //Given
        Board board = new Board();
        List<Integer> testList = new ArrayList<>(Arrays.asList(1,2,3));
        //When
        boolean check = board.hasDuplicates(testList);
        //Then
        Assertions.assertFalse(check);
    }


}