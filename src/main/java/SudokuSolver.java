import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    public SudokuElement goThroughTheBoardAndGuess() {
        boolean guessedAndSet = false;
        SudokuElement guessedElement = null;

        for (SudokuRow sudokuRow : board.getSudokuBoard()) {
            for (SudokuElement sudokuElement : sudokuRow.getRow()) {
                if (guessingPossible(sudokuElement)) {
                    sudokuElement.guessValue();
                    guessedAndSet = true;
                    guessedElement = sudokuElement;
                    break;
                }
            }
            if (guessedAndSet) {
                break;
            }
        }
        return guessedElement;
    }

    private boolean guessingPossible(SudokuElement sudokuElement) {
        return sudokuElement.isEmpty() & sudokuElement.getPossibleValues().size() > 1;
    }

    private List<Integer> elementsListToValuesList(List<SudokuElement> sudokuElements) {
        List<Integer> listOfValues = sudokuElements.stream()
                .map(SudokuElement::getValue)
                .collect(Collectors.toList());
        return listOfValues;
    }

    public boolean isBoardCorrect() {

        boolean correct = true;
        for (SudokuRow sudokuRow : board.getSudokuBoard()) {
            List<Integer> rowValues = elementsListToValuesList(sudokuRow.getRow());
            if (hasDuplicates(rowValues)) {
                correct = false;
                break;
            }
        }

        if (correct) {
            for (int n = 0; n < 9; n++) {
                List<Integer> columnItems = elementsListToValuesList(columnToList(board,n));
                if (hasDuplicates(columnItems)) {
                    correct = false;
                    break;
                }
            }
        }
         if (correct) {
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
        }
        return correct;
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
