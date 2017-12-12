package com.example.ashwini.customtimepicker;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class CustomTimePicker extends AppCompatActivity implements TimePicker.OnTimeChangedListener{

    private Typeface _fontAwesomeFont;
    private EditText _time;
    private TimePicker _timePicker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_time_picker);

        _fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesomeicon.ttf");
        _time = (EditText) findViewById(R.id.edt_time);

        _time.setFocusableInTouchMode(false);
        _time.setFocusable(false);
        _time.setCursorVisible(false);

        initClearTime();
        initTimePicker();
        initSetTime();
    }

    private void initClearTime(){
        TextView mTimeClearAll = (TextView) findViewById(R.id.time_clear_all);
        mTimeClearAll.setTypeface(_fontAwesomeFont);

        mTimeClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _time.setText("");
            }
        });
    }

    private void initTimePicker(){
        _timePicker = (TimePicker) findViewById(R.id.myTimePicker);
        _timePicker.setOnTimeChangedListener(this);

        if (_timePicker.getChildCount() > 0) {
            LinearLayout mLinearLayoutOne = (LinearLayout) _timePicker.getChildAt(0);
            if (mLinearLayoutOne.getChildCount() > 0) {
                final int childcount = mLinearLayoutOne.getChildCount();
                for (int k = 0; k < childcount; k++) {
                    if (k == 0) {
                        LinearLayout mLinearLayoutTwo = (LinearLayout) mLinearLayoutOne.getChildAt(0);
                        if (mLinearLayoutTwo.getChildCount() > 0) {
                            final int childCount = mLinearLayoutTwo.getChildCount();
                            for (int j = 0; j < childCount; j++) {
                                NumberPicker mNumberPicker = null;
                                try {
                                    mNumberPicker = (NumberPicker) mLinearLayoutTwo.getChildAt(j);
                                } catch (ClassCastException e) {
                                    continue;
                                }

                                java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
                                for (java.lang.reflect.Field pf : pickerFields) {
                                    if (pf.getName().equals("mSelectionDivider")) {
                                        pf.setAccessible(true);
                                        try {
                                            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0062c7"));
                                            pf.set(mNumberPicker, colorDrawable);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    }
                                }

                                final int count = mNumberPicker.getChildCount();
                                for (int i = 0; i < count; i++) {
                                    View child = mNumberPicker.getChildAt(i);
                                    if (child instanceof EditText) {
                                        try {
                                            Field selectorWheelPaintField = mNumberPicker.getClass().getDeclaredField("mSelectorWheelPaint");
                                            selectorWheelPaintField.setAccessible(true);
                                            ((Paint) selectorWheelPaintField.get(mNumberPicker)).setColor(Color.parseColor("#323e48"));
                                            ((EditText) child).setTextColor(Color.parseColor("#323e48"));
                                            mNumberPicker.invalidate();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        View mNumberPickerView = (View) mLinearLayoutOne.getChildAt(1);
                        NumberPicker mNumberPicker = null;
                        if(mNumberPickerView!=null && mNumberPickerView instanceof NumberPicker)
                            mNumberPicker = (NumberPicker)mNumberPickerView;

                        if(mNumberPicker!=null) {
                            java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
                            for (java.lang.reflect.Field pf : pickerFields) {
                                if (pf.getName().equals("mSelectionDivider")) {
                                    pf.setAccessible(true);
                                    try {
                                        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0062c7"));
                                        pf.set(mNumberPicker, colorDrawable);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                }
                            }

                            final int count = mNumberPicker.getChildCount();
                            for (int i = 0; i < count; i++) {
                                View child = mNumberPicker.getChildAt(i);
                                if (child instanceof EditText) {
                                    try {
                                        Field selectorWheelPaintField = mNumberPicker.getClass().getDeclaredField("mSelectorWheelPaint");
                                        selectorWheelPaintField.setAccessible(true);
                                        ((Paint) selectorWheelPaintField.get(mNumberPicker)).setColor(Color.parseColor("#323e48"));
                                        ((EditText) child).setTextColor(Color.parseColor("#323e48"));
                                        mNumberPicker.invalidate();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int i, int i1) {

    }

    private void initSetTime(){
        Button mSetTime = (Button) findViewById(R.id.btn_set_time);
        mSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mHourInt = 0;
                int mMinInt = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mHourInt = _timePicker.getHour();
                    mMinInt = _timePicker.getMinute();
                } else {
                    mHourInt = _timePicker.getCurrentHour();
                    mMinInt = _timePicker.getCurrentMinute();
                }
                String mTimeStr = getTimeString(mHourInt, mMinInt);

                try {
                    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                    final Date dateObj = sdf.parse(mTimeStr);
                    _time.setText(new SimpleDateFormat("K:mm a").format(dateObj));
                } catch (final ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static String getTimeString(int mHourInt, int mMinInt) {
        String mHour = String.format("%02d", mHourInt);
        String mMinute = String.format("%02d", mMinInt);
        String dateStr = mHour + ":" + mMinute;

        return dateStr;
    }
}
