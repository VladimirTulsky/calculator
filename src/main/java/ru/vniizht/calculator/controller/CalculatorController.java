package ru.vniizht.calculator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vniizht.calculator.model.CalculationModel;
import ru.vniizht.calculator.service.CalculatorService;

@Controller
@RequestMapping("/calculator")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CalculatorController {
    CalculationModel calculationModel = new CalculationModel();
    private final CalculatorService calculatorService;

    @GetMapping
    public String getCalculatorPage(Model model) {
        model.addAttribute("calculationModel", calculationModel);
        return "calculator";
    }

    @PostMapping(params = "calculate")
    public String evaluateExpression(@ModelAttribute("calculationModel") CalculationModel calculationModel, Model model) {
        model.addAttribute("result", calculatorService.calculate(calculationModel).getExpression());
        return "calculator";
    }
}
