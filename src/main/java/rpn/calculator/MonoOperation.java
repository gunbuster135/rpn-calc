package rpn.calculator;

public interface MonoOperation extends Operation {
    double calculate(double value);

    class SquareRoot implements MonoOperation {

        @Override
        public double calculate(double value) {
            return Math.sqrt(value);
        }
    }
}
