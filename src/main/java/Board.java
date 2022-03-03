import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board extends Prototype<Board> {
    public final static int minIndex = 0;
    public final static int maxIndex = 8;
    private List<SudokuRow> sudokuBoard = new ArrayList<>();


    public Board() {
        for (int n = 0; n <= maxIndex; n++) {
            sudokuBoard.add(new SudokuRow());
        }
    }

    public SudokuElement getSudokuElement(int horizontalPosition, int verticalPosition) {
        SudokuRow chosenRow = sudokuBoard.get(verticalPosition-1);
        return chosenRow.getElement(horizontalPosition-1);
    }

    public void enteringDigits() {
        boolean setting = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println(this);

        while (setting) {
            System.out.println("Enter parameters in the format: 'Vertical Position, Horizontal Position, Value'"
                    + "\n" +"If you want to solve SUDOKU, enter 'SUDOKU'");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("SUDOKU")) {
                setting = false;
            } else {
                Input adjustedInput = new Input(input);
                handleTheInput(adjustedInput);
                System.out.println(this);
            }
        }
    }

    public void handleTheInput(Input input) {
        if (input.isCorrect()) {
            int value = input.getValue();
            int horizontalPos = input.getHorizontalPos();
            int verticalPos = input.getVerticalPos();

            SudokuElement chosenElement = getSudokuElement(horizontalPos,verticalPos);

            if (chosenElement.isEmpty()) {
                chosenElement.setValue(value);
            } else {
                System.out.println("This place is already taken");
            }

        } else {
            System.out.println("Enter parameters correctly");
        }
    }

    public String toString() {
        String result = "";
        for (int y = minIndex; y <= maxIndex; y++) {
            SudokuRow currentRow = sudokuBoard.get(y);
            result += currentRow.toString();
            result += "\n";
        }
        return result;
    }

    public List<SudokuRow> getSudokuBoard() {
        return sudokuBoard;
    }

    public Board deepCopy() throws CloneNotSupportedException {
        Board clonedBoard = super.clone();

        clonedBoard.sudokuBoard = new ArrayList<>();
        for (SudokuRow sudokuRow : sudokuBoard) {
            SudokuRow clonedRow = new SudokuRow();
            clonedRow.getRow().clear();
            for (SudokuElement sudokuElement : sudokuRow.getRow()) {
                SudokuElement clonedElement = new SudokuElement();
                clonedElement.setValue(sudokuElement.getValue());
                clonedRow.getRow().add(clonedElement);
            }
            clonedBoard.getSudokuBoard().add(clonedRow);
        }
        return clonedBoard;
    }

    public Position getElementPosition(SudokuElement elementSearchedFor) {
        int verticalIndex = -1;
        int horizontalIndex = -1;
        for (SudokuRow sudokuRow : sudokuBoard) {
            verticalIndex = sudokuBoard.indexOf(sudokuRow);
            horizontalIndex = sudokuRow.getRow().indexOf(elementSearchedFor);
            if (horizontalIndex != -1) {
                break;
            }
        }
        return new Position(horizontalIndex + 1, verticalIndex + 1);
    }

    public boolean isFilled() {
        int emptyElements = (int) sudokuBoard.stream()
                .flatMap(l -> l.getRow().stream())
                .filter(t -> t.getValue() == -1)
                .count();
        return emptyElements == 0;
    }

    public boolean hasWrongElements() {
        int wrongElements = (int) getSudokuBoard().stream()
                .flatMap(l -> l.getRow().stream())
                .filter(t -> t.getValue() == -1)
                .filter(t -> t.getPossibleValues().size() == 0)
                .count();
        return wrongElements > 0;
    }
}
