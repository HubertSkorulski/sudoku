import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuSolverTestSuite {

    @Test
    void squareToListTest() {
        //Given
        Board board = new Board();
        SudokuSolver sudokuSolver = new SudokuSolver(board);
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
        List<SudokuElement> sudokuElements = sudokuSolver.squareToList(1,1);
        //Then
        sudokuElements.stream()
                .map(SudokuElement::getValue)
                .forEach(System.out::println);
    }

    @Test
    void squareToList2Test() {
        //Given
        Board board = new Board();
        SudokuSolver sudokuSolver = new SudokuSolver(board);
        board.handleTheInput(new Input("4,1,1"));
        board.handleTheInput(new Input("4,2,2"));
        board.handleTheInput(new Input("4,3,3"));
        board.handleTheInput(new Input("5,1,4"));
        board.handleTheInput(new Input("5,2,5"));
        board.handleTheInput(new Input("5,3,6"));
        board.handleTheInput(new Input("6,1,7"));
        board.handleTheInput(new Input("6,2,8"));
        board.handleTheInput(new Input("6,3,9"));
        //When
        List<SudokuElement> sudokuElements = sudokuSolver.squareToList(4,1);
        //Then
        sudokuElements.stream()
                .map(SudokuElement::getValue)
                .forEach(System.out::println);
    }

    @Test
    void hasDuplicatesTest() {
        //Given
        Board board = new Board();
        SudokuSolver sudokuSolver = new SudokuSolver(board);
        List<Integer> testList = new ArrayList<>(Arrays.asList(1,2,3,1));
        //When
        boolean check = sudokuSolver.hasDuplicates(testList);
        //Then
        Assertions.assertTrue(check);
    }

    @Test
    void hasDuplicates2Test() {
        //Given
        Board board = new Board();
        SudokuSolver sudokuSolver = new SudokuSolver(board);
        List<Integer> testList = new ArrayList<>(Arrays.asList(1,2,3));
        //When
        boolean check = sudokuSolver.hasDuplicates(testList);
        //Then
        Assertions.assertFalse(check);
    }

    @Test
    void correctBoardTest() {
        //Given
        Board board = new Board();
        board.handleTheInput(new Input("1,1,1"));
        board.handleTheInput(new Input("1,2,1"));
        System.out.println(board);
        SudokuSolver sudokuSolver = new SudokuSolver(board);
        //When
        boolean check = sudokuSolver.isBoardCorrect();
        //Then
        Assertions.assertFalse(check);
    }

    @Test
    void correctBoard2Test() {
        //Given
        Board board = new Board();
        board.handleTheInput(new Input("1,1,1"));
        board.handleTheInput(new Input("7,1,1"));
        System.out.println(board);
        SudokuSolver sudokuSolver = new SudokuSolver(board);
        //When
        boolean check = sudokuSolver.isBoardCorrect();
        //Then
        Assertions.assertFalse(check);
    }

    @Test
    void correctBoard3Test() {
        //Given
        Board board = new Board();
        board.handleTheInput(new Input("4,8,3"));
        board.handleTheInput(new Input("5,8,3"));
        System.out.println(board);
        SudokuSolver sudokuSolver = new SudokuSolver(board);
        //When
        boolean check = sudokuSolver.isBoardCorrect();
        //Then
        Assertions.assertFalse(check);
    }

    @Test
    void correctBoard4Test() {
        //Given
        Board board = new Board();
        board.handleTheInput(new Input("4,8,3"));
        board.handleTheInput(new Input("5,8,4"));
        board.handleTheInput(new Input("8,8,4"));
        System.out.println(board);
        SudokuSolver sudokuSolver = new SudokuSolver(board);
        //When
        boolean check = sudokuSolver.isBoardCorrect();
        //Then
        Assertions.assertFalse(check);
    }

    @Test
    void correctBoard5Test() {
        //Given
        Board board = new Board();
        board.handleTheInput(new Input("4,8,3"));
        board.handleTheInput(new Input("5,8,4"));
        System.out.println(board);
        SudokuSolver sudokuSolver = new SudokuSolver(board);
        //When
        boolean check = sudokuSolver.isBoardCorrect();
        //Then
        Assertions.assertTrue(check);
    }
}
