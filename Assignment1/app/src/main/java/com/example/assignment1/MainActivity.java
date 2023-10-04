package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.view.View;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


// What doesn't work.
// 1. Pressing the +, * or / at the start with numbers after.
// - at the start works.

public class MainActivity extends AppCompatActivity {

    private TextView result;
    private String current = "";
    private boolean resulted = false;
    Pattern pattern = Pattern.compile("[-+*/]");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.result);
    }

    // applies to all buttons
    public void buttonClick(View view) {
        Log.d("Current", current);
        Log.d("Text", ((TextView) view).getText().toString());
        // gets the button text
        String buffer = ((TextView) view).getText().toString();

        // checks if the button is an operator.
        // If it is and the equal button has not been pressed
        // set it to false.
        if (buffer.equals("+") | buffer.equals("-") | buffer.equals("/") | buffer.equals("*")) {
            resulted = false;
            Log.d("Inside", "hi");
        }
        // If the equal button was pressed, but there is no operator
        // keep the value the same.
        if (resulted) {
            if (!current.isEmpty()) {
                current = "";
                current += ((TextView) view).getText().toString();
            }
        }
        // if there was an operator pressed after the equal
        // keep the result.
        else {
            current += ((TextView) view).getText().toString();
        }
        result.setText(current);
    }

    // clears the calculator
    public void clearButton(View view) {
        current = "";
        result.setText("");
        resulted = false;
    }

    // gives the result
    public void equalButton(View view) {

        // checks if the regex was matched
        Matcher matcher = pattern.matcher(current);
        Log.d("result", current);

        // Three arrayList for:
        // matchedValue => stores operators
        // numbers => stores numbers and negative numbers
        // indexOfOperators => Stores the index of the operators
        ArrayList<String> matchedValues = new ArrayList<>();
        ArrayList<String> numbers = new ArrayList<>();
        ArrayList<Integer> indexOfOperators = new ArrayList<>();


        int index = 0;
        while (matcher.find()) {
            String match = matcher.group();
            // checks the string to see if the first input was a "-"
            if (match.equals("-") && index == 0 && current.indexOf("-") == 0) {
                numbers.add("-");
            }
            // fills up the arrayLists based on type
            else {
                numbers.add(current.substring(index, matcher.start()));
                matchedValues.add(match);
                indexOfOperators.add(matcher.start());
            }
            index = matcher.end();
        }


        // if there is no operators in the string, keep the
        // value the same
        if (!current.isEmpty() && matchedValues.isEmpty()) {
            result.setText(current);
        }

        // If first index was a "-", then add it to the front of the
        // first number that appears
        if (!numbers.isEmpty()) {
            if (numbers.get(0).equals("-") && numbers.size() >= 2) {
                String buffer = numbers.get(1);
                numbers.remove(0);
                numbers.set(0, "-" + buffer);
            }
        }

        numbers.add(current.substring(index));

        Log.d("Y", String.valueOf(matchedValues));
        Log.d("Y", String.valueOf(numbers));
        Log.d("Y", String.valueOf(indexOfOperators));


        if (!matchedValues.isEmpty() && !numbers.isEmpty()) {
            double value = 0;

            while (matchedValues.contains("*")) {
                try {
                    int in = matchedValues.indexOf("*");
                    Log.d("in", String.valueOf(in));
                    double num1 = Double.parseDouble(numbers.get(in));
                    double num2 = Double.parseDouble(numbers.get(in + 1));
                    double values = num1 * num2;
                    numbers.set(in, String.valueOf(values));
                    Log.d("num1", String.valueOf(num1));
                    Log.d("num2", String.valueOf(num2));
                    matchedValues.remove(in);
                    indexOfOperators.remove(in);
                    numbers.remove(in + 1);
                    Log.d("*", String.valueOf(matchedValues));
                    Log.d("Num", String.valueOf(numbers));
                    value = values;
                } catch (Exception e) {
                    current = "";
                    result.setText(R.string.error);
                    return;
                }

            }

            while (matchedValues.contains("/")) {
                try {
                    int in = matchedValues.indexOf("/");
                    Log.d("in", String.valueOf(in));
                    double num1 = Double.parseDouble(numbers.get(in));
                    double num2 = Double.parseDouble(numbers.get(in + 1));
                    try {
                        if (num2 == 0) {
                            current = "";
                            result.setText(R.string.zero_divide);
                            return;
                        }
                        double values = num1 / num2;
                        numbers.set(in, String.valueOf(values));
                        Log.d("num1", String.valueOf(num1));
                        Log.d("num2", String.valueOf(num2));
                        matchedValues.remove(in);
                        indexOfOperators.remove(in);
                        numbers.remove(in + 1);
                        Log.d("*", String.valueOf(matchedValues));
                        Log.d("Num", String.valueOf(numbers));
                        value = values;
                    } catch (Exception e) {
                        current = "";
                        result.setText(R.string.error);
                        return;
                    }
                } catch (Exception e) {
                    current = "";
                    result.setText(R.string.error);
                    return;
                }

            }

            while (matchedValues.contains("+")) {
                try {
                    int in = matchedValues.indexOf("+");
                    double num1 = Double.parseDouble(numbers.get(in));
                    double num2 = Double.parseDouble(numbers.get(in + 1));
                    double values = num1 + num2;
                    numbers.set(in, String.valueOf(values));
                    Log.d("num1", String.valueOf(num1));
                    Log.d("num2", String.valueOf(num2));
                    matchedValues.remove(in);
                    indexOfOperators.remove(in);
                    numbers.remove(in + 1);
                    Log.d("+", String.valueOf(matchedValues));
                    Log.d("Num", String.valueOf(numbers));
                    Log.d("Value", String.valueOf(values));
                    value = values;
                } catch (Exception e) {
                    current = "";
                    result.setText(R.string.error);
                    return;
                }

            }

            while (matchedValues.contains("-")) {
                try {
                    int in = matchedValues.indexOf("-");

                    double num1 = Double.parseDouble(numbers.get(in));
                    double num2 = Double.parseDouble(numbers.get(in + 1));
                    double values = num1 - num2;
                    numbers.set(in, String.valueOf(values));
                    matchedValues.remove(in);
                    indexOfOperators.remove(in);
                    numbers.remove(in + 1);
                    value = values;
                } catch (Exception e) {
                    current = "";
                    result.setText(R.string.error);
                    return;
                }

            }

            Log.d("val", String.valueOf(value));

            // if the value is not a double, present it as
            // an integer.
            int checkRound;
            if (value % 1 == 0) {
                checkRound = (int) value;
                current = String.valueOf(checkRound);
                result.setText(String.valueOf(checkRound));
            } else {
                current = String.valueOf(value);
                result.setText(String.valueOf(value));
            }
            Log.d("Length", String.valueOf(current.length()));
            Log.d("cu", current);
            resulted = true;
        }

    }
}