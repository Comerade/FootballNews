package com.example.igor.footballnews;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Igor on 24.01.2015.
 */
public class RazdelAdapter extends BaseAdapter{

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Razdel> razdelu;

    RazdelAdapter(Context context, ArrayList<Razdel> razdelu){
        Log.d("myLog","RazdelAdapter(Constructor)");
        this.ctx = context;
        this.razdelu = razdelu;
        this.lInflater = (LayoutInflater)ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return razdelu.size();
    }

    @Override
    public Object getItem(int position) {
        return razdelu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = convertView;
        if(view==null){
            view = lInflater.inflate(R.layout.sidemenu_item,parent,false);
        }

        Razdel r = getRazdel(position);

        ((TextView)view.findViewById(R.id.text)).setText(r.title);

        return view;
    }

    Razdel getRazdel(int position){
        return ((Razdel)getItem(position));
    }

}
