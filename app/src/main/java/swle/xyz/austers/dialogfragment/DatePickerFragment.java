package swle.xyz.austers.dialogfragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

import swle.xyz.austers.activity.CarPoolActivity;

/**
*Created by TSOMH on 2020/5/24$
*Description:
*
*/
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(requireActivity(), this, year, month, day);
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // Do something with the date chosen by the user
        ((CarPoolActivity) requireActivity()).setDate(year,month+1,dayOfMonth);
    }
}
