import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class SudokuSolver {
    private Board board;

    public void setBoard(Board board) {
        this.board = board;
    }

    public SudokuSolver(Board board) {
        this.board = board;
    }

    public void prepareValuesInElements() {
        removePossibleValuesFromRow();
        removePossibleValuesFromColumn();
        removePossibleValuesFromSquares();
    }
    
    public boolean settingOccurred() {

        List<Integer> boardPreviousValues = boardToList(board);

        settingValues();

        List<Integer> boardCurrentValues = boardToList(board);

        if (boardPreviousValues.equals(boardCurrentValues)) {
            return false;
        } else {
            return true;
        }
    }

    public List<Integer> boardToList(Board board) {
        return board.getSudokuBoard().stream()
                .flatMap(l -> l.getRow().stream())
                .map(SudokuElement::getValue)
                .collect(Collectors.toList());
    }

    public void settingValues() {
        boolean set = false;
        for (SudokuRow sudokuRow : board.getSudokuBoard()) {
            for (SudokuElement sudokuElement : sudokuRow.getRow()) {
                if (fieldIsEmptyAndOnePossibleValueLeft(sudokuElement)) {
                    sudokuElement.setLastPossibleValue();
                    set = true;
                    break;
                }
            }
            if (set) {
                break;
            }
        }
    }

    private boolean fieldIsEmptyAndOnePossibleValueLeft (SudokuElement sudokuElement) {
        if (sudokuElement.getPossibleValues().size() == 1 && sudokuElement.getValue() == -1) {
            return true;
        } else {
            return false;
        }

    }

    public void removePossibleValuesFromSquares() {
        List<SudokuElement> elementsList;
        for (int hor=1; hor <10; hor= hor + 3) {
            for (int ver = 1; ver < 10; ver = ver + 3) {
                elementsList = squareToList(ver,hor);
                removePossibleValuesFromList(elementsList);
                elementsList = new ArrayList<>();
            }
        }
    }

    public List<SudokuElement> squareToList(int ver, int hor) {
        List<SudokuElement> elementList = new ArrayList<>();
        for (int y = ver; y < 3 + ver; y = y + 1) {
            for (int x = hor; x < 3 + hor; x = x + 1) {
                elementList.add(board.getSudokuElement(x, y));
            }
        }
        return elementList;
    }

    public void removePossibleValuesFromColumn() {
        for (int n =0; n<9; n++) {
            List<SudokuElement> columnItems = columnToList(board,n);
            removePossibleValuesFromList(columnItems);
            columnItems.clear();
        }
    }

    public List<SudokuElement> columnToList(Board board, int columnNumber) {
        List<SudokuElement> columnItems = new ArrayList<>();
        for (SudokuRow row : board.getSudokuBoard()) {
            columnItems.add(row.getElement(columnNumber));
        }
        return columnItems;
    }

    public void removePossibleValuesFromRow() {
        for (SudokuRow sudokuRow : board.getSudokuBoard()) {
            removePossibleValuesFromList(sudokuRow.getRow());
        }
    }

    public void removePossibleValuesFromList(List<SudokuElement> elementList) {
        for (SudokuElement sudokuElement : elementList){
            int currentElementValue = sudokuElement.getValue();
            for (SudokuElement otherElement:elementList) {
                otherElement.removeFromPossibleValues(currentElementValue);
            }
        }
    }

    public SudokuElement guessTheValue() {
        boolean guessedAndSet = false;
        SudokuElement guessedElement = null;

        for (SudokuRow sudokuRow : board.getSudokuBoard()) {
            for (SudokuElement sudokuElement : sudokuRow.getRow()) {
                if (sudokuElement.getValue() == -1 && sudokuElement.getPossibleValues().size() > 1) {
                    Random rand = new Random();
                    List<Integer> possibleValues = sudokuElement.getPossibleValues();
                    int randomValue = possibleValues.get(rand.nextInt(sudokuElement.getPossibleValues().size()));
                    sudokuElement.setValue(randomValue);
                    guessedAndSet = true;
                    guessedElement = sudokuElement;
                    guessedElement.setValue(sudokuElement.getValue());
                    break;
                }
            }
            if (guessedAndSet) {
                break;
            }
        }
        return guessedElement;
    }

    public boolean valid() {
        int wrongElements = (int) board.getSudokuBoard().stream()
                .flatMap(l -> l.getRow().stream())
                .filter(t -> t.getValue() == -1)
                .filter(t -> t.getPossibleValues().size() == 0)
                .count();
        if (wrongElements > 0) {
            return false;
        } else {
            return true;
        }

    }
    public Board setPreviousStateAndGetAdjustedBoard(StateBeforeGuessing previousState) {

        Position position = previousState.getPosition();
        Board board = previousState.getBoard();

        SudokuSolver sudokuSolver = new SudokuSolver(board);
        int horizontalPosition = position.getHorizontal();
        int verticalPosition = position.getVertical();

        SudokuElement elementToRemovePossibleValue = board.getSudokuElement(horizontalPosition,verticalPosition);
        sudokuSolver.prepareValuesInElements();
        elementToRemovePossibleValue.getPossibleValues().remove(Integer.valueOf(previousState.getGuessedElement().getValue()));
        elementToRemovePossibleValue.guessTheValueInElement();

        return board;
    }

    public boolean correctBoard() {

        boolean correct = true;
        List<Integer> squareItems = new ArrayList<>();

        for (SudokuRow sudokuRow : board.getSudokuBoard()) {
            List<Integer> elementsValues = sudokuRow.getRow().stream()
                    .map(SudokuElement::getValue)
                    .collect(Collectors.toList());

            if (duplicatesInList(elementsValues)) {
                correct = false;
                break;
            }
        }

        if (correct) {
            for (int n = 0; n < 9; n++) {
                List<Integer> columnItems = new ArrayList<>();
                for (SudokuRow row : board.getSudokuBoard()) {
                    columnItems.add(row.getElement(n).getValue());
                }
                if (duplicatesInList(columnItems)) {
                    correct = false;
                    break;
                }
                columnItems = new ArrayList<>();
            }
        }
         if (correct) {
            for (int hor=1; hor <10; hor= hor + 3) {
                for (int ver = 1; ver < 10; ver = ver + 3) {
                    for (int y = ver; y < 3 + ver; y = y + 1) {
                        for (int x = hor; x < 3 + hor; x = x + 1) {
                            squareItems.add(board.getSudokuElement(x, y).getValue());
                        }
                    }
                    if (duplicatesInList(squareItems)) {
                        correct = false;
                        break;
                    }
                    squareItems = new ArrayList<>();
                }
                if (!correct) {
                    break;
                }
            }
        }
        return correct;
    }

    public boolean duplicatesInList(List<Integer> elementsValues) {
        boolean wrong = false;

        for (int i = 1; i <10; i++) {
            int occurrence = Collections.frequency(elementsValues,i);
            if (occurrence > 1) {
                wrong = true;
                break;
            }
        }
        return wrong;
    }
}
