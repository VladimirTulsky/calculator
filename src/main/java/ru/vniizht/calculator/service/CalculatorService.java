package ru.vniizht.calculator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vniizht.calculator.model.CalculationModel;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Pattern;

@Slf4j
@Service
public class CalculatorService {
    public CalculationModel calculate(CalculationModel calculationModel) {
        return new CalculationModel(arithmeticFun(calculationModel));
    }

    private String arithmeticFun(CalculationModel calculationModel) {
        String expression = calculationModel.getExpression();
        expression = stringFormatter(expression.trim());

        String[] elements = expression.split(",");
        Deque<Double> numbers = new ArrayDeque<>();
        Deque<String> operators = new ArrayDeque<>();
        double res = 0;
        for (String el : elements) {
            if (el.isBlank()) continue;
            try {
                numbers.addLast(Double.parseDouble(el));
            } catch (NumberFormatException e) {
                if (!el.isBlank()) {
                    operators.addLast(el);
                }
            }
            if (operators.size() > numbers.size()) {
                log.warn("Wrong expression");
                return "Wrong expression";
            }
            if (!operators.isEmpty()
                    && operators.peekLast().matches("[*/]")
                    && numbers.size() > operators.size()) {
                if (numbers.size() > operators.size() + 1) {
                    log.warn("Wrong expression");
                    return "Wrong expression";
                }
                String op = operators.pollLast();
                double a = numbers.pollLast();
                double b = numbers.pollLast();
                switch (op) {
                    case "*":
                        res = a * b;
                        numbers.addLast(res);
                        break;
                    case "/":
                        if (a == 0) {
                            log.warn("division by zero");
                            return "division by zero";
                        }
                        res = b / a;
                        numbers.addLast(res);
                }
            }
        }
        while (!operators.isEmpty()) {
            if (numbers.size() > operators.size() + 1) {
                log.warn("Wrong expression");
                return "Wrong expression";
            }
            double a = numbers.pollFirst();
            double b = numbers.pollFirst();
            String op = operators.pollFirst();
            switch (op) {
                case "+":
                    res = a + b;
                    numbers.addFirst(res);
                    break;
                case "-":
                    res = a - b;
                    numbers.addFirst(res);
            }
        }
        if (numbers.size() != 1 || !operators.isEmpty() || numbers.getFirst() != res) {
            log.warn("Wrong expression");
            return "Wrong expression";
        }
        log.info("result sent: {}", numbers.pollFirst());
        return String.format("%.2f", res);
    }

    private static String stringFormatter(String str) {
        StringBuilder sb = new StringBuilder();
        for (Character c : str.toCharArray()) {
            if (sb.length() == 0 && Pattern.matches("[+-]", c.toString())) {
                sb.append(c);
            } else if (Pattern.matches("[0-9.]", c.toString())) {
                sb.append(c);
            } else if (Pattern.matches("[*/+-]", c.toString())) {
                sb.append(",").append(c).append(",");
            } else if (c.equals(' ')) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
