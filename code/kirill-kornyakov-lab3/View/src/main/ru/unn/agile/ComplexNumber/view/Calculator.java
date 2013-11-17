package ru.unn.agile.ComplexNumber.view;

import ru.unn.agile.ComplexNumber.infrastructure.TxtLogger;
import ru.unn.agile.ComplexNumber.viewmodel.ViewModel;

import javax.swing.*;
import java.awt.event.*;

public class Calculator {
    private JPanel mainPanel;
    private JButton btnCalc;
    private ViewModel viewModel;
    // Fields to bind
    private JTextField txtZ1Re;
    private JTextField txtZ1Im;
    private JTextField txtZ2Re;
    private JTextField txtZ2Im;
    private JComboBox<ViewModel.Operation> cbOperation;
    private JTextField txtResult;
    private JLabel lbStatus;
    private JList lstLog;

    public Calculator(ViewModel viewModel) {
        this.viewModel = viewModel;
        backBind();

        loadListOfOperations();

        btnCalc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                bind();
                Calculator.this.viewModel.calculate();
                backBind();
            }
        });

        cbOperation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                bind();
                backBind();
            }
        });

        KeyAdapter keyListener = new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                bind();
                Calculator.this.viewModel.processKeyInTextField(e.getKeyCode());
                backBind();
            }
        };
        txtZ1Re.addKeyListener(keyListener);
        txtZ1Im.addKeyListener(keyListener);
        txtZ2Re.addKeyListener(keyListener);
        txtZ2Im.addKeyListener(keyListener);

        FocusAdapter focusLostListener = new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                bind();
                Calculator.this.viewModel.editingFinished();
                backBind();
            }
        };
        txtZ1Re.addFocusListener(focusLostListener);
        txtZ1Im.addFocusListener(focusLostListener);
        txtZ2Re.addFocusListener(focusLostListener);
        txtZ2Im.addFocusListener(focusLostListener);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator");

        TxtLogger logger = new TxtLogger("/Users/kirill-kornyakov/Temp/agile.log");
        frame.setContentPane(new Calculator(new ViewModel(logger)).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void loadListOfOperations() {
        ViewModel.Operation[] operations = ViewModel.Operation.values();
        cbOperation.setModel(new JComboBox<ViewModel.Operation>(operations).getModel());
    }

    private void bind() {
        viewModel.re1 = txtZ1Re.getText();
        viewModel.im1 = txtZ1Im.getText();
        viewModel.re2 = txtZ2Re.getText();
        viewModel.im2 = txtZ2Im.getText();

        viewModel.setOperation((ViewModel.Operation) cbOperation.getSelectedItem());
    }

    private void backBind() {
        btnCalc.setEnabled(viewModel.isCalculateButtonEnabled());

        txtResult.setText(viewModel.result);
        lbStatus.setText(viewModel.status);

        lstLog.setListData(viewModel.getLog().toArray());
    }
}
