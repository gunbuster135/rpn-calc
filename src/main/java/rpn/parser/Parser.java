package rpn.parser;

import rpn.calculator.BiOperation;
import rpn.calculator.MonoOperation;
import rpn.calculator.Operation;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {
    private static final String DELIMITER = " ";

    public List<Operation> parse(String line) {
        if (line == null || line.isBlank()) {
            return Collections.emptyList();
        }

        return Stream.of(line.split(DELIMITER))
                     .map(String::trim)
                     .map(this::parseToken)
                     .collect(Collectors.toUnmodifiableList());
    }

    private Operation parseToken(String value) {
        return tryParseNumeric(value)
                .or(() -> tryParseOperand(value))
                .orElseThrow(() -> new IllegalArgumentException(String.format("Could not parse value '%s'", value)));
    }

    // Java does not provide a handy numerical check that returns a boolean
    // so we'll attempt to just parse it & see if we get an exception
    private Optional<Operation> tryParseNumeric(String str) {
        try {
            double value = Double.parseDouble(str);
            return Optional.of(new Operation.AddNumber(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private Optional<Operation> tryParseOperand(String str) {
        switch (str) {
            case "+":
                return Optional.of(new BiOperation.Add());
            case "-":
                return Optional.of(new BiOperation.Subtract());
            case "/":
                return Optional.of(new BiOperation.Divide());
            case "*":
                return Optional.of(new BiOperation.Multiply());
            case "sqrt":
                return Optional.of(new MonoOperation.SquareRoot());
            case "clear":
                return Optional.of(new Operation.Clear());
            case "undo":
                return Optional.of(new Operation.Undo());
            default:
                return Optional.empty();
        }
    }
}
