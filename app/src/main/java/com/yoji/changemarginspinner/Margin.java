package com.yoji.changemarginspinner;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({Margin.DEFAULT, Margin.SMALL, Margin.MEDIUM, Margin.LARGE})
public @interface Margin {
    int DEFAULT = 0;
    int SMALL = 1;
    int MEDIUM = 2;
    int LARGE = 3;
}
