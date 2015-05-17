package com.cse24gmail.jakir.mycalculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    private TextView tvDisplayScreen;
    private Boolean userIsInTheMiddleOfTypingANumber=false;
    private CalculatorBrain mCalculatorBrain;
    private static final String DIGITS="0123456789.";
    DecimalFormat df=new DecimalFormat("@###########");

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCalculatorBrain=new CalculatorBrain();
        tvDisplayScreen= (TextView) findViewById(R.id.tvResult);

        df.setMinimumFractionDigits(0);
        df.setMinimumIntegerDigits(1);
        df.setMaximumIntegerDigits(8);

        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);

        findViewById(R.id.btnAdd).setOnClickListener(this);
        findViewById(R.id.btnSubtract).setOnClickListener(this);
        findViewById(R.id.btnMultiplication).setOnClickListener(this);
        findViewById(R.id.btnDivision).setOnClickListener(this);
        findViewById(R.id.btnToggleSign).setOnClickListener(this);
        findViewById(R.id.btnDot).setOnClickListener(this);
        findViewById(R.id.btnEquals).setOnClickListener(this);
        findViewById(R.id.btnClear).setOnClickListener(this);
        findViewById(R.id.btnClearMemory).setOnClickListener(this);
        findViewById(R.id.btnAddToMemory).setOnClickListener(this);
        findViewById(R.id.btnSubtractMemory).setOnClickListener(this);
        findViewById(R.id.btnRecallMemory).setOnClickListener(this);

        // for landscape mode
        if(findViewById(R.id.btnSquareRoot) !=null){
            findViewById(R.id.btnSquareRoot).setOnClickListener(this);
        }
        if(findViewById(R.id.btnSquared) !=null){
            findViewById(R.id.btnSquared).setOnClickListener(this);
        }
        if(findViewById(R.id.btnInvert) !=null){
            findViewById(R.id.btnInvert).setOnClickListener(this);
        }
        if(findViewById(R.id.btnSin) !=null){
            findViewById(R.id.btnSin).setOnClickListener(this);
        }
        if(findViewById(R.id.btnCos) !=null){
            findViewById(R.id.btnCos).setOnClickListener(this);
        }
        if(findViewById(R.id.btnTan) !=null){
            findViewById(R.id.btnTan).setOnClickListener(this);
        }


    }



    @Override
    public void onClick(View v) {

        String buttonPressed=((Button) v).getText().toString();
        if(DIGITS.contains(buttonPressed)){

            //digits was pressed
            if(userIsInTheMiddleOfTypingANumber){
                if(buttonPressed.equals(".") && tvDisplayScreen.getText().toString().contains(".") ){

                }else{
                    tvDisplayScreen.append(buttonPressed);
                }
            }else{
                if(buttonPressed.equals(".")){

                    tvDisplayScreen.setText(0 + buttonPressed);
                }else{
                    tvDisplayScreen.setText(buttonPressed);
                }
                userIsInTheMiddleOfTypingANumber=true;
            }
        }else{
            // operation was pressed
            if(userIsInTheMiddleOfTypingANumber){
                mCalculatorBrain.setOperand((Double.parseDouble(tvDisplayScreen.getText().toString())));
                userIsInTheMiddleOfTypingANumber=false;
            }
            mCalculatorBrain.performOperation(buttonPressed);
            if (new Double(mCalculatorBrain.getResult()).equals(0.0)){
                tvDisplayScreen.setText("" + 0);
            } else {
                tvDisplayScreen.setText(df.format(mCalculatorBrain.getResult()));
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save variables on screen orientation change
        outState.putDouble("OPERAND", mCalculatorBrain.getResult());
        outState.putDouble("MEMORY", mCalculatorBrain.getMemory());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore variables on screen orientation change
        mCalculatorBrain.setOperand(savedInstanceState.getDouble("OPERAND"));
        mCalculatorBrain.setMemory(savedInstanceState.getDouble("MEMORY"));
        tvDisplayScreen.setText(df.format(mCalculatorBrain.getResult()));
    }
}
