import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SudokuGame {

    List<State> states = new ArrayList<>();
    Board board = new Board();
    SudokuSolver sudokuSolver = new SudokuSolver(board);

    public void processingSudoku(){
        int tries = 0;
        board.enteringDigits();
        if (board.isCorrect()) {
            while (!board.isFilled()) {
                sudokuSolver.prepareValuesInElements(board);
                boolean solved = sudokuSolver.settingOccurred();
                if (!solved) {
                    tries ++;
                } else {
                    tries = 0;
                }
                if (tries > 2) {
                    State state = new State();
                    state.addCopyOfBoard(board);
                    SudokuElement guessedElement = sudokuSolver.goThroughTheBoardAndGuess();
                    if (guessedElement != null && !board.hasWrongElements()) {
                        saveStateAndGuessedElement(state,guessedElement);
                        tries = 0;
                    } else {
                        State previousState = states.get(states.size()-1);

                        board = previousState.getBoardWithoutPreviouslyGuessedValue();
                        sudokuSolver.setBoard(board);

                        if (board.hasWrongElements()) {
                            State beforePreviousState = getStateBeforePreviousState(previousState);
                            states.remove(previousState);
                            board = beforePreviousState.getBoardWithoutPreviouslyGuessedValue();
                            sudokuSolver.setBoard(board);
                        }
                    }
                }
            }
        } else {
            System.out.println("Board not correct");
        }
        System.out.println(board);
    }

    private void guessing() {
        State state = new State();
        state.addCopyOfBoard(board);
        SudokuElement guessedElement = sudokuSolver.goThroughTheBoardAndGuess();


    }


    public State getStateBeforePreviousState(State state) {
        int indexOfPreviousState = states.indexOf(state);
        return states.get(indexOfPreviousState - 1);
    }

    public boolean solveAgain() {
        boolean correctAnswer = false;
        boolean gameFinished = false;

        processingSudoku();
        Scanner scan = new Scanner(System.in);

        while (!correctAnswer) {
            System.out.println("Wanna play again? Yes or No?");
            String answer = scan.nextLine();
            if (answer.equalsIgnoreCase("YES")) {
                correctAnswer = true;
                gameFinished = false;
            } else if (answer.equalsIgnoreCase("NO")) {
                System.out.println("Thanks for playing");
                correctAnswer = true;
                gameFinished = true;
            } else {
                System.out.println("I don't understand your answer");
            }
        }
        return gameFinished;
    }

    private void saveStateAndGuessedElement(State state, SudokuElement guessedElement) {
        states.add(state);
        state.setGuessedValue(guessedElement.getValue());
        state.setPosition(board.getElementPosition(guessedElement));
    }

}

