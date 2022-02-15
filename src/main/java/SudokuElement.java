import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SudokuElement {

    private int value = -1;
    private List<Integer> possibleValues = new ArrayList<>();

    public SudokuElement() {
        for (int i =1; i<10; i++) {
            possibleValues.add(i);
        }
    }

    public List<Integer> getPossibleValues() {
        return possibleValues;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public String toString() {
        if (value == -1) {
            return " ";
        } else  {
            return String.valueOf(value);
        }
    }

    public void guessTheValueInElement() {
        if (possibleValues.size() > 0) {
            Random rand = new Random();
            int newValue = possibleValues.get(rand.nextInt(getPossibleValues().size()));
            setValue(newValue);
        }
    }
}
