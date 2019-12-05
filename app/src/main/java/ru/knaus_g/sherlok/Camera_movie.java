package ru.knaus_g.sherlok;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.webkit.WebView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

public class Camera_movie extends Activity {


   /* public class GifWebView extends WebView{

        public GifWebView(Context context, String path){
            super(context);
            loadUrl(path);
        }

    }

    */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_movie);
/*
        GifWebView gifWebView = new GifWebView(this, "file:///android_asset/dog.gif");
        setContentView(gifWebView);

 */
    }

}
