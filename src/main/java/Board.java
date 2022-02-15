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
        SudokuRow chosenRow = sudokuBoard.get(verticalPosition);
        return chosenRow.getElement(horizontalPosition);
    }

    public void setDigit() {
        boolean setting = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println(toString());

        while (setting) {
            System.out.println("Enter parameters in the format: 'Vertical Position, Horizontal Position, Value'"
                    + "\n" +"If you want to solve SUDOKU, enter 'SUDOKU'");
            String input = scanner.nextLine();
            if (input.toUpperCase().equals("SUDOKU")) {
                setting = false;
            } else {
                try {
                    int value = Character.getNumericValue(input.charAt(4));
                    int horizontalPosition = Character.getNumericValue(input.charAt(2));
                    int verticalPosition = Character.getNumericValue(input.charAt(0));

                    SudokuRow chosenRow = sudokuBoard.get(verticalPosition-1);
                    SudokuElement chosenElement = chosenRow.getElement(horizontalPosition-1);

                    if (chosenElement.getValue() == -1) {
                        if (chosenElement.getPossibleValues().contains(value)) {
                            chosenElement.setValue(value);
                        }
                    } else {
                        System.out.println("This place has already existing value!");
                    }
                    System.out.println(toString());
                } catch (Exception e) {
                    System.out.println("Enter parameters correctly");
                }
            }
        }
    }

    public String toString() {
        String result = "";
        for (int y = minIndex; y <= maxIndex; y++) {
            result += "|";
            for (int x = minIndex; x <= maxIndex; x++) {
                SudokuRow currentRow = sudokuBoard.get(y);
                SudokuElement currentElement = currentRow.getElement(x);
                result += currentElement.toString();
                result += "|";
            }
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
            clonedRow.getOneRow().clear();
            for (SudokuElement sudokuElement : sudokuRow.getOneRow()) {
                SudokuElement clonedElement = new SudokuElement();
                clonedElement.setValue(sudokuElement.getValue());
                clonedRow.getOneRow().add(clonedElement);
            }
            clonedBoard.getSudokuBoard().add(clonedRow);
        }
        return clonedBoard;
    }

    public String findElement(SudokuElement elementSearchedFor) {
        int horizontalPosition = 0;
        int verticalPosition = 0;
        boolean found = false;

        for (SudokuRow sudokuRow : sudokuBoard) {
            horizontalPosition += 1;
            verticalPosition = 0;
            for (SudokuElement sudokuElement : sudokuRow.getOneRow()) {
                verticalPosition += 1;
                if (sudokuElement.equals(elementSearchedFor)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }
        return horizontalPosition + "," + verticalPosition;
    }

    public int emptyElements() {
        return (int) sudokuBoard.stream()
                .flatMap(l -> l.getOneRow().stream())
                .filter(t -> t.getValue() == -1)
                .count();
    }
}
