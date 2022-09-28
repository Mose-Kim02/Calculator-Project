package com.example.calculator;

import java.util.*;

public class CalculatorCode {
    public String calculate(String str) {
        Boolean isNum = false;
        String number = str;
        Boolean reverse = false;
        int intValue;
        double calculated;
        int i = 0;
        int count = 0;
        String op = "";
        Stack<Double> operand = new Stack<>();
        Stack<String> operator = new Stack<>();

        if (number.substring(0, 1).equals("-")) {
            number = "0 " + number;
        }
        try {
            if (!number.substring(0, 1).equals("(")) {
                intValue = Integer.parseInt(number.substring(0, 1));
            }
            if (!number.substring(number.length() - 1, number.length()).equals(")")) {
                intValue = Integer.parseInt(number.substring(number.length() - 1, number.length()));
            }
        } catch (NumberFormatException e) {
            System.out.println("The equation starts with an operator other than ( or - "
                    + "OR it ends with an operator other than (");
            System.exit(1);
        }
        do {
            if (i >= number.length() - 1) {
                op = pickNum(number, i);
                try {
                    intValue = Integer.parseInt(op);
                    isNum = true;
                } catch (NumberFormatException e) {
                    isNum = false;
                }
                if (isNum && !reverse) {
                    operand.push((double) Integer.parseInt(op));
                }
                reverse = true;
            } else {
                op = pickNum(number, i);
                if (op != null && !reverse) {
                    i = i + op.length();
                }
                try { // checks if separated string is an operand or operator
                    intValue = Integer.parseInt(op);
                    isNum = true;
                } catch (NumberFormatException e) {
                    isNum = false;
                }
            }
            if (!reverse) {
                if (isNum) {
                    operand.push((double) Integer.parseInt(op));
                } else {
                    if (op == null) {
                        i++;
                    } else if (op.equals("(")) {
                        operator.push(op);
                        count = 1;
                    } else if (count == 1) {
                        operator.push(op);
                        count = 0;
                    } else if (op.equals(")")) {
                        reverse = true;
                    } else if (!operator.empty() && precedence(operator.peek()) >= precedence(op)) {
                        calculated = calculate(precedence(operator.peek()), operand.pop(),
                                operand.pop(), operator.pop());
                        operand.push(calculated);
                        operator.push(op);
                    } else {
                        operator.push(op);
                    }
                }
            } else {
                if (!operator.empty() && operator.peek().equals("(")) {
                    operator.pop();
                    reverse = false;
                } else if (!operator.empty()) {
                    calculated = calculate(precedence(operator.peek()), operand.pop(), operand.pop(), operator.pop());
                    if (operand.empty()) {
                        if (!operator.empty()) {
                            operand.push(calculated);
                        } else {
                            System.out.println(number + " = " + calculated);
                            return Double.toString(calculated);
                        }
                    } else {
                        operand.push(calculated);
                    }
                } else {
                }
            }
        } while (!reverse || !operand.empty());
        return null;
    }
    public static String pickNum(String str, int startPoint) {
        String num = "";
        String operator = "";
        for (int i = startPoint; i < str.length(); i++) {
            char value = str.charAt(i);
            if (i == startPoint && !Character.isDigit(value)) {
                if (value == ' ') {
                    return null;
                } else {
                    operator = operator + value;
                    return operator;
                }
            }
            if (Character.isDigit(value)) {
                num = num + value;
            } else {
                i = str.length();
            }
        }
        return num;
    }
    public static int precedence(String operator) {
        if (operator.equals("*") || operator.equals("/")) {
            return 2;
        } else if (operator.equals("+") || operator.equals("-")) {
            return 1;
        } else {
            return 0;
        }
    }
    public static double calculate(int level,  double operandTop, double operandBelow,  String operator) {
        if (level == 2) {
            if (operator.equals("*")) {
                return operandBelow * operandTop;
            } else {
                return operandBelow / operandTop;
            }
        } else {
            if (operator.equals("+")) {
                return operandBelow + operandTop;
            } else {
                return operandBelow - operandTop;
            }
        }
    }
}
