import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SudokuGame {

    List<State> states = new ArrayList<>();
    Board board = new Board();
    SudokuElementsSetter sudokuElementsSetter = new SudokuElementsSetter();

    public void processingSudoku(){
        board.enteringDigits();
        if (board.isCorrect()) {
            board = solveTheSudoku(board);
        } else {
            System.out.println("Board not correct");
        }
        System.out.println(board);
    }

    private Board solveTheSudoku(Board board) {
        int i=0;
        while (!board.isFilled()) {
            sudokuElementsSetter.prepareValuesInElements(board);
            boolean solved = sudokuElementsSetter.settingOccurred(board);

            if (!solved) {
                board = guess(board);
                i++;
            }
            if (i>200) {
                board = states.get(0).getBoard();
                i=0;
                states = new ArrayList<>();
            }
        }
        return board;
    }

    private Board guess(Board board) {
        State state = new State();
        state.addCopyOfBoard(board);
        SudokuElement guessedElement = sudokuElementsSetter.goThroughTheBoardAndGuess(board);
        if (!board.hasImpossibleToSetElements()) {
            Position positionOfGuessedElement = board.getElementPosition(guessedElement);
            saveState(state,guessedElement, positionOfGuessedElement);
        } else {
            State previousState = states.get(states.size()-1);
            board = previousState.getBoardWithoutPreviouslyGuessedValue();
            if (board.hasImpossibleToSetElements()) {
                State beforePreviousState = getStateBeforePreviousState(previousState);
                states.remove(previousState);
                board = beforePreviousState.getBoardWithoutPreviouslyGuessedValue();
            }
        }
        return board;
    }

    private void saveState(State state, SudokuElement guessedElement, Position position) {
        states.add(state);
        state.setGuessedValue(guessedElement.getValue());
        state.setPosition(position);
    }

    public State getStateBeforePreviousState(State state) {
        int indexOfPreviousState = states.indexOf(state);
        return states.get(indexOfPreviousState - 1);
    }

    public boolean play() {
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



}

