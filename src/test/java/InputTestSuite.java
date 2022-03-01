import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputTestSuite {

    @Test
    void correctInputTest() {
        //Given
        Input input = new Input("1,2,4");
        //When
        boolean correct = input.isCorrect();
        //Then
        Assertions.assertTrue(correct);
    }

    @Test
    void wrongInputTest() {
        //Given
        Input input = new Input("1,2,14");
        //When
        boolean correct = input.isCorrect();
        //Then
        Assertions.assertFalse(correct);
    }

    @Test
    void wrongInput2Test() {
        //Given
        Input input = new Input("1,214");
        //When
        boolean correct = input.isCorrect();
        //Then
        Assertions.assertFalse(correct);
    }
    @Test
    void wrongInput3Test() {
        //Given
        Input input = new Input("1,e,4");
        //When
        boolean correct = input.isCorrect();
        //Then
        Assertions.assertFalse(correct);
    }
}
