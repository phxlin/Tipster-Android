package me.yufanlin.tipster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final int SETTING_REQUEST = 1000;
    private double tipPercentage;

    @BindView(R.id.billEditText) EditText mBillEditText;
    @BindView(R.id.tipAmountTextView) TextView mTipTextView;
    @BindView(R.id.totalAmountTextView) TextView mTotalTextView;
    @BindView(R.id.tipSpinner) Spinner mTipSpinner;
    @OnClick(R.id.billEditText) void onBillClick() {
        displayAmount();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind butter knife api
        ButterKnife.bind(this);

        //Set default bill amount
        mBillEditText.setText(String.valueOf(0));

        //Set spinner
        setSpinner();
    }

    //Setting menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_setting) {
            Intent settingsIntent = new Intent(this, PrefsActivity.class);
            startActivityForResult(settingsIntent, SETTING_REQUEST);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SETTING_REQUEST && resultCode == RESULT_OK) {
            setSpinner();
        }
    }

    //Set spinner
    private void setSpinner() {
        SharedPreferences prefs = getSharedPreferences(PrefsActivity.MY_GLOBAL_PREFS, MODE_PRIVATE);

        //Retrieve tip percentage
        int mLow = prefs.getInt(PrefsActivity.LOW_KEY, 10);
        int mMid = prefs.getInt(PrefsActivity.MID_KEY, 20);
        int mHigh = prefs.getInt(PrefsActivity.HIGH_KEY, 30);

        //Make tip percentage list
        List<String> tipPercentageList = new ArrayList<>();
        tipPercentageList.add(mLow + "%");
        tipPercentageList.add(mMid + "%");
        tipPercentageList.add(mHigh + "%");

        //Set adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, tipPercentageList);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mTipSpinner.setAdapter(adapter);

        //Set listener
        mTipSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tipPercentage = Double.parseDouble(adapterView.getItemAtPosition(i).toString().split("%")[0]);
                displayAmount();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //Display amount
    private void displayAmount() {
        if(!mBillEditText.getText().toString().equals("")) {
            double bill = Double.parseDouble(mBillEditText.getText().toString());
            double tip = bill * tipPercentage / 100;

            String tipString = String.format("%.2f", tip);
            String totalString = String.format("%.2f", tip + bill);

            mTipTextView.setText(String.valueOf(tipString));
            mTotalTextView.setText(String.valueOf(totalString));
        } else {
            Toast.makeText(this, "Please enter a bill amount", Toast.LENGTH_SHORT).show();
        }
    }
}
