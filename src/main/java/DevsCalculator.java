import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.script.*;
public class DevsCalculator extends JFrame {
    private final JTextArea display;
    private final StringBuilder expression;
    private final ScriptEngine engine;
    private final String[][] buttonLayout = {
        {"AC", "(", ")", "±", "^", "÷"},
        {"7",  "8",  "9", "log", "1/e", "×"},
        {"4",  "5",  "6", "sin", "cos", "-"},
        {"1",  "2",  "3", "ln",  "x!",  "+"},
        {"0",  ".",  "π", "e",   "Rand","="}
    };
    public DevsCalculator() {
        super("Dev's Calculator");
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);
        ScriptEngineManager mgr = new ScriptEngineManager();
        engine = mgr.getEngineByName("JavaScript");
        expression = new StringBuilder();
        display = new JTextArea(3, 20);
        display.setLineWrap(true);
        display.setWrapStyleWord(true);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        display.setEditable(false);
        display.setBackground(Color.BLACK);
        display.setForeground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(display);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.NORTH);
        JPanel panel = new JPanel(new GridLayout(buttonLayout.length, 6, 5, 5));
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
        if ("÷×-+=^".contains(text)) {
            btn.setBackground(new Color(255, 149, 0));
        } else if (text.equals("AC")) {
            btn.setBackground(new Color(120, 120, 120));
        } else if (text.equals("±")) {
            btn.setBackground(new Color(80, 80, 80));
        } else {
            btn.setBackground(new Color(50, 50, 50));
        }
    }
    private void handleButton(String text) {
        switch (text) {
            case "AC" -> clearAll();
            case "=" -> evaluateExpression();
            case "±" -> toggleSign();
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
    private void append(String value) {
        switch (value) {
            case "sin" -> expression.append("Math.sin(");
            case "cos" -> expression.append("Math.cos(");
            case "tan" -> expression.append("Math.tan(");
            case "ln" -> expression.append("Math.log(");
            case "log" -> expression.append("Math.log10(");
            case "x!" -> expression.append("factorial(");
            case "π" -> expression.append("Math.PI");
            case "e" -> expression.append("Math.E");
            case "^" -> expression.append("**");
            case "1/e" -> expression.append("(1/Math.E)");
            case "Rand" -> expression.append("Math.random()");
            default -> expression.append(value);
        }
        display.setText(expression.toString());
    }
    private void evaluateExpression() {
        try {
            String exp = expression.toString();
            exp = "function factorial(n){if(n<=1)return 1;else return n*factorial(n-1);}"+ exp;
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
            display.setText(String.valueOf(result));
            expression.setLength(0);
            expression.append(result);
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
