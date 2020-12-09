package rpn;

import rpn.calculator.Calculator;
import rpn.calculator.CalculatorException;
import rpn.parser.Parser;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.stream.Collectors;

public class RpnService {
    // Formatting with up to 10 decimal precision
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("##.##########");

    // Locale decide if comma or dot is used when formatting decimals
    static {
        DECIMAL_FORMAT.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));
    }

    private final Calculator calculator;
    private final Parser parser;

    public RpnService() {
        this(new Calculator(), new Parser());
    }

    RpnService(Calculator calculator, Parser parser) {
        this.calculator = calculator;
        this.parser = parser;
    }

    public void process(String input) throws RpnProcessingException {
        if (input == null || input.isBlank()) {
            return;
        }
        try {
            parser.parse(input)
                  .forEach(calculator::execute);
        } catch (CalculatorException e) {
            throw new RpnProcessingException(e.getMessage(), e);
        }
    }

    public String getStackOutput() {
        return calculator.readStack()
                         .stream()
                         .map(DECIMAL_FORMAT::format)
                         .collect(Collectors.joining(" "));
    }
}
