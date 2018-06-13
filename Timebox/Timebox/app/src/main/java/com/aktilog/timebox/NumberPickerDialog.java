package com.aktilog.timebox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NumberPickerDialog extends DialogFragment {
    private NumberPicker.OnValueChangeListener valueChangeListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LinearLayout LL = new LinearLayout(getActivity());
        LL.setOrientation(LinearLayout.HORIZONTAL);

        //declare two numberPickers
        //numberPicker for hours
        final NumberPicker numberPickerH = new NumberPicker(getActivity());
        //numberPicker for minutes
        final NumberPicker numberPickerM = new NumberPicker(getActivity());

        //set min and max values
        numberPickerH.setMinValue(0);
        numberPickerH.setMaxValue(50);
        numberPickerM.setMinValue(0);
        numberPickerM.setMaxValue(59);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50);
        params.gravity = Gravity.CENTER;

        LinearLayout.LayoutParams HPickerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        HPickerParams.weight = 1;
        LinearLayout.LayoutParams MPickerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        MPickerParams.weight = 1;

        LL.setLayoutParams(params);
        LL.addView(numberPickerH, HPickerParams);
        LL.addView(numberPickerM, MPickerParams);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose amount");
        builder.setMessage("Choose hours and minutes: ");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                valueChangeListener.onValueChange(numberPickerH, numberPickerH.getValue(), numberPickerM.getValue());
                //valueChangeListener.onValueChange(numberPickerM, numberPickerM.getValue(), numberPickerM.getValue());
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //valueChangeListener.onValueChange(numberPicker, numberPicker.getValue(), numberPicker.getValue());
            }
        });

        builder.setView(LL);
        return builder.create();
    }

    public NumberPicker.OnValueChangeListener getValueChangeListener() {
        return valueChangeListener;
    }

    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }
}
