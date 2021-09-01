package com.project.workingcalculator.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.project.workingcalculator.R;
import com.project.workingcalculator.databinding.ActivityMainBinding;
import com.project.workingcalculator.viewModel.MainViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MainViewModel viewModel;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.getDisplayedString().observe(this, s -> binding.testing.setText(s));

        binding.numBtn0.setOnClickListener(this);
        binding.numBtn1.setOnClickListener(this);
        binding.numBtn2.setOnClickListener(this);
        binding.numBtn3.setOnClickListener(this);
        binding.numBtn4.setOnClickListener(this);
        binding.numBtn5.setOnClickListener(this);
        binding.numBtn6.setOnClickListener(this);
        binding.numBtn7.setOnClickListener(this);
        binding.numBtn8.setOnClickListener(this);
        binding.numBtn9.setOnClickListener(this);

        binding.sysBtn1.setOnClickListener(this);
        binding.sysBtn2.setOnClickListener(this);
        binding.sysBtn3.setOnClickListener(this);

        binding.sysBtn5.setOnClickListener(this);

        binding.funBtn1.setOnClickListener(this);
        binding.funBtn2.setOnClickListener(this);
        binding.funBtn3.setOnClickListener(this);
        binding.funBtn4.setOnClickListener(this);
        binding.funBtn5.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String buttonText = ((MaterialButton)view).getText().toString();
        if(buttonText.equals("C")) clearAll();
        else if (buttonText.equals("+")) addOperandAndValue(buttonText);
        else if (buttonText.equals("-")) addOperandAndValue(buttonText);
        else if (buttonText.equals("x")) addOperandAndValue(buttonText);
        else if (buttonText.equals("/")) addOperandAndValue(buttonText);
        else if (buttonText.equals("=")) calculateValue();
        else if (buttonText.equals("+/-")) flipNegative();
        else if (buttonText.equals(".")) addDecimal();
        else if (buttonText.equals("%")) makePercentage();
        else updateText(buttonText);
    }

    public void updateText (String msg) {
        viewModel.updateDisplayString(msg);
    }

    public void addToValuesArray () {
        String msg = binding.testing.getText().toString();
        viewModel.addValueString(msg);
        clearText();
    }

    public void addOperandAndValue (String operand) {
        String msg = binding.testing.getText().toString();
        if (msg.length() != 0) {
            viewModel.addOperand(operand);
            addToValuesArray();
        }
    }

    public void flipNegative () {
        String msg = binding.testing.getText().toString();
        if (msg.contains("-")) msg = msg.substring(1);
        else msg = "-" + msg;
        clearText();
        viewModel.updateDisplayString(msg);
    }

    public void addDecimal () {
        String msg = binding.testing.getText().toString();
        if (msg.length() == 0 && !msg.contains(".")) updateText("0.");
        else if (!msg.contains(".")) updateText(".");
    }

    public void makePercentage () {
        String msg = binding.testing.getText().toString();
        if (msg.length() != 0) {
            double val = Double.parseDouble(msg) / 100;
            clearText();
            updateText("" + val);
        }
    }

    public void calculateValue() {
        String a = binding.testing.getText().toString();
        if (a.length() != 0) {
            addToValuesArray();
            viewModel.calculateTotals();
        }
    }

    public void clearText () {
        viewModel.clearDisplayString();
    }

    public void clearAll () {
        viewModel.clearOperands();
        viewModel.clearValuesArray();
        viewModel.clearDisplayString();
    }
}