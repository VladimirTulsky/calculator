package ru.vniizht.calculator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vniizht.calculator.model.CalculationModel;
import ru.vniizht.calculator.service.CalculatorService;

import javax.validation.Valid;

@RestController
@RequestMapping("/calculate")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RestCalculatorController {
    private final CalculatorService calculatorService;

    @PostMapping
    public CalculationModel evaluateExpression(@Valid @RequestBody CalculationModel calculationModel) {
        return calculatorService.calculate(calculationModel);
    }
}
