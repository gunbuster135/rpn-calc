package rpn.parser;

import rpn.calculator.BiOperation;
import rpn.calculator.MonoOperation;
import rpn.calculator.Operation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @ParameterizedTest(name = "Parsing line {0} with expected output {1}")
    @MethodSource("singleOperations")
    public void testParseSingleOperation(String line, Class<Operation> expectedType) {
        Parser parser = new Parser();

        List<Operation> operations = parser.parse(line);

        assertEquals(1, operations.size());
        assertEquals(expectedType, operations.get(0).getClass());
    }

    @Test
    public void testParseMultipleOperations() {
        Parser parser = new Parser();

        List<Operation> operations = parser.parse("5 * / -");

        assertEquals(4, operations.size());
        assertTrue(operations.get(0) instanceof Operation.AddNumber);
        assertTrue(operations.get(1) instanceof BiOperation.Multiply);
        assertTrue(operations.get(2) instanceof BiOperation.Divide);
        assertTrue(operations.get(3) instanceof BiOperation.Subtract);
    }

    @Test
    public void testIncorrectInputThrowsException() {
        Parser parser = new Parser();

        assertThrows(IllegalArgumentException.class,
                () -> parser.parse("GG"));
    }

    private static Stream<Arguments> singleOperations() {
        return Stream.of(
                //add
                Arguments.of("5", Operation.AddNumber.class),

                //mono
                Arguments.of("sqrt", MonoOperation.SquareRoot.class),

                //bi operations
                Arguments.of("+", BiOperation.Add.class),
                Arguments.of("-", BiOperation.Subtract.class),
                Arguments.of("*", BiOperation.Multiply.class),
                Arguments.of("/", BiOperation.Divide.class),
                Arguments.of("+", BiOperation.Add.class),

                //stack operations
                Arguments.of("clear", Operation.Clear.class),
                Arguments.of("undo", Operation.Undo.class)
        );
    }
}