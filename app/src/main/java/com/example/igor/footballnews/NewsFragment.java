package com.example.igor.footballnews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
public class NewsFragment extends SherlockFragment{


    ProgressDialog progressDialog;
    ListView lvNews;
    String src;
    ArrayList<String> titlesList = new ArrayList<>();
    ArrayList<Razdel> newsList = new ArrayList<>();
    ArrayAdapter adapter;

    public static NewsFragment newInstance(String srcRazdela) {
        NewsFragment newsFragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString("srcRazdela", srcRazdela);
        newsFragment.setArguments(args);
        return newsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        src = getArguments().getString("srcRazdela");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list_fragment,container,false);

        lvNews = ((ListView)view.findViewById(R.id.lvNewsList));

        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getSherlockActivity(), DetailNewsActivity.class);
                intent.putExtra("href", newsList.get(position).href);
                startActivity(intent);
            }
        });

        new ThreadNews().execute();

        adapter = new ArrayAdapter(getSherlockActivity(),R.layout.titlemenu_item,R.id.text,titlesList);

        return view;
    }

    class ThreadNews extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            //Отображаем системный диалог загрузки
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.getWindow().setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            System.out.println("(News)onPreExecute...");

        }

        @Override
        protected String doInBackground(String... params) {
            Document doc;
            Log.d("myLog", "(News)doInBackground...start");
            try {

                if(src.contains("http")) {
                    Log.d("myLog", "(News)doInBackground...getArgs");

                    src = getArguments().getString("srcRazdela");
                }else{


                    Log.d("myLog", "(News)doInBackground...setSrc");

                    src = "http://football.ua/ukraine.html";
                }
                doc = Jsoup.connect(src).get();

                Elements names = doc.select(".news-feed li a");

                newsList.clear();
                for (Element link : names) {

                    String title = link.text();
                    String href = link.attr("href");

                    Log.d("myLog", title + "   " + href);

                    titlesList.add(title);
                    newsList.add(new Razdel(title,href));

                }
            } catch (IOException e){
                e.printStackTrace();
            }

            Log.d("myLog", "(News)doInBackground...end");

            return null;

        }

        @Override
        protected void onPostExecute(String s) {

            progressDialog.cancel();
            Log.d("myLog", "(News)PreSetAdapter");

            lvNews.setAdapter(adapter);

            Log.d("myLog", "(News)PostSetAdapter");
        }
    }
}
