import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SudokuGame {

    List<BeforeGuessing> states = new ArrayList<>();
    Board board = new Board();
    SudokuSolver sudokuSolver = new SudokuSolver(board);

    public void processingSudoku(){
        int tries = 0;
        sudokuSolver.exampleParameters();
        board.setDigit();
        if (sudokuSolver.correctBoard()) {
            while (board.emptyElements() != 0) {
                sudokuSolver.prepareValuesInElements();
                boolean solved = sudokuSolver.settingOccurred();
                if (!solved) {
                    tries ++;
                } else {
                    tries = 0;
                }
                if (tries > 2) { //to zrobić na guessing process
                    BeforeGuessing beforeGuessing = new BeforeGuessing();
                    saveBoard(beforeGuessing);
                    SudokuElement guessedElement = sudokuSolver.guessTheValue();
                    if (guessedElement != null && sudokuSolver.valid()) {
                        definePreviousState(beforeGuessing,guessedElement);
                        tries = 0;
                    } else {
                        BeforeGuessing previousState = states.get(states.size()-1);
                        board = useBoardFromPreviousState(previousState);

                        if (!sudokuSolver.valid()) {
                            board = useStateFromBeforePreviousState(previousState);
                        }
                    }
                }
            }
        } else {
            System.out.println("Board not correct");
        }
        System.out.println(board);
    }

    public boolean resolveSudoku() {
        boolean correctAnswer = false;
        boolean gameFinished = false;

        processingSudoku();
        Scanner scan = new Scanner(System.in);

        while (!correctAnswer) {
            System.out.println("Wanna play again? Yes or No?");
            String answer = scan.nextLine();
            if (answer.toUpperCase().equals("YES")) {
                correctAnswer = true;
                gameFinished = false;
            } else if (answer.toUpperCase().equals("NO")) {
                System.out.println("Thanks for playing");
                correctAnswer = true;
                gameFinished = true;
            } else {
                System.out.println("I don't understand your answer");
            }
        }
    return gameFinished;
    }

    public void definePreviousState(BeforeGuessing beforeGuessing, SudokuElement guessedElement) {
        states.add(beforeGuessing);
        beforeGuessing.setGuessedElement(guessedElement);
        beforeGuessing.setCoordinates(board.findElement(guessedElement));
    }

    public Board useBoardFromPreviousState(BeforeGuessing previousState) {
        Board board = sudokuSolver.setPreviousStateAndGetAdjustedBoard(previousState);
        sudokuSolver.setBoard(board);
        return board;
    }

    public void saveBoard(BeforeGuessing beforeGuessing) {
        try {
            Board boardToSave = board.deepCopy();
            beforeGuessing.setBoard(boardToSave);
        } catch (CloneNotSupportedException e) {
            System.out.println("Deep copy not executed correctly");
        }
    }

    public Board useStateFromBeforePreviousState(BeforeGuessing previousState) {
        int indexOfPreviousState = states.indexOf(previousState);
        previousState = states.get(indexOfPreviousState - 1);
        board = useBoardFromPreviousState(previousState);
        states.remove(indexOfPreviousState);
        return board;
    }


}

