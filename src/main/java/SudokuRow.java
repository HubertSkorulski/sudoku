import java.util.ArrayList;
import java.util.List;

public class SudokuRow {

    private int width = 9;
    private List<SudokuElement> oneRow = new ArrayList<>();

    public SudokuRow() {
        for (int i = 0; i < width; i++) {
            oneRow.add(new SudokuElement());
        }
    }

    public List<SudokuElement> getOneRow() {
        return oneRow;
    }

    public SudokuElement getElement(int position) {
        return oneRow.get(position);
    }

    public String toString() {
        String oneRowList = "|";
        for (SudokuElement sudokuElement : oneRow) {
            oneRowList = oneRowList + sudokuElement.toString() + "|";
        }
        return oneRowList;
    }
}
