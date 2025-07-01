import java.util.Stack;

class Trechner {

    private Stack<String> stacks;

    Trechner() {
        this.stacks = new Stack<>();
    }

    public float evaluate(String rpn) {
        this.stacks.clear(); // Stack vor der Auswertung leeren

        for (int i = 0; i < rpn.length(); i++) {
            String c = String.valueOf(rpn.charAt(i));

            if (c.equals(" ")) {
                continue; // Leerzeichen überspringen
            } else if (isOperator(c)) {
                if (stacks.size() < 2) {
                    throw new IllegalArgumentException("Nicht genug Operanden für den Operator: " + c);
                }
                double num2 = Double.parseDouble(stacks.pop()); // Rechter Operand
                double num1 = Double.parseDouble(stacks.pop()); // Linker Operand
                double result = 0;
                switch (c) {
                    case "+":
                        result = num1 + num2;
                        break;
                    case "-":
                        result = num1 - num2;
                        break;
                    case "*":
                        result = num1 * num2;
                        break;
                    case "/":
                        if (num2 == 0) {
                            throw new ArithmeticException("Division durch 0 ist nicht erlaubt");
                        }
                        result = num1 / num2;
                        break;
                }
                stacks.push(result + "");
            } else {
                StringBuilder operand = new StringBuilder();
                while (i < rpn.length() && !c.equals(" ") && !isOperator(c)) {
                    operand.append(c);
                    i++;
                    if (i < rpn.length()) {
                        c = String.valueOf(rpn.charAt(i));
                    }
                }
                stacks.push(operand.toString());
            }
        }

        if (stacks.size() != 1) {
            throw new IllegalArgumentException("Ungültiger RPN-Ausdruck: " + rpn);
        }
        return Float.parseFloat(stacks.pop());
    }

    private static boolean isOperator(String token) {
        return "+-*/".contains(token);
    }

    Turtle turtle = new Turtle(300,120);
    void view(Trechner tr, String rpn){
        String s = "";
        turtle.reset();
        turtle.moveTo(50,50);
        s = "Mein RPNRechner";
        turtle.left(90).text(s).moveTo(50,100).right(90);
         s = rpn + " = " + tr.evaluate(rpn);
         turtle.left(90).text(s).right(90);
        s = "";
    }
}

