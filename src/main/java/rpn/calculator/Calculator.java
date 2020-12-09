package rpn.calculator;

import java.util.List;
import java.util.Stack;

import static java.lang.String.format;

public class Calculator {
    private final Stack<Operation> history = new Stack<>();

    // Use `Double` for required precision
    private final Stack<Double> numbers = new Stack<>();

    public List<Double> readStack() {
        return List.copyOf(numbers);
    }

    public void execute(Operation operation) {
        apply(operation);

        //Do not track undo operation in history
        if (!(operation instanceof Operation.Undo)) {
            history.push(operation);
        }
    }

    private void apply(Operation operation) {
        if (operation instanceof BiOperation) {
            apply((BiOperation) operation);
        } else if (operation instanceof MonoOperation) {
            apply((MonoOperation) operation);
        } else if (operation instanceof Operation.AddNumber) {
            numbers.add(((Operation.AddNumber) operation).getValue());
        } else if (operation instanceof Operation.Clear) {
            numbers.clear();
        } else if (operation instanceof Operation.Undo) {
            undo();
        } else {
            throw new CalculatorException(
                    format("Cannot apply operation '%s' as it is not supported",
                            operation.getClass().getSimpleName()));
        }
    }

    private void apply(BiOperation value) {
        if (numbers.size() < 2) {
            throw new CalculatorException(
                    format("Cannot apply operation '%s', not enough parameters!",
                            value.getClass().getSimpleName()));
        }
        //Ordering matters here
        Double rhs = numbers.pop();
        Double lhs = numbers.pop();

        numbers.push(value.calculate(lhs, rhs));
    }

    private void apply(MonoOperation value) {
        if (numbers.size() < 1) {
            throw new CalculatorException(format("Cannot apply operation '%s', stack is empty!", value.getClass()));
        }

        numbers.push(value.calculate(numbers.pop()));
    }

    // Undo should pop the most recent historical operation and refresh
    // the current numbers stack by applying all historical operations.
    private void undo() {
        if (history.isEmpty()) {
            return;
        }
        history.pop();
        numbers.clear();

        history.forEach(this::apply);
    }

}
