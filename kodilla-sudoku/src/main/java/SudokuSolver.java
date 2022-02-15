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
        rowToListAndRemovePossibleValues();
        columnToListAndRemovePossibleValues();
        squareToListAndRemovePossibleValues();
    }
    
    public boolean settingOccurred() {

        List<Integer> boardPreviousValues = board.getSudokuBoard().stream()
                .flatMap(l -> l.getOneRow().stream())
                .map(SudokuElement::getValue)
                .collect(Collectors.toList());

        settingValues();

        List<Integer> boardCurrentValues = board.getSudokuBoard().stream()
                .flatMap(l -> l.getOneRow().stream())
                .map(SudokuElement::getValue)
                .collect(Collectors.toList());

        if (boardPreviousValues.equals(boardCurrentValues)) {
            return false;
        } else {
            return true;
        }
    }

    public void settingValues() {
        boolean set = false;
        for (SudokuRow sudokuRow : board.getSudokuBoard()) {
            for (SudokuElement sudokuElement : sudokuRow.getOneRow()) {
                if (sudokuElement.getPossibleValues().size() == 1 && sudokuElement.getValue() == -1) {
                    sudokuElement.setValue(sudokuElement.getPossibleValues().get(0));
                    set = true;
                    break;
                }
            }
            if (set) {
                break;
            }
        }
    }

    public void squareToListAndRemovePossibleValues() {
        List<SudokuElement> elementList = new ArrayList<>();

        for (int hor=0; hor <9; hor= hor + 3) {
            for (int ver = 0; ver < 9; ver = ver + 3) {
                for (int y = ver; y < 3 + ver; y = y + 1) {
                    for (int x = hor; x < 3 + hor; x = x + 1) {
                        elementList.add(board.getSudokuElement(x, y));
                    }
                }
                removePossibleValuesFromList(elementList);
                elementList.clear();
            }
        }
    }

    public void columnToListAndRemovePossibleValues() {
        for (int n =0; n<9; n++) {
            List<SudokuElement> columnItems = new ArrayList<>();
            for (SudokuRow row : board.getSudokuBoard()) {
                columnItems.add(row.getElement(n));
            }
            removePossibleValuesFromList(columnItems);
            columnItems.clear();
        }
    }
    public void rowToListAndRemovePossibleValues() {

        for (SudokuRow sudokuRow : board.getSudokuBoard()) {
            removePossibleValuesFromList(sudokuRow.getOneRow());
        }
    }

    public void removePossibleValuesFromList(List<SudokuElement> elementList) {
        for (SudokuElement sudokuElement:elementList){
            int currentElementValue = sudokuElement.getValue();
            for (SudokuElement otherElement:elementList) {
                otherElement.getPossibleValues().remove(Integer.valueOf(currentElementValue));
            }
        }
    }

    public void exampleSetting(int x, int y, int value) {
        SudokuRow sudokuRow = board.getSudokuBoard().get(y-1);
        SudokuElement sudokuElement = sudokuRow.getElement(x-1);
        sudokuElement.setValue(value);
    }

    public void exampleParameters() {
        /*exampleSetting(1,2,4);
        exampleSetting(1,6,8);
        exampleSetting(1,8,3);
        exampleSetting(2,1,3);
        exampleSetting(1,4,6);
        //exampleSetting(2,2,8);
        //exampleSetting(2,4,7);
        //exampleSetting(2,7,4);
        exampleSetting(3,1,7);
        //exampleSetting(3,2,9);
        exampleSetting(3,4,2);
        exampleSetting(3,6,4);
        //exampleSetting(3,7,6);
        //exampleSetting(4,2,6);
        exampleSetting(4,3,4);
        exampleSetting(4,4,5);
        //exampleSetting(4,5,7);
        //exampleSetting(4,6,2);
        exampleSetting(4,9,8);
        exampleSetting(5,1,8);
        //exampleSetting(5,2,5);
        exampleSetting(5,8,7);
        exampleSetting(6,4,8);
        //exampleSetting(6,5,9);
        exampleSetting(6,6,3);
        //exampleSetting(7,4,4);
        exampleSetting(7,6,5);
        //exampleSetting(7,7,1);
        exampleSetting(7,9,3);
        //exampleSetting(8,1,5);
        //exampleSetting(8,4,9);
        //exampleSetting(8,6,1);
        //exampleSetting(8,9,4);
        //exampleSetting(9,3,1);
        //exampleSetting(9,5,6);
        //exampleSetting(9,6,7);
        //exampleSetting(9,7,8);*/
    }


    public SudokuElement guessTheValue() {
        boolean guessedAndSet = false;
        SudokuElement guessedElement = null;

        for (SudokuRow sudokuRow : board.getSudokuBoard()) {
            for (SudokuElement sudokuElement : sudokuRow.getOneRow()) {
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
                .flatMap(l -> l.getOneRow().stream())
                .filter(t -> t.getValue() == -1)
                .filter(t -> t.getPossibleValues().size() == 0)
                .count();
        if (wrongElements > 0) {
            return false;
        } else {
            return true;
        }

    }
    public Board setPreviousStateAndGetAdjustedBoard(BeforeGuessing previousState) {

        String coordinates = previousState.getCoordinates();
        Board board = previousState.getBoard();

        SudokuSolver sudokuSolver = new SudokuSolver(board);
        int horizontalPosition = Character.getNumericValue(coordinates.charAt(2))-1;
        int verticalPosition = Character.getNumericValue(coordinates.charAt(0))-1;

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
            List<Integer> elementsValues = sudokuRow.getOneRow().stream()
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
            for (int hor=0; hor <9; hor= hor + 3) {
                for (int ver = 0; ver < 9; ver = ver + 3) {
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
