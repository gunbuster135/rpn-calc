package rpn.calculator;

public interface Operation {

    class Clear implements Operation {
    }

    class Undo implements Operation {
    }

    class AddNumber implements Operation {
        private final double value;

        public AddNumber(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }
}
