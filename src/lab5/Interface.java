package lab5;

import lab5.sequence.ArithmeticSequence;
import lab5.sequence.ExponentialSequence;
import lab5.sequence.Sequence;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Interface extends JFrame {
    private JRadioButton arithmetic = new JRadioButton("arithmetic"), exponential = new JRadioButton("exponential");
    private Controller controller;
    private JTextField outputField = new JTextField(), nField = new JTextField(), a1Field = new JTextField(), dField = new JTextField();
    private JButton calcSum = new JButton("sum"), showSequence = new JButton("show"), save = new JButton("save");

    public Interface() {
        super("Simple Example");
        this.setSize(new Dimension(500, 250));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(6, 1));

        inputPanel.add(makeRowForInputPanel(new Label("input:"), new Label("")));

        inputPanel.add(makeRowForInputPanel(new JLabel("n:"), nField));

        inputPanel.add(makeRowForInputPanel(new JLabel("a1:"), a1Field));

        inputPanel.add(makeRowForInputPanel(new JLabel("d:"), dField));

        ButtonGroup gr1 = new ButtonGroup();
        gr1.add(arithmetic);
        gr1.add(exponential);
        container.add(inputPanel);
        inputPanel.add(arithmetic);
        arithmetic.setSelected(true);
        inputPanel.add(exponential);

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.add(new Label("Sequence:"), BorderLayout.WEST);
        outputPanel.add(outputField);
        outputField.setEditable(false);
        container.add(outputPanel, BorderLayout.SOUTH);

        JPanel controlPanel = new JPanel(new GridLayout(3, 0));
        controlPanel.add(calcSum);
        controlPanel.add(showSequence);
        controlPanel.add(save);
        container.add(controlPanel, BorderLayout.EAST);

        controller = new Controller(new View(outputField), new ArithmeticSequence(0, 0, 0));
        showSequence.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int n = Integer.parseInt(nField.getText());
                    double a1 = Double.parseDouble(a1Field.getText()), d = Double.parseDouble(dField.getText());
                    controller.updateSequence(arithmetic.isSelected() ? new ArithmeticSequence(n, a1, d) : new ExponentialSequence(n, a1, d));
                } catch (NumberFormatException except) {
                    errorMessage("invalid input");
                } catch (IllegalArgumentException except) {
                    errorMessage(except.getMessage());
                }
            }
        });
        calcSum.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int n = Integer.parseInt(nField.getText());
                    double a1 = Double.parseDouble(a1Field.getText()), d = Double.parseDouble(dField.getText());
                    controller.sum(arithmetic.isSelected() ? new ArithmeticSequence(n, a1, d) : new ExponentialSequence(n, a1, d));
                } catch (NumberFormatException except) {
                    errorMessage("invalid input");
                } catch (IllegalArgumentException except) {
                    errorMessage(except.getMessage());
                }
            }
        });
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int res = chooser.showSaveDialog(Interface.this);
                if (res == JFileChooser.APPROVE_OPTION) {
                    try {
                        controller.printToFile(chooser.getSelectedFile());
                    } catch (IOException e1) {
                        errorMessage("something went wrong");
                    }
                }else if(res==JFileChooser.ERROR_OPTION)
                    errorMessage("something went wrong");
            }
        });
    }

    private static Component makeRowForInputPanel(Component name, Component comp) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(name, BorderLayout.WEST);
        panel.add(comp);
        return panel;
    }

    private void errorMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}

class View {
    private JTextField textField;

    View(JTextField textField) {
        this.textField = textField;
    }

    void showSequence(Sequence sequence) {
        textField.setText(sequence.toString());
    }

    void showSum(double sum) {
        textField.setText(Double.toString(sum));
    }
}

class Controller {
    private View view;
    private Sequence sequence;

    Controller(View view, Sequence sequence) {
        this.view = view;
        this.sequence = sequence;
    }


    void updateSequence(Sequence sequence) {
        this.sequence = sequence;
        view.showSequence(sequence);
    }

    void printToFile(File file) throws IOException {
        sequence.saveToFile(file);
    }

    void sum(Sequence sequence) {
        this.sequence = sequence;
        view.showSum(sequence.sum());
    }
}