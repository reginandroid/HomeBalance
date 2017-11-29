package com.example.buh.homebalance;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Calendar;



public class Dialog_ChoosePeriod extends DialogPreference {
    final String LOG_TAG = "my";
    Button from_date, to_date;
    private int mYear, mMonth, mDay;
    EditText txtFrom, txtTo;


    public Dialog_ChoosePeriod(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDialogLayoutResource(R.layout.dialog_date_pref);
        setPositiveButtonText("SELECT");
        setNegativeButtonText("CANCEL");

        setDialogIcon(null);

    }

    @Override
    protected void onBindDialogView(View view)  {
        txtFrom = (EditText)view.findViewById(R.id.from_date) ;
        txtTo= (EditText)view.findViewById(R.id.to_date) ;
        from_date = (Button)view.findViewById(R.id.select_from_date);
        from_date.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             final Calendar c = Calendar.getInstance();
                                             mYear = c.get(Calendar.YEAR);
                                             mMonth = c.get(Calendar.MONTH);
                                             mDay = c.get(Calendar.DAY_OF_MONTH);


                                             DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                                                     new DatePickerDialog.OnDateSetListener() {

                                                         @Override
                                                         public void onDateSet(DatePicker view, int year,
                                                                               int monthOfYear, int dayOfMonth) {
                                                             String day =null, month=null;
                                                             int current_month = monthOfYear+1;
                                                             if(current_month < 10){

                                                                 month= "0" + current_month;
                                                             } else
                                                                 month = String.valueOf(current_month);
                                                             if(dayOfMonth < 10){

                                                                 day= "0" + dayOfMonth ;
                                                             }
                                                             else day = String.valueOf(dayOfMonth);

                                                             txtFrom.setText(year + "-" + month + "-" + day );

                                                             SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                                             (sharedPreferences.edit().putString("date_from", year + "-" + month + "-" + day )).commit();
                                                         }
                                                     }, mYear, mMonth, mDay);
                                             datePickerDialog.show();  }
                                         });

                to_date = (Button) view.findViewById(R.id.select_to_date);
        to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                int current_month = monthOfYear+1;
                                String day =null, month=null;
                                if(current_month < 10){
                                    month= "0" + current_month;
                                } else
                                    month = String.valueOf(current_month);
                                if(dayOfMonth < 10){
                                    day= "0" + dayOfMonth ;
                                }
                                else day = String.valueOf(dayOfMonth);

                                txtTo.setText(year + "-" + month + "-" + day );
                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                (sharedPreferences.edit().putString("date_to", year + "-" + month + "-" + day )).commit();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }

        });
        super.onBindDialogView(view);
    }
    @Override
    protected void onDialogClosed(boolean positiveResult) {

        if (positiveResult) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String from = sharedPreferences.getString("date_from", "");
            String to = sharedPreferences.getString("date_to", "");
            Toast.makeText(getContext(), "The period from " + from + " to " + to + " was selected", Toast.LENGTH_SHORT).show();

        } else {

        }
    }

}





