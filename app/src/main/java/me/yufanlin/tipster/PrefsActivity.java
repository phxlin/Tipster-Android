package me.yufanlin.tipster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.security.acl.LastOwnerException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrefsActivity extends AppCompatActivity {

    public static final String MY_GLOBAL_PREFS = "my_global_preferences";
    public static final String LOW_KEY = "low_key";
    public static final String MID_KEY = "mid_key";
    public static final String HIGH_KEY = "high_key";

    @BindView(R.id.lowEditText) TextView mLowEditView;
    @BindView(R.id.midEditText) TextView mMidEditView;
    @BindView(R.id.highEditText) TextView mHighEditView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefs);

        //Bind butter knife api
        ButterKnife.bind(this);

        //noinspection ConstantConditions
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_checkmark);

        //Set tip percentage
        SharedPreferences prefs = getSharedPreferences(PrefsActivity.MY_GLOBAL_PREFS, MODE_PRIVATE);

        mLowEditView.setText(String.valueOf(prefs.getInt(LOW_KEY, 10)));
        mMidEditView.setText(String.valueOf(prefs.getInt(MID_KEY, 20)));
        mHighEditView.setText(String.valueOf(prefs.getInt(HIGH_KEY, 30)));
    }


    @Override
    public void onBackPressed() {
        SharedPreferences.Editor editor = getSharedPreferences(MY_GLOBAL_PREFS, MODE_PRIVATE).edit();

        //Put int
        editor.putInt(LOW_KEY, Integer.parseInt(mLowEditView.getText().toString()));
        editor.putInt(MID_KEY, Integer.parseInt(mMidEditView.getText().toString()));
        editor.putInt(HIGH_KEY, Integer.parseInt(mHighEditView.getText().toString()));
        editor.apply();

        //Send back intent with result
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    //Put double
//    SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
//        return edit.putLong(key, Double.doubleToRawLongBits(value));
//    }
//
//    //Get double
//    double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
//        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
//    }
}
