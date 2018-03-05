package me.yufanlin.tipster;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tipAmountTextView) TextView tipTextView;
    @BindView(R.id.totalAmountTextView) TextView totalTextView;
    @BindView(R.id.tipSpinner) Spinner tipSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        SharedPreferences prefs = getSharedPreferences(PrefsActivity.MY_GLOBAL_PREFS, MODE_PRIVATE);

        double mLow = (getDouble(prefs, PrefsActivity.LOW_KEY, 10))/100;
        double mMid = (getDouble(prefs, PrefsActivity.MID_KEY, 20))/100;
        double mHigh = (getDouble(prefs, PrefsActivity.HIGH_KEY, 30))/100;

        List<Double> tipPercentage = new ArrayList<>();
        tipPercentage.add(mLow);
        tipPercentage.add(mMid);
        tipPercentage.add(mHigh);

        ArrayAdapter<Double> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tipPercentage);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipSpinner.setAdapter(adapter);

        tipSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, adapterView.getItemAtPosition(i) + " is selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_setting) {
            Intent settingsIntent = new Intent(this, PrefsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Get double
    double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }
}
