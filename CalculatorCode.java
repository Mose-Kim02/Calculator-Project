package com.example.calculator;

import java.util.*;

public class CalculatorCode {

    public String calculate(String str) {
        boolean isNum;
        boolean reverse = false;
        int intValue;
        double calculated;
        int i = 0;
        String op;
        Stack<Double> operand = new Stack<>();
        Stack<String> operator = new Stack<>();

        // Handle negative numbers at the beginning
        if (str.startsWith("-")) {
            str = "0 " + str;
        }

        // Validate that the expression starts and ends properly
        try {
            if (!str.startsWith("(") && !Character.isDigit(str.charAt(0))) {
                throw new NumberFormatException("Invalid start of the equation.");
            }
            if (!str.endsWith(")") && !Character.isDigit(str.charAt(str.length() - 1))) {
                throw new NumberFormatException("Invalid end of the equation.");
            }
        } catch (NumberFormatException e) {
            System.out.println("The equation starts with an invalid operator or ends improperly.");
            System.exit(1);
        }

        while (i < str.length()) {
            op = pickNum(str, i);
            if (op == null) {
                i++;
                continue;
            }
            i += op.length();

            try {
                intValue = Integer.parseInt(op);
                isNum = true;
            } catch (NumberFormatException e) {
                isNum = false;
            }

            if (isNum) {
                operand.push((double) intValue);
            } else {
                if (op.equals("(")) {
                    operator.push(op);
                } else if (op.equals(")")) {
                    while (!operator.peek().equals("(")) {
                        calculated = performOperation(operand, operator);
                        operand.push(calculated);
                    }
                    operator.pop(); // Remove the "(" from the stack
                } else {
                    while (!operator.isEmpty() &&
                           precedence(operator.peek()) >= precedence(op)) {
                        calculated = performOperation(operand, operator);
                        operand.push(calculated);
                    }
                    operator.push(op);
                }
            }
        }

        // Evaluate remaining operations in the stacks
        while (!operator.isEmpty()) {
            calculated = performOperation(operand, operator);
            operand.push(calculated);
        }

        // Final result
        double result = operand.pop();
        System.out.println(str + " = " + result);
        return Double.toString(result);
    }

    public static String pickNum(String str, int startPoint) {
        StringBuilder num = new StringBuilder();
        StringBuilder operator = new StringBuilder();

        for (int i = startPoint; i < str.length(); i++) {
            char value = str.charAt(i);
            if (i == startPoint && !Character.isDigit(value) && value != '-') {
                if (value == ' ') {
                    return null;
                } else {
                    operator.append(value);
                    return operator.toString();
                }
            }
            if (Character.isDigit(value) || value == '.') {
                num.append(value);
            } else {
                break;
            }
        }
        return num.toString();
    }

    public static int precedence(String operator) {
        switch (operator) {
            case "*":
            case "/":
                return 2;
            case "+":
            case "-":
                return 1;
            default:
                return 0;
        }
    }

    public static double performOperation(Stack<Double> operand, Stack<String> operator) {
        double operandTop = operand.pop();
        double operandBelow = operand.pop();
        String op = operator.pop();

        switch (op) {
            case "*":
                return operandBelow * operandTop;
            case "/":
                if (operandTop == 0) {
                    throw new ArithmeticException("Division by zero.");
                }
                return operandBelow / operandTop;
            case "+":
                return operandBelow + operandTop;
            case "-":
                return operandBelow - operandTop;
            default:
                throw new IllegalArgumentException("Invalid operator.");
        }
    }
}
