package com.yoji.changemarginspinner;

import android.app.Activity;
import android.content.Intent;

import com.yoji.changemarginespinner.R;

public class Utils {
    private static int switchingMargin;

    public static void changeToTheme(Activity activity, int theme) {
        switchingMargin = theme;
        activity.finish();

        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (switchingMargin) {
            default:
            case Margin.DEFAULT:
                activity.setTheme(R.style.AppTheme);
                break;
            case Margin.SMALL:
                activity.setTheme(R.style.SmallMarginTheme);
                break;
            case Margin.MEDIUM:
                activity.setTheme(R.style.MediumMarginTheme);
                break;
            case Margin.LARGE:
                activity.setTheme(R.style.LargeMarginTheme);
                break;
        }
    }

    public static int getMarginId() {
        return switchingMargin;
    }
}
