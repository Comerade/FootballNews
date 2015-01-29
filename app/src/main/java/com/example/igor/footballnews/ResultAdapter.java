package com.example.igor.footballnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.R.*;
import static android.R.color.*;
import static com.example.igor.footballnews.R.color.*;

/**
 * Created by Igor on 26.01.2015.
 */
public class ResultAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Team> teamsList = new ArrayList<>();


    ResultAdapter(Context context, ArrayList<Team> teamsList){
        this.ctx = context;
        this.teamsList = teamsList;
        this.lInflater = (LayoutInflater)ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return teamsList.size();
    }

    @Override
    public Object getItem(int position) {
        return teamsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if(view==null){
            view = lInflater.inflate(R.layout.result_list_item,parent,false);
        }

        Team t = getTeam(position);

        /**
         * Делаем шапку таблицы
         */
        if(t.name.equals("")&& t.games.equals("И")) {
            ((TextView) view.findViewById(R.id.tvNo)).setText("#");
            ((TextView) view.findViewById(R.id.tvName)).setText("Команда");
            ((TextView) view.findViewById(R.id.tvPoints)).setText("О");
            ((TextView) view.findViewById(R.id.tvGames)).setText("И");
            ((ImageView) view.findViewById(R.id.imgEmblem)).setVisibility(View.INVISIBLE);
        }else{
            /**
             * Заполняем таблицу данными
             */
            ((TextView) view.findViewById(R.id.tvNo)).setText(String.valueOf(position));
            ((TextView) view.findViewById(R.id.tvName)).setText(t.name);
            ((TextView) view.findViewById(R.id.tvPoints)).setText(t.score);
            ((TextView) view.findViewById(R.id.tvGames)).setText(t.games);
            Picasso.with(ctx).load(t.bitmapImg)
                    .into((ImageView) view.findViewById(R.id.imgEmblem));
        }
        return view;
    }

    Team getTeam(int position){
        return ((Team)getItem(position));
    }
}
