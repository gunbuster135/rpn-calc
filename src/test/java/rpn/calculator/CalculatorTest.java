package rpn.calculator;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {

    @ParameterizedTest(name = "Executing operation {0} with parameters {1} and {2}")
    @MethodSource("biOperationTestCases")
    public void testBiOperations(BiOperation biOperation,
                                 int first,
                                 int second,
                                 double expected) {
        Calculator calculator = new Calculator();

        calculator.execute(new Operation.AddNumber(first));
        calculator.execute(new Operation.AddNumber(second));
        calculator.execute(biOperation);

        assertEquals(List.of(expected), calculator.readStack());
    }


    @ParameterizedTest(name = "Executing operations {0} with expected result {1}")
    @MethodSource("assortedTestCases")
    public void testBiOperations(List<Operation> operations, List<Double> expectedStack) {
        Calculator calculator = new Calculator();

        operations.forEach(calculator::execute);

        assertEquals((expectedStack), calculator.readStack());
    }

    @Test
    public void testMonoOperation() {
        Calculator calculator = new Calculator();

        calculator.execute(new Operation.AddNumber(25));
        calculator.execute(new MonoOperation.SquareRoot());

        assertEquals(List.of(5d), calculator.readStack());
    }

    @Test
    public void testClearOperation() {
        Calculator calculator = new Calculator();

        calculator.execute(new Operation.AddNumber(25));
        calculator.execute(new Operation.Clear());

        assertEquals(List.of(), calculator.readStack());
    }

    @Test
    public void testUndoOperation() {
        Calculator calculator = new Calculator();

        calculator.execute(new Operation.AddNumber(25));
        calculator.execute(new Operation.AddNumber(5));
        calculator.execute(new BiOperation.Add());
        calculator.execute(new Operation.Undo());
        calculator.execute(new Operation.Undo());

        //Undo should undo the addition operation & the second operand
        assertEquals(List.of(25d), calculator.readStack());
    }

    @Test
    public void testNotSufficientParametersForBiOperation() {
        Calculator calculator = new Calculator();
        calculator.execute(new Operation.AddNumber(5));

        assertThrows(CalculatorException.class,
                () -> calculator.execute(new BiOperation.Multiply()));
    }

    @Test
    public void testNotSufficientParametersForMonoOperation() {
        Calculator calculator = new Calculator();

        assertThrows(CalculatorException.class,
                () -> calculator.execute(new MonoOperation.SquareRoot()));
    }

    private static Stream<Arguments> biOperationTestCases() {
        return Stream.of(
                Arguments.of(new BiOperation.Add(), 5, 5, 10d),
                Arguments.of(new BiOperation.Multiply(), 5, 5, 25d),
                Arguments.of(new BiOperation.Divide(), 5, 5, 1d),
                Arguments.of(new BiOperation.Subtract(), 5, 5, 0d)
        );
    }

    private static Stream<Arguments> assortedTestCases() {
        return Stream.of(
                // 7 12 2 / -> 7 6
                Arguments.of(
                        List.of(
                                new Operation.AddNumber(7),
                                new Operation.AddNumber(12),
                                new Operation.AddNumber(2),
                                new BiOperation.Divide()
                        ),
                        List.of(7d, 6d)
                ),
                // 5 4 3 2 undo undo * -> 20
                Arguments.of(
                        List.of(
                                new Operation.AddNumber(5),
                                new Operation.AddNumber(4),
                                new Operation.AddNumber(3),
                                new Operation.AddNumber(2),
                                new Operation.Undo(),
                                new Operation.Undo(),
                                new BiOperation.Multiply()
                        ),
                        List.of(20d)
                )
        );
    }



}