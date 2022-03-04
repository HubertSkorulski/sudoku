import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

    public boolean isCorrect() {
        if(valuesInRowsCorrect()) {
            if (valuesInColumnsCorrect()) {
                return valuesInSquareCorrect();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean valuesInRowsCorrect() {
        boolean correct = true;
        for (SudokuRow sudokuRow : sudokuBoard) {
            List<Integer> rowValues = elementsListToValuesList(sudokuRow.getRow());
            if (hasDuplicates(rowValues)) {
                correct = false;
                break;
            }
        }
        return correct;
    }

    private boolean valuesInColumnsCorrect() {
        boolean correct = true;
        for (int n = 0; n < 9; n++) {
            List<Integer> columnItems = elementsListToValuesList(columnToList(n));
            if (hasDuplicates(columnItems)) {
                correct = false;
                break;
            }
        }
        return correct;
    }

    private boolean valuesInSquareCorrect() {
        boolean correct = true;
        for (int hor=1; hor <10; hor= hor + 3) {
            for (int ver = 1; ver < 10; ver = ver + 3) {
                List<Integer> squareItems = elementsListToValuesList(squareToList(ver,hor));
                if (hasDuplicates(squareItems)) {
                    correct = false;
                    break;
                }
            }
            if (!correct) {
                break;
            }
        }
        return correct;
    }

    public List<SudokuElement> columnToList(int columnNumber) {
        List<SudokuElement> columnItems = new ArrayList<>();
        for (SudokuRow row : sudokuBoard) {
            columnItems.add(row.getElement(columnNumber));
        }
        return columnItems;
    }

    public List<SudokuElement> squareToList(int ver, int hor) {
        List<SudokuElement> elementList = new ArrayList<>();
        for (int y = ver; y < 3 + ver; y = y + 1) {
            for (int x = hor; x < 3 + hor; x = x + 1) {
                elementList.add(getSudokuElement(x, y));
            }
        }
        return elementList;
    }

    private List<Integer> elementsListToValuesList(List<SudokuElement> sudokuElements) {
            List<Integer> listOfValues = sudokuElements.stream()
                    .map(SudokuElement::getValue)
                    .collect(Collectors.toList());
            return listOfValues;
    }

    public boolean hasDuplicates(List<Integer> elementsValues) {
        boolean wrong = false;
        for (int i = 1; i < 10; i++) {
            int occurrence = Collections.frequency(elementsValues,i);
            if (occurrence > 1) {
                wrong = true;
                break;
            }
        }
        return wrong;
    }

}
