import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.script.*;

public class DevsCalculator extends JFrame {
    private final JTextField display;
    private final StringBuilder expression;
    private final ScriptEngine engine;
    private final String[][] buttonLayout = {
        {"(", ")", "AC", "±", "%"},
        {"sin", "cos", "tan", "ln", "log"},
        {"x!", "π", "e", "√", "^"},
        {"7", "8", "9", "÷", "x^2"},
        {"4", "5", "6", "×", "x^3"},
        {"1", "2", "3", "-", "x^y"},
        {"0", ".", "Ans", "+", "="},
        {"Rand", "sinh", "cosh", "tanh", "!"}
    };

    public DevsCalculator() {
        super("Dev's Calculator");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(Color.BLACK);
        ScriptEngineManager mgr = new ScriptEngineManager();
        engine = mgr.getEngineByName("JavaScript");
        expression = new StringBuilder();
        display = new JTextField();
        display.setFont(new Font("Arial", Font.PLAIN, 26));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(Color.BLACK);
        display.setForeground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(display, BorderLayout.NORTH);
        JPanel panel = new JPanel(new GridLayout(buttonLayout.length, 5, 5, 5));
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(panel, BorderLayout.CENTER);
        for (String[] row : buttonLayout) {
            for (String text : row) {
                RoundedButton btn = new RoundedButton(text);
                styleButton(btn, text);
                btn.addActionListener(e -> handleButton(text));
                panel.add(btn);
            }
        }
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(RoundedButton btn, String text) {
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setForeground(Color.WHITE);
        if ("÷×-+^=".contains(text)) {
            btn.setBackground(new Color(255, 149, 0));
        } else if (text.equals("AC")) {
            btn.setBackground(new Color(120, 120, 120));
        } else if (text.equals("Ans") || text.equals("±") || text.equals("%") || text.equals("Rand")) {
            btn.setBackground(new Color(80, 80, 80));
        } else if (text.isEmpty()) {
            btn.setBackground(Color.BLACK);
            btn.setEnabled(false);
        } else {
            btn.setBackground(new Color(50, 50, 50));
        }
    }

    private void handleButton(String text) {
        switch (text) {
            case "AC" -> clearAll();
            case "=" -> evaluateExpression();
            case "±" -> toggleSign();
            case "Ans" -> recallAnswer();
            default -> append(text);
        }
    }

    private void clearAll() {
        expression.setLength(0);
        display.setText("");
    }

    private void toggleSign() {
        if (expression.length() == 0) {
            expression.append("-");
        } else {
            expression.insert(0, "-");
        }
        display.setText(expression.toString());
    }

    private void recallAnswer() {
        if (!display.getText().isEmpty()) {
            expression.append(display.getText());
            display.setText(expression.toString());
        }
    }

    private void append(String value) {
        switch (value) {
            case "√" -> expression.append("Math.sqrt(");
            case "sin" -> expression.append("Math.sin(");
            case "cos" -> expression.append("Math.cos(");
            case "tan" -> expression.append("Math.tan(");
            case "sinh" -> expression.append("Math.sinh(");
            case "cosh" -> expression.append("Math.cosh(");
            case "tanh" -> expression.append("Math.tanh(");
            case "ln" -> expression.append("Math.log(");
            case "log" -> expression.append("Math.log10(");
            case "x!" -> expression.append("factorial(");
            case "π" -> expression.append("Math.PI");
            case "e" -> expression.append("Math.E");
            case "x^2" -> expression.append("**2");
            case "x^3" -> expression.append("**3");
            case "x^y" -> expression.append("**");
            case "Rand" -> expression.append("Math.random()");
            default -> expression.append(value);
        }
        display.setText(expression.toString());
    }

    private void evaluateExpression() {
        try {
            String exp = expression.toString();
            exp = "function factorial(n){if(n<=1)return 1;else return n*factorial(n-1);}"+exp;
            exp = exp.replace("÷", "/");
            exp = exp.replace("×", "*");
            int openParens = 0, closeParens = 0;
            for (int i = 0; i < exp.length(); i++) {
                if (exp.charAt(i) == '(') openParens++;
                if (exp.charAt(i) == ')') closeParens++;
            }
            while (closeParens < openParens) {
                exp += ")";
                closeParens++;
            }
            Object result = engine.eval(exp);
            display.setText(result.toString());
            expression.setLength(0);
            expression.append(result.toString());
        } catch (ScriptException ex) {
            display.setText("Error");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DevsCalculator::new);
    }
}

class RoundedButton extends JButton {
    public RoundedButton(String text) {
        super(text);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
    }
    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (getModel().isArmed()) {
            g2.setColor(getBackground().darker());
        } else {
            g2.setColor(getBackground());
        }
        int diameter = Math.min(width, height);
        g2.fillRoundRect(0, 0, width, height, diameter, diameter);
        super.paintComponent(g2);
        g2.dispose();
    }
    @Override
    protected void paintBorder(Graphics g) {
    }
}
