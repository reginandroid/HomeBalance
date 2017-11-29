package com.example.buh.homebalance;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class Balance_Fragment extends Fragment {
    TextView balance_sum;
    SharedPreferences sp;
    Balance_DBHelper dbHelper;
    int balance;

    final static String BALANCE = "balance";
    Cursor c;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_balance, container, false);
    balance_sum = (TextView)rootView.findViewById(R.id.balanceSum);
    sp=  PreferenceManager.getDefaultSharedPreferences(getContext());


        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible && isResumed())
        {
            onResume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(balance!=0)
        dbHelper = new Balance_DBHelper(getContext());
        dbHelper.saveBalanceSum(Banance_Contract.TABLE_NAME_BALANCE, balance);

        c = dbHelper.queryDB(Banance_Contract.TABLE_NAME_BALANCE);
        c.moveToLast() ;
        Log.d("my", c.getString(c.getColumnIndex(Banance_Contract.COLUMN_SUM)) + " " + c.getString(c.getColumnIndex(Banance_Contract.COLUMN_ID)));
        c.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (balance>=0){
            balance_sum.setBackgroundColor(Color.GREEN);
        }
        else
            balance_sum.setBackgroundColor(Color.RED);
        balance_sum.setText(String.valueOf(balance));
      //  if (balance != 0) {


            //Log.d("my", String.valueOf(balance));
//        }
//        if (balance == 0) {
//            dbHelper = new Balance_DBHelper(getContext());
//            if (c == null) {
//                int total_income = sp.getInt("total_income", 0);
//                int total_charge = sp.getInt("total_charge", 0);
//                balance = total_income - total_charge;
//                balance_sum.setText(String.valueOf(balance));
//            }
//         else {
//            c = dbHelper.queryDB(Banance_Contract.TABLE_NAME_BALANCE);
//            c.moveToLast();
//            balance = Integer.parseInt(c.getString(c.getColumnIndex(Banance_Contract.COLUMN_SUM)));
//            balance_sum.setText(String.valueOf(balance));
//
//        }
//    }

        if (!getUserVisibleHint())
        {
            return;
        }

        StartActivity mainActivity = (StartActivity) getActivity();
        mainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "SELECT CHARGE OR INCOME", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt(BALANCE, balance);
        Log.d("my", String.valueOf(balance));

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int total_income = sp.getInt("total_income", 0);
        int total_charge = sp.getInt("total_charge", 0);
        balance = total_income - total_charge;

        if (savedInstanceState != null) {
            balance = savedInstanceState.getInt(BALANCE);

        }
    }}