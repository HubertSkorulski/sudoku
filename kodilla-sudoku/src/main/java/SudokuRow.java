import java.util.ArrayList;
import java.util.List;

public class SudokuRow {

    private int width = 9;
    private List<SudokuElement> oneRow = new ArrayList<>();

    public List<SudokuElement> getOneRow() {
        return oneRow;
    }

    public SudokuRow() {
        for (int i = 0; i < width; i++) {
            oneRow.add(new SudokuElement());
        }
    }

    public void setWidth (int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public SudokuElement getElement(int position) {
        return oneRow.get(position);
    }

    @Override
    public String toString() {
        String oneRowList = "";

        for (SudokuElement sudokuElement : oneRow) {
            oneRowList = oneRowList + sudokuElement.getValue() + ",";
        }

        return oneRowList;
    }
}
