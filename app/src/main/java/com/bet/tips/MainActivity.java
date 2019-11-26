package com.bet.tips;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    private String link = "https://www.freesupertips.com/previews/?date=";
    private ArrayList<String> mAuthorNameList = new ArrayList<>();
   private ArrayList<String> mImageList = new ArrayList<>();
  private ArrayList<String> mImageList2 = new ArrayList<>();
    private ArrayList<String> mBlogUploadDateList = new ArrayList<>();
    private ArrayList<String> mBlogTitleList = new ArrayList<>();
    private ArrayList<String> mBetLink = new ArrayList<>();
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;


    private Button button1, button2;
    private ProgressBar progressBar;
    private TextView text;

    private String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

   // ImageView mImageList;
    //ImageView mImageList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(!isConnected(MainActivity.this)) buildDialog(MainActivity.this).show();
        else{

        }
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




      ActionBar actionBar = getSupportActionBar();

      actionBar.setTitle("Predictions for " + date);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);




        new Description().execute();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this,Previous.class);
                    startActivity(i);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent i = new Intent(MainActivity.this,Tommorow.class);
                    startActivity(i);

            }
        });


    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }

    private class Description extends AsyncTask<Void, Void, Void> {
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
          // mProgressDialog.setTitle("Bet Predictions");
            mProgressDialog.setMessage("Loading data for " + date);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();






        }

        @Override
        protected Void doInBackground(Void... params) {
            try {


                String url = link + date;


                // Connect to the web site
                Document mBlogDocument = Jsoup.connect(url).get();




                // Using Elements to get the Meta data
               Elements mElementDataSize = mBlogDocument.select("a[class=ind-prev-holder]");
                //Elements mElementDataSize = mBlogDocument.select("div[class=ui list]");
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

            DataAdapter mDataAdapter = new DataAdapter(MainActivity.this, mBlogTitleList, mImageList, mImageList2, mAuthorNameList, mBlogUploadDateList, mBetLink);
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
