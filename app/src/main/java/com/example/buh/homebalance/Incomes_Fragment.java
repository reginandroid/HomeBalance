package com.example.buh.homebalance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;



public class Incomes_Fragment extends Fragment {
    Cursor c;
    ListView listView;
    Balance_DBHelper dbHelper;
   CustomArrayAdapter customArrayAdapter;
    SharedPreferences sp;
    TextView totalSum;
    public Incomes_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_incomes, container, false);
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        listView =(ListView) rootView.findViewById(R.id.incomes_list);
        totalSum = (TextView)rootView.findViewById(R.id.tvSum);
      refreshListViewWithSelectedData();
         listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
             public boolean onItemLongClick(final AdapterView<?> parent, View view,
                                            final int pos, final long id) {
                // Log.d("my", "selected" +String.valueOf(id));
                 new AlertDialog.Builder(getActivity())
                        .setTitle("Delete Item")
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dbHelper = new Balance_DBHelper(getContext());
                                dbHelper.remove(Banance_Contract.TABLE_NAME_INCOMES, pos);
                                Log.d("my", "was deleted" +String.valueOf(pos));
                                refreshListViewWithSelectedData();
                               ;}

                            })

                         .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                                    .setIcon(android.R.drawable.ic_menu_delete)
                            .show();

                    return true;
                        }
             });

        return rootView;
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
Log.d("my", "onDestroy was made");

    }

    @Override
    public void onResume()
    {
        super.onResume();

refreshListViewWithSelectedData();

        if (!getUserVisibleHint())
        {
            return;
        }

        StartActivity mainActivity = (StartActivity) getActivity();
        mainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Dialog_NewIncomeCharge df = Dialog_NewIncomeCharge.newInstance("NEW INCOME", Banance_Contract.TABLE_NAME_INCOMES);

                df.show(getFragmentManager(), "df_income");

            }
        });
        refreshListViewWithSelectedData();
    }

    public void refreshListViewWithSelectedData() {
        int total_sum = 0;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String date_from = sharedPreferences.getString("date_from", "");
        String date_to = sharedPreferences.getString("date_to", "");
        dbHelper = new Balance_DBHelper(getContext());
        ArrayList<Balance_Item> list= dbHelper.showDataByDate(Banance_Contract.TABLE_NAME_INCOMES, date_from, date_to);
        for (Balance_Item item:list){
        Log.d("my", "Item =" + item.getDate()+ item.getSum() + item.getItem_name() );}
        for (int i = 0; i<list.size(); i++){
            total_sum+=list.get(i).getSum();
        }
        Log.d("my", String.valueOf(total_sum));
        totalSum.setText(String.valueOf(total_sum));
        (sharedPreferences.edit().putInt("total_income", total_sum)).commit();

        customArrayAdapter = new CustomArrayAdapter(getContext(), R.layout.item_list, list);
        listView.setAdapter(customArrayAdapter);
        customArrayAdapter.notifyDataSetChanged();
    }
    }







