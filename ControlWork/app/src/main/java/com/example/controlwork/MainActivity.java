package com.example.controlwork;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    Spinner spSex;
    ArrayAdapter<String> aaSex;

    EditText etLying;
    EditText etStanding;

    Button btnComplete;

    String[] sexes = {"М", "Ж"};

    int day;
    int month;
    int year;
    int sex;

    int lyingPulse;
    int standingPulse;

    Calendar calendar;
    TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setElements();
    }

    public void setTvDate(View v) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }

    private void setInitialDateTime() {
        tvDate.setText(DateUtils.formatDateTime(this,
                calendar.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }


    @Override
    public void onDateSet(DatePicker view, int numberOfYear, int monthOfYear, int dayOfMonth) {
        calendar.set(Calendar.YEAR, numberOfYear);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        year = numberOfYear;
        month = monthOfYear + 1;
        day = dayOfMonth;
        setInitialDateTime();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        sex = i + 1;
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvDate:
                setTvDate(tvDate);
                break;
            case R.id.complete_button:
                if (!checkPulse()) {
                    changeBorder(R.drawable.circle_corners_incorrect);
                    Toast.makeText(this, StringStorage.INCORRECT_INPUT, Toast.LENGTH_SHORT).show();
                } else {
                    getPulse();
                    getResponse();
                }
        }
    }

    public void setElements() {
        spSex = findViewById(R.id.spinner_sex);
        aaSex = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sexes);
        aaSex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSex.setAdapter(aaSex);
        spSex.setSelection(0);
        spSex.setOnItemSelectedListener(this);

        etLying = findViewById(R.id.lying_pulse);
        etStanding = findViewById(R.id.standing_pulse);

        btnComplete = findViewById(R.id.complete_button);
        btnComplete.setOnClickListener(this);

        calendar = Calendar.getInstance();
        tvDate = findViewById(R.id.tvDate);
        tvDate.setOnClickListener(this);
        setInitialDateTime();
    }

    public boolean checkPulse() {
        return isInteger(etLying.getText().toString()) && isInteger(etStanding.getText().toString());
    }

    public boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void changeBorder(int drawable) {
        etLying.setBackgroundResource(drawable);
        etStanding.setBackgroundResource(drawable);
    }

    public void getPulse() {
        lyingPulse = Integer.parseInt(etLying.getText().toString());
        standingPulse = Integer.parseInt(etStanding.getText().toString());
    }

    public void getResponse() {
        new Thread(() -> {
            try {
                String parameters = "day=" + day + "&month=" + month + "&year=" + year + "&sex=" + sex + "&m1=" + lyingPulse + "&m2=" + standingPulse;

                URL url = new URL(StringStorage.LINK);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod(StringStorage.METHOD);
                connection.setRequestProperty(StringStorage.HOST_KEY, StringStorage.HOST_VALUE);
                connection.setRequestProperty(StringStorage.CONNECTION_KEY, StringStorage.CONNECTION_VALUE);
                connection.setRequestProperty(StringStorage.CACHE_KEY, StringStorage.CACHE_VALUE);
                connection.setRequestProperty(StringStorage.DNT_KEY, StringStorage.DNT_VALUE);
                connection.setRequestProperty(StringStorage.UPGRADE_KEY, StringStorage.UPGRADE_VALUE);
                connection.setRequestProperty(StringStorage.ACCEPT_KEY, StringStorage.ACCEPT_VALUE);
                connection.setRequestProperty(StringStorage.ENCODING_KEY, StringStorage.ENCODING_VALUE);
                connection.setRequestProperty(StringStorage.LANGUAGE_KEY, StringStorage.LANGUAGE_VALUE);
                connection.setRequestProperty(StringStorage.TYPE_KEY, StringStorage.TYPE_VALUE);connection.setRequestProperty(StringStorage.LENGTH_KEY, String.valueOf(parameters.length()));
                connection.setDoInput(StringStorage.INPUT);
                connection.setDoOutput(StringStorage.OUTPUT);
                connection.connect();

                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(parameters.getBytes(StandardCharsets.UTF_8));
                outputStream.close();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String data;
                StringBuilder response = new StringBuilder();

                while ((data = bufferedReader.readLine()) != null) {
                    response.append(data);
                }
                bufferedReader.close();

                String parsedResponse = new String(response.toString().getBytes(), StandardCharsets.UTF_8).replaceAll(StringStorage.RESPONSE_PATTERN, "");
                Intent intent = new Intent(this, ResultActivity.class);
                intent.putExtra(StringStorage.INTENT_KEY, parsedResponse);
                startActivity(intent);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }).start();
    }
}
