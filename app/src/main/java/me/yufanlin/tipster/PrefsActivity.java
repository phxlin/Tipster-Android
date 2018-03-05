package me.yufanlin.tipster;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    SharedPreferences.Editor editor;

    @BindView(R.id.lowEditText) TextView mLowEditView;
    @BindView(R.id.midEditText) TextView mMidEditView;
    @BindView(R.id.highEditText) TextView mHighEditView;

    @OnClick(R.id.lowEditText) void onEtvLowClick() {
        editor = getSharedPrefs().edit();
        putDouble(editor, LOW_KEY, Double.parseDouble(mLowEditView.getText().toString())).apply();
    }
    @OnClick(R.id.midEditText) void onEtvMidClick() {
        editor = getSharedPrefs().edit();
        putDouble(editor, MID_KEY, Double.parseDouble(mMidEditView.getText().toString())).apply();
    }
    @OnClick(R.id.highEditText) void onEtvHighClick() {
        editor = getSharedPrefs().edit();
        putDouble(editor, HIGH_KEY, Double.parseDouble(mHighEditView.getText().toString())).apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefs);

        ButterKnife.bind(this);

        SharedPreferences prefs = getSharedPreferences(PrefsActivity.MY_GLOBAL_PREFS, MODE_PRIVATE);

        mLowEditView.setText(String.valueOf((int)(getDouble(prefs, PrefsActivity.LOW_KEY, 0.10))));
        mMidEditView.setText(String.valueOf((int)(getDouble(prefs, PrefsActivity.MID_KEY, 0.20))));
        mHighEditView.setText(String.valueOf((int)(getDouble(prefs, PrefsActivity.HIGH_KEY, 0.30))));
    }

    //Put double
    SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }

    //Get double
    double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }

    //Get shared preference
    SharedPreferences getSharedPrefs() {
        return getSharedPreferences(MY_GLOBAL_PREFS, MODE_PRIVATE);
    }
}
