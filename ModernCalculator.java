import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ModernCalculator extends JFrame {
    private JTextField display;
    private String currentInput = "";
    private int operand1 = 0;
    private String operator = "";
    private boolean resultShown = false;
    public ModernCalculator() {
        setTitle("Modern Calculator");
        setSize(350, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
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
                        if(cmd.equals("C")) {
                            clear();
                        } else if(cmd.equals("<--")) {
                            backspace();
                        } else if(cmd.equals("=")) {
                            calculate();
                        } else if(cmd.equals("+") || cmd.equals("-") || cmd.equals("*") || cmd.equals("/")) {
                            setOperator(cmd);
                        } else {
                            append(cmd);
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
        if(resultShown) {
            display.setText("");
            currentInput = "";
            resultShown = false;
        }
        currentInput += value;
        display.setText(display.getText() + value);
    }
    private void setOperator(String op) {
        if(currentInput.isEmpty() && display.getText().isEmpty())
            return;
        try {
            operand1 = currentInput.isEmpty() ? Integer.parseInt(display.getText()) : Integer.parseInt(currentInput);
        } catch(NumberFormatException e) {
            operand1 = 0;
        }
        operator = op;
        display.setText(display.getText() + " " + op + " ");
        currentInput = "";
    }
    private void calculate() {
        if(operator.isEmpty() || currentInput.isEmpty())
            return;
        int operand2 = 0;
        try {
            operand2 = Integer.parseInt(currentInput);
        } catch(NumberFormatException e) {
            operand2 = 0;
        }
        int result = 0;
        switch(operator) {
            case "+" -> result = operand1 + operand2;
            case "-" -> result = operand1 - operand2;
            case "*" -> result = operand1 * operand2;
            case "/" -> {
                if(operand2 != 0)
                    result = operand1 / operand2;
                else {
                    display.setText("Error: Div 0");
                    clear();
                    return;
                }
            }
        }
        display.setText(String.valueOf(result));
        operand1 = result;
        operator = "";
        currentInput = "";
        resultShown = true;
    }
    private void clear() {
        currentInput = "";
        operand1 = 0;
        operator = "";
        display.setText("");
    }
    private void backspace() {
        if(!currentInput.isEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length()-1);
            String text = display.getText();
            if(text.length() > 0)
                display.setText(text.substring(0, text.length()-1));
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ModernCalculator());
    }
}
