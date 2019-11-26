package com.bet.tips;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Previous extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    private String link = "https://www.freesupertips.com/previews/?date=";
    private ArrayList<String> mAuthorNameList = new ArrayList<>();
    private ArrayList<String> mImageList = new ArrayList<>();
    private ArrayList<String> mImageList2 = new ArrayList<>();
    private ArrayList<String> mBlogUploadDateList = new ArrayList<>();
    private ArrayList<String> mBlogTitleList = new ArrayList<>();
    private ArrayList<String> mBetLink = new ArrayList<>();
    private Button button1, button2;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;



    private String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return dateFormat.format(cal.getTime());
    }



    // ImageView mImageList;
    //ImageView mImageList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Predictions for " + getYesterdayDateString());

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);

        new Description().execute();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Previous.this,MainActivity.class);
                startActivity(i);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Previous.this,Tommorow.class);
                startActivity(i);
            }
        });

    }

    private class Description extends AsyncTask<Void, Void, Void> {
        String desc;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(Previous.this);
            mProgressDialog.setTitle("Bet Predictions");
            mProgressDialog.setMessage("Loading data for " + getYesterdayDateString());
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {


                String url = link + getYesterdayDateString();

                // Connect to the web site
                Document mBlogDocument = Jsoup.connect(url).get();




                // Using Elements to get the Meta data
                Elements mElementDataSize = mBlogDocument.select("a[class=ind-prev-holder]");
                // Locate the content attribute
                int mElementSize = mElementDataSize.size();




                for (int i = 0; i < mElementSize; i++) {
                    mBlogDocument.select("div[class=prev-mob-btn]").remove();
                    mBlogDocument.select("div[class=PreviewListItem__tv-channel]").remove();


                    Elements mTipLink = mElementDataSize.select("a");

                    for (Element link : mTipLink) {
                        String mlink = link.attr("href");
                        mBetLink.add(mlink);



                    }




                    Elements mElementAuthorName = mBlogDocument.select("div[class=ind-prev-content]").eq(i);

                    int mtitle2Size = mElementAuthorName.size();

                    for (int x = 0; x < mtitle2Size; x++) {

                        Elements mGame2Title = mElementAuthorName.select("div[class=prev-team-name]").eq(1);


                        String mAuthorName = mGame2Title.text();
                        mAuthorNameList.add(mAuthorName);

                    }

                    Elements mClubImage = mBlogDocument.select("div[class=ind-prev-content]").eq(i);

                    int mimageSize = mClubImage.size();

                    for (int b = 0; b < mimageSize; b++){

                        Elements mClubImage1 = mClubImage.select("div[class=prev-team-badge]").select("img").eq(b);

                        // for (int k = 0; k < mimage2Size; k++){


                        //  Elements mClub1Image2 = mClubImage3.select("img");

                        for (Element image : mClubImage1) {
                            String mImag = image.attr("data-src");
                            mImageList.add(mImag);
                        }
                        //  }





                    }



                    Elements mClubImage2 = mBlogDocument.select("div[class=ind-prev-content]").eq(i);

                    int mimage2Size = mClubImage2.size();

                    for (int j = 0; j < mimage2Size; j++){

                        Elements mClubImage3 = mClubImage2.select("div[class=prev-team-badge]").select("img").eq(1);

                        // for (int k = 0; k < mimage2Size; k++){


                        //  Elements mClub1Image2 = mClubImage3.select("img");

                        for (Element image : mClubImage3) {
                            String mImage = image.attr("data-src");
                            mImageList2.add(mImage);
                        }
                        //  }





                    }

                    Elements mElementBlogUploadDate = mBlogDocument.select("div[class=ind-prev-content]").eq(i);

                    int mTimeSize = mElementBlogUploadDate.size();

                    for (int t = 0; t < mTimeSize; t++){

                        Elements mTimeTitle = mElementBlogUploadDate.select("div[class=prev-info]").eq(t);
                        String mBlogUploadDate = mTimeTitle.text();
                        mBlogUploadDateList.add(mBlogUploadDate);

                    }


                    Elements mElementBlogTitle = mBlogDocument.select("div[class=ind-prev-content]").eq(i);

                    int mtitleSize = mElementBlogTitle.size();

                    for (int x = 0; x < mtitleSize; x++) {

                        Elements mGameTitle = mElementBlogTitle.select("div[class=prev-team-name]").eq(x);



                        String mBlogTitle = mGameTitle.text();
                        mBlogTitleList.add(mBlogTitle);
                    }

                    // mAuthorNameList.add(mAuthorName);
                    // mBlogUploadDateList.add(mBlogUploadDate);
                    // mBlogTitleList.add(mBlogTitle);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.act_recyclerview);

            DataAdapter mDataAdapter = new DataAdapter(Previous.this, mBlogTitleList, mImageList, mImageList2, mAuthorNameList, mBlogUploadDateList, mBetLink);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mDataAdapter);

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

}
