package com.yoji.changemarginspinner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.yoji.changemarginespinner.R;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Spinner langSpinner;
    private Spinner marginSpinner;

    ArrayAdapter<CharSequence> langSpinnerAdapter;
    ArrayAdapter<CharSequence> marginSpinnerAdapter;
    private SharedPreferences sharedPrefs;
    private final String LOCALE_KEY = "locale_key";
    private final String MARGIN_KEY = "margin_key";
    private Locale locale;
    private String chosenLocale;
    private int chosenMarginId;

    private AdapterView.OnItemSelectedListener marginSpinnerOnItemSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            chosenMarginId = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private AdapterView.OnItemSelectedListener langSpinnerOnItemSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case Language.RUSSIAN:
                    chosenLocale = "ru";
                    break;
                case Language.ENGLISH:
                    chosenLocale = "en";
                    break;
                case Language.SPANISH:
                    chosenLocale = "es";
                    break;
                case Language.GERMAN:
                    chosenLocale = "de";
                    break;
                case Language.FRENCH:
                    chosenLocale = "fr";
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };


    private View.OnClickListener okBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (chosenLocale != null) {
                //Create chosen locale
                locale = new Locale(chosenLocale);
                Configuration config = new Configuration();
                config.setLocale(locale);
                //Save chosen locale to SharedPrefs
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(LOCALE_KEY, chosenLocale);
                editor.apply();
                //Set changes
                getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                recreate();
            }
            //Save chosen theme to SharedPrefs
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putInt(MARGIN_KEY, chosenMarginId);
            editor.apply();
            Utils.changeToTheme(MainActivity.this, chosenMarginId);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init() {
        langSpinner = findViewById(R.id.langSpinnerId);
        marginSpinner = findViewById(R.id.marginSpinnerId);
        Button okButton = findViewById(R.id.okBtnId);

        sharedPrefs = getSharedPreferences("Language", MODE_PRIVATE);

        langSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.language_array,
                android.R.layout.simple_spinner_dropdown_item);
        marginSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.margin_array,
                android.R.layout.simple_spinner_dropdown_item);

        langSpinner.setAdapter(langSpinnerAdapter);
        langSpinner.setOnItemSelectedListener(langSpinnerOnItemSelectListener);
        marginSpinner.setAdapter(marginSpinnerAdapter);
        marginSpinner.setOnItemSelectedListener(marginSpinnerOnItemSelectListener);
        setLangSpinnerItem();
        setColorSpinnerItem();
        okButton.setOnClickListener(okBtnOnClickListener);
    }

    public void setLangSpinnerItem() {
        String currentLanguage;
        String[] languageArray = getResources().getStringArray(R.array.language_array);
        if (sharedPrefs.getString(LOCALE_KEY, "").matches("")) {
            locale = Locale.getDefault();
        } else {
            locale = new Locale(sharedPrefs.getString(LOCALE_KEY, ""));
        }
        switch (locale.getLanguage()) {
            case "ru":
                currentLanguage = languageArray[Language.RUSSIAN];
                break;
            case "en":
                currentLanguage = languageArray[Language.ENGLISH];
                break;
            case "es":
                currentLanguage = languageArray[Language.SPANISH];
                break;
            case "de":
                currentLanguage = languageArray[Language.GERMAN];
                break;
            case "fr":
                currentLanguage = languageArray[Language.FRENCH];
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + locale.getLanguage());
        }
        langSpinner.setSelection(langSpinnerAdapter.getPosition(currentLanguage));
        String currentLocale = getResources().getConfiguration().locale.getLanguage();
        if (!currentLocale.equals(sharedPrefs.getString(LOCALE_KEY, "")) &&
                !sharedPrefs.getString(LOCALE_KEY, "").matches("")) {
            Configuration config = new Configuration();
            config.setLocale(locale);
            getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            recreate();
        }
    }

    public void setColorSpinnerItem() {
        int sharedTheme = sharedPrefs.getInt(MARGIN_KEY, 4);
        int currentTheme = Utils.getMarginId();
        if (sharedTheme != 4) {
            marginSpinner.setSelection(sharedTheme);
            if (sharedTheme != currentTheme) {
                Utils.changeToTheme(MainActivity.this, sharedTheme);
            }
        }
    }
}