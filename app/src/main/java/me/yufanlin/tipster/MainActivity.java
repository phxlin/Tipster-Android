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

    public static final int SETTING_REQUEST = 1000;

    @BindView(R.id.tipAmountTextView) TextView mTipTextView;
    @BindView(R.id.totalAmountTextView) TextView mTotalTextView;
    @BindView(R.id.tipSpinner) Spinner mTipSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSpinner();
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

    private void setSpinner() {
        SharedPreferences prefs = getSharedPreferences(PrefsActivity.MY_GLOBAL_PREFS, MODE_PRIVATE);

        int mLow = prefs.getInt(PrefsActivity.LOW_KEY, 10);
        int mMid = prefs.getInt(PrefsActivity.MID_KEY, 20);
        int mHigh = prefs.getInt(PrefsActivity.HIGH_KEY, 30);

        List<String> tipPercentage = new ArrayList<>();
        tipPercentage.add(mLow + "%");
        tipPercentage.add(mMid + "%");
        tipPercentage.add(mHigh + "%");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tipPercentage);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTipSpinner.setAdapter(adapter);

        mTipSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, adapterView.getItemAtPosition(i) + " is selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
