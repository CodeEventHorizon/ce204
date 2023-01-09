import java.util.Scanner;
import java.util.Stack;

//PART 1
public class RPNCalculator {

    Stack<Integer> stack;

    public static void main(String[] args) {
        RPNCalculator r = new RPNCalculator();
        System.out.println("Type in RPN expression:");
        Scanner input = new Scanner(System.in); //user input
        while (input.hasNext()) { //never ending loop
            String inputText = input.nextLine();
            System.out.println("Result: " + r.evaluate(inputText));
        }
    }

    private static class RPNException extends RuntimeException{ //RPN Exception
        RPNException() {
            System.err.println("RPN is invalid!");
        }
    }

    protected void clear() {
        stack.clear();
    }

    protected int evaluate(String expr) throws RPNException{
        stack = new Stack<>();
        clear(); //empties the stack
        if (expr.isEmpty()) { //String expression is empty exception is thrown
            throw new RPNException();
        }
        for (String token : expr.split("\\s")) { // splits expression string by whitespace
            switch (token) {
                case "+":
                    System.out.println("Addition\t\t");
                    if (stack.size() < 2) {throw new RPNException();} //if the stack does not contain more than 1 number there will be an error during computation
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-":
                    System.out.println("Subtraction\t\t");
                    if (stack.size() < 2) {throw new RPNException();}
                    stack.push(-stack.pop() + stack.pop());
                    break;
                case "*":
                    System.out.println("Multiplication\t\t");
                    if (stack.size() < 2) {throw new RPNException();}
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/":
                    System.out.println("Division\t\t");
                    if (stack.size() < 2) {throw new RPNException();}
                    int divisor = stack.pop();
                    stack.push(stack.pop() / divisor);
                    break;
                default:
                    System.out.println("Push: " + token );
                    stack.push(Integer.parseInt(token));
                    break;
            }
            System.out.println("Current Stack:" + stack);
        }
        if (stack.size() >= 2 || stack.empty()) { // throws exception in case the end result for some reason has stack greater than 1 & is empty
            throw new RPNException(); // result needs to be 1, which means stack should equal to 1
        }
        return stack.pop(); //returns result
    }
}
