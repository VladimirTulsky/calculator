package ru.vniizht.calculator;

import org.junit.jupiter.api.Test;
import ru.vniizht.calculator.model.CalculationModel;
import ru.vniizht.calculator.service.CalculatorService;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorServiceTest {
    CalculationModel requestModel;
    CalculatorService calculatorService = new CalculatorService();

    @Test
    void additionCurrentTwoNumbersTest() {
        requestModel = new CalculationModel("5+2");
        assertEquals("7,00", calculatorService.calculate(requestModel).getExpression());
    }

    @Test
    void subtractionCurrentTwoNumbersWithSpacesTest() {
        requestModel = new CalculationModel("5  -  2  ");
        assertEquals("3,00", calculatorService.calculate(requestModel).getExpression());
    }

    @Test
    void correctNumbersWithMixedOperatorsTest() {
        requestModel = new CalculationModel("-5  -  2.2 /3 - 2.4 +2 ");
        assertEquals("-6,13", calculatorService.calculate(requestModel).getExpression());
    }

    @Test
    void divisionByZeroTest() {
        requestModel = new CalculationModel("5 / 0");
        assertEquals("division by zero", calculatorService.calculate(requestModel).getExpression());
    }

    @Test
    void wrongExpressionWithMoreNumbersTest() {
        requestModel = new CalculationModel("5 / 2 2");
        assertEquals("Wrong expression", calculatorService.calculate(requestModel).getExpression());
    }

    @Test
    void wrongExpressionWithMoreOperatorsTest() {
        requestModel = new CalculationModel("5 / 2 + + 2");
        assertEquals("Wrong expression", calculatorService.calculate(requestModel).getExpression());
    }

    @Test
    void correctExpressionWhenExpressionStartsWithMinus() {
        requestModel = new CalculationModel("-5 / 2 + 2");
        assertEquals("-0,50", calculatorService.calculate(requestModel).getExpression());
    }

    @Test
    void correctExpressionWithMultiplication() {
        requestModel = new CalculationModel("5 - 2 * 1.5 - 2");
        assertEquals("0,00", calculatorService.calculate(requestModel).getExpression());
    }
}
