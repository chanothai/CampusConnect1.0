package com.company.zicure.campusconnect.dialog;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

import gallery.zicure.company.com.modellibrary.models.DateModel;
import gallery.zicure.company.com.modellibrary.utilize.EventBusCart;
import gallery.zicure.company.com.modellibrary.utilize.ModelCart;

/**
 * Created by 4GRYZ52 on 1/30/2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private int year, month, day;
    private DatePickerDialog pickDialog = null;

    public DatePickerFragment(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();

        if (getDate().getYear() == 0 && getDate().getMonth() == 0 && getDate().getDay() == 0){
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }else{
            year = getDate().getYear();
            month = getDate().getMonth();
            day = getDate().getDay();
        }

        pickDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, this, year, month, day);
        pickDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        pickDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return pickDialog;
    }

    private DateModel getDate(){
        return ModelCart.getInstance().getModel().dateModel;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        getDate().setDay(day);
        getDate().setMonth(month + 1);
        getDate().setYear(year);
        EventBusCart.getInstance().getEventBus().post(getDate());
    }
}
