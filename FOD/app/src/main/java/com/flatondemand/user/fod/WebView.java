package com.flatondemand.user.fod;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flatondemand.user.fod.App.Constant;
import com.flatondemand.user.fod.App.NetworkStatus;

public class WebView extends AppCompatActivity {
    android.webkit.WebView  webView;
    private String postUrl , title ;

    private ProgressBar progressBar;
    private ImageView imgHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        postUrl= getIntent().getStringExtra("URL");
        title=getIntent().getStringExtra("Title");
        webView = (android.webkit.WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imgHeader = (ImageView) findViewById(R.id.backdrop);
        // initializing toolbar
        initCollapsingToolbar();

        webView.getSettings().setJavaScriptEnabled(true);
        if (NetworkStatus.isInternetAvailable(WebView.this)) {
            webView.loadUrl(postUrl);
        }
        else{
            Toast.makeText(getApplicationContext() , "No Internet Connectivity" , Toast.LENGTH_SHORT).show();
        }
        webView.setHorizontalScrollBarEnabled(false);


    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the txtPostTitle when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(title);
                    isShow = true;
                    progressBar.setVisibility(View.GONE);
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });

        // loading toolbar header image
        Glide.with(getApplicationContext()).load(Constant.ROOT_URL_DOMAIN+"/assets/image/image_logo_back.png")
                .thumbnail(0.5f)
                .into(imgHeader);


    }
}
