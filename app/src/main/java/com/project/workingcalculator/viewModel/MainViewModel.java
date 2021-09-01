package com.project.workingcalculator.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<String> displayString = new MutableLiveData<>();

    public LiveData<String> getDisplayedString() {
        return displayString;
    }

    public void updateDisplayString(String item) {
        String a = displayString.getValue();
        if (a == null) a = "";
        displayString.setValue(a + item);
    }

    public void clearDisplayString() {
        displayString.setValue("");
    }

    private final MutableLiveData<List<String>> valuesArray = new MutableLiveData<>();

    public LiveData<List<String>> getValuesArray() {
        return valuesArray;
    }

    public void addValueString(String item) {
        List<String> a = valuesArray.getValue();
        if (a == null) a = new ArrayList<>();
        a.add(item);
        valuesArray.setValue(a);
    }

    public void clearValuesArray() {
        List<String> a = new ArrayList<>();
        valuesArray.setValue(a);
    }

    private final MutableLiveData<List<String>> operandArray = new MutableLiveData<>();

    public LiveData<List<String>> getOperands () {
        return operandArray;
    }

    public void addOperand(String item) {
        List<String> a = operandArray.getValue();
        if (a == null) a = new ArrayList<>();
        a.add(item);
        operandArray.setValue(a);
    }

    public void clearOperands() {
        List<String> a = new ArrayList<>();
        operandArray.setValue(a);
    }

    public void calculateTotals() {
        int opStart = 0;
        List<String> op = getOperands().getValue();
        double total = 0;
        List<String> vals = getValuesArray().getValue();
        if (vals.size() == 1) {
            total = Double.parseDouble(vals.get(0));
        } else {
            for (int i = 0; i < vals.size(); i++){
                if (i == 0) {
                    double val1 = Double.parseDouble(vals.get(i));
                    double val2 = Double.parseDouble(vals.get(i+1));
                    total = determineOperandCalc(val1, val2, op.get(opStart));
                    i++;
                    opStart++;
                } else {
                    double val1 = total;
                    double val2 = Double.parseDouble(vals.get(i));
                    total = determineOperandCalc(val1, val2, op.get(opStart));
                    opStart++;
                }
            }
        }
        String newTotal = "" + total;
        updateDisplayString(newTotal);
        opStart = 0;
        clearOperands();
        clearValuesArray();
    }

    public double determineOperandCalc(double val1, double val2, String operand) {
        double total;
        if (operand.equals("+")) {
            total = val1 + val2;
            return total;
        } else if (operand.equals("-")) {
            total = val1 - val2;
            return total;
        } else if (operand.equals("x")) {
            total = val1 * val2;
            return total;
        } else {
            total = val1 / val2;
            return total;
        }
    }
}
