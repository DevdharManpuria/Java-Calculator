package code.Calculator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ModernCalculator extends JFrame {
    private JTextField display;
    private StringBuilder input;
    private String operator = "";
    private int num1 = 0, num2 = 0;
    private boolean isNewInput = false;
    public ModernCalculator() {
        setTitle("Dev's Calculator");
        setSize(350, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        input = new StringBuilder();
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(Color.BLACK);
        display.setForeground(Color.WHITE);
        add(display, BorderLayout.NORTH);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.DARK_GRAY);
        String[] buttons = {
            "C", "<--", "/", "*",
            "7", "8", "9", "-",
            "4", "5", "6", "+",
            "1", "2", "3", "=",
            "0", ".", "", ""
        };
        for (String text : buttons) {
            if (text.equals("")) {
                panel.add(new JLabel());
            } else {
                JButton button = new JButton(text);
                button.setFont(new Font("Arial", Font.BOLD, 20));
                button.setBackground(Color.LIGHT_GRAY);
                button.setFocusPainted(false);
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String cmd = e.getActionCommand();
                        switch (cmd) {
                            case "C":
                                clear();
                                break;
                            case "<--":
                                backspace();
                                break;
                            case "=":
                                calculate();
                                break;
                            case "+": case "-": case "*": case "/":
                                setOperator(cmd);
                                break;
                            default:
                                append(cmd);
                                break;
                        }
                    }
                });
                panel.add(button);
            }
        }
        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }
    private void append(String value) {
        if (isNewInput) {
            input.setLength(0);
            isNewInput = false;
        }
        input.append(value);
        display.setText(input.toString());
    }
    private void setOperator(String op) {
        if (input.length() == 0) return;
        num1 = Integer.parseInt(input.toString());
        operator = op;
        input.append(" ").append(op).append(" ");
        display.setText(input.toString());
        isNewInput = true;
    }
    private void calculate() {
        String[] parts = input.toString().split(" ");
        if (parts.length < 3) return;
        num1 = Integer.parseInt(parts[0]);
        num2 = Integer.parseInt(parts[2]);
        int result = 0;
        switch (operator) {
            case "+" -> result = num1 + num2;
            case "-" -> result = num1 - num2;
            case "*" -> result = num1 * num2;
            case "/" -> result = (num2 != 0) ? num1 / num2 : 0;
        }
        input.setLength(0);
        input.append(result);
        display.setText(String.valueOf(result));
        isNewInput = true;
    }
    private void clear() {
        input.setLength(0);
        display.setText("");
    }
    private void backspace() {
        if (input.length() > 0) {
            input.deleteCharAt(input.length() - 1);
            display.setText(input.toString());
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ModernCalculator());
    }
}
