
import java.util.List;
import java.util.stream.Collectors;


public class SudokuElementsSetter {
    public void prepareValuesInElements(Board board) {
        removePossibleValuesFromRow(board);
        removePossibleValuesFromColumn(board);
        removePossibleValuesFromSquares(board);
    }
    
    public boolean settingOccurred(Board board) {
        List<Integer> boardPreviousValues = boardToList(board);
        settingValues(board);
        List<Integer> boardCurrentValues = boardToList(board);
        return !boardPreviousValues.equals(boardCurrentValues);
    }

    public List<Integer> boardToList(Board board) {
        return board.getSudokuBoard().stream()
                .flatMap(l -> l.getRow().stream())
                .map(SudokuElement::getValue)
                .collect(Collectors.toList());
    }

    public void settingValues(Board board) {
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
        return sudokuElement.getPossibleValues().size() == 1 && sudokuElement.getValue() == -1;
    }

    public void removePossibleValuesFromSquares(Board board) {
        List<SudokuElement> elementsList;
        for (int hor=1; hor <10; hor= hor + 3) {
            for (int ver = 1; ver < 10; ver = ver + 3) {
                elementsList = board.squareToList(ver,hor);
                removePossibleValuesFromList(elementsList);
            }
        }
    }

    public void removePossibleValuesFromColumn(Board board) {
        for (int n =0; n<9; n++) {
            List<SudokuElement> columnItems = board.columnToList(n);
            removePossibleValuesFromList(columnItems);
            columnItems.clear();
        }
    }

    public void removePossibleValuesFromRow(Board board) {
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

    private boolean guessingPossible(SudokuElement sudokuElement) {
        return sudokuElement.isEmpty() & sudokuElement.getPossibleValues().size() > 1;
    }

    public SudokuElement goThroughTheBoardAndGuess(Board board) {
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
}
