import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.BinaryOperator;

public class Calculator {
    
    public int calculate(String s) {
        List<Object> tokens = tokenize(s);
        Deque<Character> operatorStack = new ArrayDeque<>();
        Deque<Integer> operandStack = new ArrayDeque<>();
        for (Object token : tokens) {
            if (token instanceof Integer) {
                operandStack.push((Integer) token);
            } else if (token instanceof Character) {
                char op = (char) token;
                if (op == ')') {
                    while (operatorStack.peek().charValue() != '(') {
                        reduce(operatorStack, operandStack);
                    }
                    operatorStack.pop();
                } else if (op == '(') {
                    operatorStack.push(op);
                } else {
                    while (!operatorStack.isEmpty() && precedenceCompare(operatorStack.peek(), op) >= 0) {
                        reduce(operatorStack, operandStack);
                    }
                    operatorStack.push(op);
                }
            } 
        }
        while (!operatorStack.isEmpty()) {
            reduce(operatorStack, operandStack);
        }
        return operandStack.peek();
    }
    
    private List<Object> tokenize(String s) {
        List<Object> tokens = new ArrayList<>();
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                int number = 0;
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    number *= 10;
                    number += Character.digit(s.charAt(i++), 10);
                }
                tokens.add(number);
                i--;
            } else if (c == '(' || c == ')' || c == '*' || c == '/' || c == '+') {
                tokens.add(c);
            } else if (c == '-') {
                if (tokens.isEmpty()) {
                    tokens.add('u');
                } else {
                    Object prevToken = tokens.get(tokens.size() - 1);
                    if (!(prevToken instanceof Integer) && ((char) prevToken) != ')') {
                        tokens.add('u');
                    } else {
                        tokens.add('-');
                    }
                }
            }
            i++;
        }
        return tokens;
    }
    
    private void reduce(Deque<Character> operatorStack, Deque<Integer> operandStack) {
        char operator = operatorStack.pop();
        int right = operandStack.pop();
        if (operator == 'u') {
            operandStack.push(-right);
        } else {
            int left = operandStack.pop();
            int result = getFunction(operator).apply(left, right);
            operandStack.push(result);   
        }
    }
    
    private int precedenceCompare(char left, char right) {
        if (left == '(') {
            return -1;
        } else if (right == '(') {
            return 1;
        } else if (left == '*' || left == '/') {
            return (right == '*' || right == '/') ? 0 : 1;
        } else {
            return (right == '*' || right == '/') ? -1 : 0;
        }
    }

    private BinaryOperator<Integer> getFunction(char op) {
        switch(op) {
            case '*':
                return (a, b) -> a * b;
            case '/':
                return (a, b) -> a / b;
            case '+':
                return (a, b) -> a + b;
            case '-':
                return (a, b) -> a - b;
            default:
                throw new IllegalStateException();
        }
    }
}
