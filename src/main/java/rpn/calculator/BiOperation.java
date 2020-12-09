package rpn.calculator;

public interface BiOperation extends Operation {
    double calculate(double left, double right);

    class Add implements BiOperation {
        @Override
        public double calculate(double left, double right) {
            return left + right;
        }
    }

    class Subtract implements BiOperation {
        @Override
        public double calculate(double left, double right) {
            return left - right;
        }
    }

    class Multiply implements BiOperation {
        @Override
        public double calculate(double left, double right) {
            return left * right;
        }
    }

    class Divide implements BiOperation {
        @Override
        public double calculate(double left, double right) {
            return left / right;
        }
    }

}
