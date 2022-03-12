import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SudokuElement {

    private int value = -1;
    private final List<Integer> possibleValues = new ArrayList<>();

    public SudokuElement() {
        for (int i =1; i<10; i++) {
            possibleValues.add(i);
        }
    }

    public List<Integer> getPossibleValues() {
        return possibleValues;
    }

    public int getValue(){
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setLastPossibleValue() {
        setValue(possibleValues.get(0));
    }

    public void guessValue() {
        Random rand = new Random();
        int valueIndex = rand.nextInt(getPossibleValues().size());
        int newValue = possibleValues.get(valueIndex);
        setValue(newValue);
    }

    public boolean isEmpty() {
        return getValue() == -1;
    }

    public void removeFromPossibleValues(int value) {
        getPossibleValues().remove(Integer.valueOf(value));
    }

    public String toString() {
        if (value == -1) {
            return " ";
        } else  {
            return String.valueOf(value);
        }
    }
}
