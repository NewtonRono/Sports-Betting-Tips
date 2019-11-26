package com.bet.tips;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    private ArrayList<String> mBlogTitleList = new ArrayList<>();
    private ArrayList<String> mAuthorNameList = new ArrayList<>();
    private ArrayList<String> mImageList = new ArrayList<>();
    private ArrayList<String> mImageList2 = new ArrayList<>();
    private ArrayList<String> mBlogUploadDateList = new ArrayList<>();
    private ArrayList<String> mBetLink = new ArrayList<>();

    private InterstitialAd mInterstitialAd;


    private Activity mActivity;
    private int lastPosition = -1;

    public DataAdapter(MainActivity activity, ArrayList<String> mBlogTitleList, ArrayList<String> mImageList, ArrayList<String> mImageList2, ArrayList<String> mAuthorNameList,  ArrayList<String> mBlogUploadDateList, ArrayList<String> mBetLink) {
        this.mActivity = activity;
        this.mBlogTitleList = mBlogTitleList;
        this.mImageList = mImageList;
        this.mImageList2 = mImageList2;
        this.mAuthorNameList = mAuthorNameList;
        this.mBlogUploadDateList = mBlogUploadDateList;
        this.mBetLink = mBetLink;
    }

    public DataAdapter(Previous activity, ArrayList<String> mBlogTitleList, ArrayList<String> mImageList, ArrayList<String> mImageList2, ArrayList<String> mAuthorNameList,  ArrayList<String> mBlogUploadDateList, ArrayList<String> mBetLink) {
        this.mActivity = activity;
        this.mBlogTitleList = mBlogTitleList;
        this.mImageList = mImageList;
        this.mImageList2 = mImageList2;
        this.mAuthorNameList = mAuthorNameList;
        this.mBlogUploadDateList = mBlogUploadDateList;
        this.mBetLink = mBetLink;
    }

    public DataAdapter(Tommorow activity, ArrayList<String> mBlogTitleList, ArrayList<String> mImageList, ArrayList<String> mImageList2, ArrayList<String> mAuthorNameList,  ArrayList<String> mBlogUploadDateList, ArrayList<String> mBetLink) {
        this.mActivity = activity;
        this.mBlogTitleList = mBlogTitleList;
        this.mImageList = mImageList;
        this.mImageList2 = mImageList2;
        this.mAuthorNameList = mAuthorNameList;
        this.mBlogUploadDateList = mBlogUploadDateList;
        this.mBetLink = mBetLink;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_blog_title, tv_blog_author, tv_blog_upload_date;
        private ImageView tv_image1, tv_image2;
        private Button button;

        public MyViewHolder(View view) {
            super(view);
            tv_blog_title = (TextView) view.findViewById(R.id.row_tv_blog_title);
            tv_blog_author = (TextView) view.findViewById(R.id.row_tv_blog_author);
            tv_blog_upload_date = (TextView) view.findViewById(R.id.row_tv_blog_upload_date);
            tv_image1 = (ImageView) view.findViewById(R.id.imageView1);
            tv_image2 = (ImageView) view.findViewById(R.id.imageView2);
           button =  (Button) view.findViewById(R.id.button);




        }




    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_data, parent, false);





        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_blog_title.setText(mBlogTitleList.get(position));
        holder.tv_blog_author.setText(mAuthorNameList.get(position));
        holder.tv_blog_upload_date.setText(mBlogUploadDateList.get(position));


        Glide.with(holder.tv_image1.getContext()).load(mImageList.get(position)).into(holder.tv_image1);
        Glide.with(holder.tv_image2.getContext()).load(mImageList2.get(position)).into(holder.tv_image2);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String reLink = mBetLink.get(position);
                String Club1 = mBlogTitleList.get(position);
                String Club2 = mAuthorNameList.get(position);
                String Time = mBlogUploadDateList.get(position);
                String Image1 = mImageList.get(position);
                String Image2 = mImageList2.get(position);

                Intent intent = new Intent (view.getContext(), DetailActivity.class);
                intent.putExtra("betlink", reLink);
                intent.putExtra("club1", Club1);
                intent.putExtra("club2", Club2);
                intent.putExtra("time", Time);
                intent.putExtra("image1", Image1);
                intent.putExtra("image2",Image2);
                mActivity.startActivity(intent);

            }
        });




    }

    @Override
    public int getItemCount() {
        return mBlogTitleList.size();
    }
}
