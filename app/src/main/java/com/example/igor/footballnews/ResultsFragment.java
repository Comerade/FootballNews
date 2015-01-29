package com.example.igor.footballnews;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Igor on 24.01.2015.
 */
public class ResultsFragment extends SherlockFragment {

    String src;
    ProgressDialog progressDialog;
    ArrayList<Team> teamsList = new ArrayList<>();
    ListView lvResult;
    ResultAdapter adapter;

    public static ResultsFragment newInstance(String srcRazdela) {
        ResultsFragment resFragment = new ResultsFragment();
        Bundle args = new Bundle();
        args.putString("srcRazdela", srcRazdela);
        resFragment.setArguments(args);
        return resFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        src = getArguments().getString("srcRazdela");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result,container,false);

        new ThreadResult().execute();

        lvResult = ((ListView)view.findViewById(R.id.listView));

        adapter = new ResultAdapter(getActivity(),teamsList);

        return view;
    }

    class ThreadResult extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            //Отображаем системный диалог загрузки
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.getWindow().setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            System.out.println("onPreExecute...");

        }

        @Override
        protected String doInBackground(String... params) {
            Document doc;

            try {

                if(src.contains("http")) {
                    src = getArguments().getString("srcRazdela");
                }else{
                    src = "http://football.ua/ukraine.html";
                }
                doc = Jsoup.connect(src).get();

                Elements names = doc.select(".tournament-table").select("tr");

                teamsList.clear();
                for (Element link : names) {
                    String name = link.select("a").text();
                    String img = link.select("img").attr("src");
                    String game = link.select(".games").text();
                    String point = link.select(".score").text();
                    Log.d("myLog",name+"   "+img+"   "+game+"   "+point);
                    teamsList.add(new Team(name,img,game,point));

                }
            } catch (IOException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            lvResult.setAdapter(adapter);
            progressDialog.cancel();
        }
    }
}

