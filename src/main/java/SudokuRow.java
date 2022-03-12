import java.util.ArrayList;
import java.util.List;

public class SudokuRow {

    private final List<SudokuElement> row = new ArrayList<>();

    public SudokuRow() {
        int width = 9;
        for (int i = 0; i < width; i++) {
            row.add(new SudokuElement());
        }
    }

    public List<SudokuElement> getRow() {
        return row;
    }

    public SudokuElement getElement(int index) {
        return row.get(index);
    }

    public String toString() {
        StringBuilder oneRowList = new StringBuilder("|");
        for (SudokuElement sudokuElement : row) {
            oneRowList.append(sudokuElement.toString()).append("|");
        }
        return oneRowList.toString();
    }
}
