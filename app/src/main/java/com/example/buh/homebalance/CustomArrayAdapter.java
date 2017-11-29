package com.example.buh.homebalance;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class CustomArrayAdapter extends ArrayAdapter<Balance_Item> {
    private ArrayList<Balance_Item> list;
    Context context;

    public CustomArrayAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<Balance_Item> arrayList) {
        super(context, R.layout.item_list, arrayList);
        this.list =arrayList;
        this.context =context;
    }
    @Override
    @NonNull
    @SuppressWarnings("NullableProblems")
    public View getView(int position, View convertView, ViewGroup parent)  {

        MyViewHolder mViewHolder;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.item_list, null);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {

            mViewHolder = (MyViewHolder) convertView.getTag();
        }

       Balance_Item currentItem = list.get(position);

        mViewHolder.date.setText(currentItem.getDate());
        mViewHolder.name.setText(currentItem.getItem_name());
        mViewHolder.sum.setText(String.valueOf(currentItem.getSum()));


        return convertView;
    }
    private class MyViewHolder {
        TextView date, name, sum ;

        private MyViewHolder(View item) {
            date = (TextView) item.findViewById(R.id.date);
            name = (TextView) item.findViewById(R.id.name);
            sum = (TextView) item.findViewById(R.id.sum);

        }
    }
}


