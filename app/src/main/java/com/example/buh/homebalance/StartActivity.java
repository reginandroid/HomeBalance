package com.example.buh.homebalance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity implements Dialog_NewIncomeCharge.OnDBUpdatedListener {

    private FragmentTabHost mTabHost;
    public FloatingActionButton fab;
    SharedPreferences sp;
    Dialog_NewIncomeCharge.OnDBUpdatedListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("INCOMES"),
                Incomes_Fragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("CHARGES"),
                Charges_Fragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator("BALANCE"),
                Balance_Fragment.class, null);


        sp = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor editor = sp.edit();
        if (sp.getString("date_from", null)==null & sp.getString("date_to", null)==null ){
            Log.d("my", "from" + sp.getString("date_from", "") + "to" +  sp.getString("date_to", ""));
        editor.clear();
        editor.putString("date_from", "1990-01-01");
        editor.putString("date_to", "2100-12-31");
        editor.commit();
        String from = sp.getString("date_from", "");
        String to = sp.getString("date_to", "");
        Toast.makeText(this, "You choose data for all period" + from + " " + to, Toast.LENGTH_SHORT).show();}


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.settings) {
            Intent i = new Intent(this, PrefActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onDBChanged() {

    Incomes_Fragment f = (Incomes_Fragment)this.getSupportFragmentManager().findFragmentByTag("tab1");
           f.refreshListViewWithSelectedData();
try {
    Charges_Fragment f1 = (Charges_Fragment)this.getSupportFragmentManager().findFragmentByTag("tab2");
    f1.refreshListViewWithSelectedData();
}catch (NullPointerException e){
    e.printStackTrace();
}


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

