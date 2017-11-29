package com.example.buh.homebalance;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import java.util.Calendar;


public class Dialog_NewIncomeCharge extends DialogFragment implements View.OnClickListener {
    final String LOG_TAG = "myLogs";
    Button select_date, btn_save, btn_cancel;
    private int mYear, mMonth, mDay;
    EditText txtDate, income_name, income_summ;
    public static final String TITLE = "title";
    public static final String TABLE_NAME = "table_name";
    String title;
    String table_name;
    private  OnDBUpdatedListener mCallback;

    public static Dialog_NewIncomeCharge newInstance(String title, String tablename) {
        Dialog_NewIncomeCharge f = new Dialog_NewIncomeCharge();
        f.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        Bundle b = new Bundle();
        b.putString(TITLE, title);
        b.putString(TABLE_NAME, tablename);
        f.setArguments(b);
        return f;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            title = bundle.getString(TITLE);
            Log.d("my", title);
            getDialog().setTitle(title);

        }

        View v = inflater.inflate(R.layout.income_dialog, null);
         select_date = (Button) v.findViewById(R.id.select_date);
        select_date.setOnClickListener(this);
        txtDate = (EditText)v.findViewById(R.id.in_date) ;
        txtDate.setOnClickListener(this);
        btn_save = (Button)v.findViewById(R.id.btnSave);
        btn_cancel = (Button)v.findViewById(R.id.btnCancel);
        income_name = (EditText)v.findViewById(R.id.income_name);
        income_summ = (EditText)v.findViewById(R.id.income_summ);
        v.findViewById(R.id.btnSave).setOnClickListener(this);
        v.findViewById(R.id.btnCancel).setOnClickListener(this);

        return v;
    }

  public void onClick(View v) {

        if (v == select_date) {

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @TargetApi(24)
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

                            txtDate.setText(year + "-" + month + "-" + day );
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                            (sharedPreferences.edit().putString("item_date",year + "-" + month + "-" + day )).commit();
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();

        }
        if (v == btn_save) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
            String date = sharedPref.getString("item_date", "");
            String item_name = income_name.getText().toString();
            int item_sum = Integer.parseInt(income_summ.getText().toString());
            Balance_Item item = new Balance_Item(date, item_name, item_sum);
            Balance_DBHelper dbHelper= new Balance_DBHelper(getContext());
            Bundle bundle = this.getArguments();
            if (bundle != null) {
               table_name = bundle.getString(TABLE_NAME);
            }
            dbHelper.saveArticleItem(table_name, item);

            Log.d("my",item.getDate() + " "+ item.getItem_name() + " "+ item.getSum());

            mCallback.onDBChanged();
            dismiss();
        }
            if (v == btn_cancel) {
                getDialog().dismiss();
            }

    }
    public void onDismiss(DialogInterface dialog) {
        mCallback.onDBChanged();
        super.onDismiss(dialog);
        Log.d("my", "Dialog 1: onDismiss");
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(LOG_TAG, "Dialog 1: onCancel");
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnDBUpdatedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnDBUpdatedListenerr");
        }}
    public interface OnDBUpdatedListener{
    public void onDBChanged();
    }

}
