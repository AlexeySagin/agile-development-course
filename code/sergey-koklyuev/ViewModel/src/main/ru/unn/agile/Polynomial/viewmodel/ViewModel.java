package ru.unn.agile.Polynomial.viewmodel;

import ru.unn.agile.Polynomial.PolynomialCalculator;
import ru.unn.agile.Polynomial.Term;

public class ViewModel {
    public static final int ENTER_CODE = 10;
    public String polynomial1 = "";
    public String polynomial2 = "";
    public Operation operation = Operation.ADD;
    public String result = "";
    public String status = Status.WAITING;
    public boolean isCalculateButtonEnabled = false;

    public ViewModel() {
        parser = new PolynomialParser();
        writer = new PolynomialWriter();
        calculator = new PolynomialCalculator();
    }

    public void calculate() throws Exception {
        if (!parseInput())
            return;

        Term[] resultTerms = null;
        switch (operation) {
            case ADD:
                resultTerms = calculator.add(terms1, terms2);
                break;
            case SUB:
                resultTerms = calculator.sub(terms1, terms2);
                break;
            case MUL:
                resultTerms = calculator.mul(terms1, terms2);
                break;
        }

        result = writer.writePolynomial(resultTerms);
        status = Status.SUCCESS;
    }

    public void processKeyInTextField(int keyCode) throws Exception {
        if (keyCode == ENTER_CODE)
            calculate();
        else
            parseInput();
    }

    public class Status {
        public static final String WAITING = "Please provide input data";
        public static final String READY = "Press 'Calculate' or Enter";
        public static final String BAD_FORMAT = "Bad format";
        public static final String SUCCESS = "Success";
    }

    private boolean isInputAvailable() {
        return !polynomial1.isEmpty() && !polynomial2.isEmpty();
    }

    private boolean parseInput() {
        try {
            if (!polynomial1.isEmpty()) terms1 = parser.parsePolynomial(polynomial1);
            if (!polynomial2.isEmpty()) terms2 = parser.parsePolynomial(polynomial2);
        } catch (Exception e) {
            status = Status.BAD_FORMAT;
            isCalculateButtonEnabled = false;
            return false;
        }

        isCalculateButtonEnabled = isInputAvailable();
        if (isCalculateButtonEnabled) {
            status = Status.READY;
        } else {
            status = Status.WAITING;
        }

        return isCalculateButtonEnabled;
    }

    private PolynomialParser parser = null;
    private PolynomialWriter writer = null;
    private PolynomialCalculator calculator = null;
    private Term[] terms1 = null;
    private Term[] terms2 = null;
}
