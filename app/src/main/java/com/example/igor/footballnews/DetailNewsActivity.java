package com.example.igor.footballnews;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import java.io.IOException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Igor on 24.01.2015.
 */
public class DetailNewsActivity extends SherlockActivity{

    TextView tvTitle;
    TextView tvSubTitle;
    TextView tvText;

    LinearLayout detailLayout;

    public ImageView imageView;

    ProgressDialog progressDialog;

    String textTitle;
    String textSubTitle;
    String textText;
    String urlImage;

    Elements title;
    Elements subTitle;
    Elements text;

    public Bitmap networkBitmap = null;

    public String href;

    @SuppressLint("NewApi")
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.detail_activity);
        href = getIntent().getStringExtra("href");

        detailLayout = (LinearLayout)findViewById(R.id.mainLayout);
        detailLayout.setVisibility(LinearLayout.INVISIBLE);

        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvSubTitle = (TextView)findViewById(R.id.tvSubTitle);
        tvText = (TextView)findViewById(R.id.tvText);

        imageView = (ImageView)findViewById(R.id.imageView1);

        new NewThread().execute();
    }

    public class NewThread extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(DetailNewsActivity.this);
            progressDialog.getWindow().setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            Document doc;

            try{
                doc = Jsoup.connect(href).get();

                textTitle = doc.select("h1").text();
                textSubTitle = doc.select(".intro").first().text();

                urlImage = doc.select(".article-photo img").attr("src").toString();

                Elements textLinks = doc.select(".article-text p");
                for(Element link:textLinks){
                    textText += link.text()+"\n\n";
                }

                textText = textText.substring(4);//Почему то вначале стревает "null" в строке
                //вот так собственно и избавляемся от него


                URL myUrl = new URL(urlImage);

                networkBitmap = BitmapFactory.decodeStream(myUrl
                        .openConnection().getInputStream());

            } catch (IOException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result){
            progressDialog.cancel();
            tvTitle.setText(textTitle);
            tvSubTitle.setText(textSubTitle);
            tvText.setText(textText);
            imageView.setImageBitmap(networkBitmap);
            detailLayout.setVisibility(LinearLayout.VISIBLE);
        }

    }
}
