import java.util.ArrayList;
import java.util.List;

public class SudokuRow {

    private int width = 9;
    private List<SudokuElement> row = new ArrayList<>();

    public SudokuRow() {
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
        String oneRowList = "|";
        for (SudokuElement sudokuElement : row) {
            oneRowList = oneRowList + sudokuElement.toString() + "|";
        }
        return oneRowList;
    }
}
