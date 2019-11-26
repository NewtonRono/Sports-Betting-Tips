package com.bet.tips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.EventLogTags;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    TextView textView, Team1, Team2, mTime, mtip1, mtip2;
    ImageView mImage1, mImage2;


    private String code;
    private InterstitialAd mInterstitialAd;




    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Load the next interstitial.
                mInterstitialAd.show();
            }

        });


        AdLoader adLoader = new AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110")
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().build();

                        TemplateView template = findViewById(R.id.my_template);
                        template.setStyles(styles);
                        template.setNativeAd(unifiedNativeAd);

                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());





    Intent intent = getIntent();

    //String url = intent.getExtras().getString("betlink");
    String Club1 = intent.getExtras().getString("club1");
    String Club2 = intent.getExtras().getString("club2");
    String Time = intent.getExtras().getString("time");
    String Image1 = intent.getExtras().getString("image1");
    String Image2 = intent.getExtras().getString("image2");

    code = intent.getExtras().getString("betlink");









    Team1 =(TextView) findViewById(R.id.row_tv_blog_title);
    Team2 =(TextView) findViewById(R.id.row_tv_blog_author);
    mTime =(TextView) findViewById(R.id.row_tv_blog_upload_date);
    mImage1 =(ImageView) findViewById(R.id.imageView1);
    mImage2 =(ImageView) findViewById(R.id.imageView2);


        Team1.setText(Club1);
        Team2.setText(Club2);
        mTime.setText(Time);


        
        



        Glide.with(mImage1.getContext()).load(Image1).into(mImage1);
        Glide.with(mImage2.getContext()).load(Image2).into(mImage2);

        new Description().execute();


    //  textView =(TextView) findViewById(R.id.textView);

    // textView.setText(mBetLink);
}

private class Description extends AsyncTask<Void, Void, Void> {
    String desc;
    String kiprop;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(DetailActivity.this);
       // mProgressDialog.setTitle("Bet Predictions");
        mProgressDialog.setMessage("Loading tip ");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Document mBlogDocument = Jsoup.connect(code).get();

            Elements elements = mBlogDocument.select("span[class=PreviewTip__title-text]").eq(0);
            desc = elements.text();

            Elements boys = mBlogDocument.select("span[class=PreviewTip__title-text]").eq(1);
            kiprop = boys.text();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPostExecute(Void result) {

        // Set description into TextView


            mtip1 =(TextView) findViewById(R.id.tip1);
            mtip2 =(TextView) findViewById(R.id.tip2);

            mtip1.setText(desc);
            mtip2.setText(kiprop);

            mProgressDialog.dismiss();








        // button.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //   public void onClick(View view) {
        //      Intent i = new Intent(this,DetailActivity.class);
        //      i.putExtra("betlink", mBetLink);
        //      startActivity(i);

        //  }
        // });


    }



}

    @Override
    public void onBackPressed() {

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            DetailActivity.super.onBackPressed();

        } else {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            DetailActivity.super.onBackPressed();

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }

                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    }
