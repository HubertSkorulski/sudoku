import java.util.List;

public class Input {

    private final String input;
    List<Character> acceptableValues = List.of('1','2','3','4','5','6','7','8','9');

    public Input(String input) {
        this.input = input;
    }

    public int getValue() {
        if (acceptableValues.contains(input.charAt(4))) {
            int value = Character.getNumericValue(input.charAt(4));
            return value;
        } else  {
            System.out.println("Wrong Value");
            return -1;
        }
    }

    public int getHorizontalPos() {
        if (acceptableValues.contains(input.charAt(2))) {
            int horizontalPos = Character.getNumericValue(input.charAt(2));
            return horizontalPos;
        } else  {
            System.out.println("Wrong horizontal position");
            return -1;
        }
    }

    public int getVerticalPos() {
        if (acceptableValues.contains(input.charAt(0))) {
            int verticalPos = Character.getNumericValue(input.charAt(0));
            return verticalPos;
        } else  {
            System.out.println("Wrong vertical position");
            return -1;
        }
    }

    public boolean isCorrect() {
        if (input.length() == 5) {
            if (valuesInList() & correctComas()) {
                return true;
            } else {
                return false;
            }
        }   else {
            return false;
        }
    }

    private boolean valuesInList() {
        if(getValue() != -1 & getHorizontalPos() != -1 & getVerticalPos() != -1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean correctComas() {
        if (input.charAt(1) == ',' & input.charAt(3) == ',') {
            return true;
        } else {
            return false;
        }
    }
}
