package rpn;

import rpn.calculator.Calculator;
import rpn.calculator.CalculatorException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import rpn.parser.Parser;
import rpn.RpnProcessingException;
import rpn.RpnService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class RpnServiceTest {

    @Test
    public void testThatStackOutputIsCorrectlyFormatted() {
        Calculator calcMock = Mockito.mock(Calculator.class);
        RpnService service = new RpnService(calcMock, new Parser());
        when(calcMock.readStack()).thenReturn(List.of(5.0315, 5.000));

        String stackOutput = service.getStackOutput();

        assertEquals("5.0315 5", stackOutput);
    }

    @Test
    public void testThatExceptionIsBubbledUpProperly() {
        Calculator calcMock = Mockito.mock(Calculator.class);
        RpnService service = new RpnService(calcMock, new Parser());
        doThrow(new CalculatorException("")).when(calcMock).execute(any());

        assertThrows(RpnProcessingException.class,
                () -> service.process("clear"));
    }

}