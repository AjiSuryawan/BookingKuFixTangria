package com.tangria.spa.bookingku.Activity.Booking;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import com.tangria.spa.bookingku.R;

import java.util.Calendar;

/**
 * Created by deepak.rattan on 9/4/2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Use the current date as the default date in the picker
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        Dialog dialog = new DatePickerDialog(getContext(),R.style.DialogTheme, this, year, month, day);
        ((DatePickerDialog) dialog).getDatePicker().setMinDate(System.currentTimeMillis());
        Calendar maxDate = Calendar.getInstance();
        maxDate.add(Calendar.YEAR, 1);
        ((DatePickerDialog) dialog).getDatePicker().setMaxDate(maxDate.getTimeInMillis());
        //Create a new instance of DatePickerDialog and return it
        return dialog;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        //Do something with the date chosen by the user
        BookingActivity mainActivity = (BookingActivity) getActivity();
        mainActivity.processDatePickerResult(year, month, day);
    }
}
