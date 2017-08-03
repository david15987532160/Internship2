package com.example.quocanhnguyen.retrofitexample.UI.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.quocanhnguyen.retrofitexample.R;
import com.example.quocanhnguyen.retrofitexample.model.data.prefs.SharedPrefs;
import com.snappydb.SnappydbException;

public class FragmentDetail extends Fragment implements View.OnClickListener {
    private WebView webView;
    private Bundle bundle;
    private String url = "";
    private int ID = 0;
    private boolean isLiked = false;

    public FragmentDetail() throws SnappydbException {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        webView = (WebView) view.findViewById(R.id.webView);
        view.findViewById(R.id.addFavorite).setOnClickListener(this);
        view.findViewById(R.id.buttonBack).setOnClickListener(this);
        view.findViewById(R.id.buttonForward).setOnClickListener(this);

        bundle = getArguments();
        ID = bundle.getInt("Id");

        for (int i = 0; i <SharedPrefs.ID.size(); ++i){
            if (String.valueOf(ID).equals(SharedPrefs.ID.get(i))){
                isLiked = true;
            }
        }
        if (isLiked == true)
            view.findViewById(R.id.addFavorite).setBackgroundResource(R.drawable.star);
        webView.setWebChromeClient(new MyWebChromeClient(getContext()));
//        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setSupportZoom(true);
//        webView.getSettings().setBuiltInZoomControls(true);
//        webView.getSettings().setDisplayZoomControls(true);
        if (bundle != null) {
            url = "https://www.themoviedb.org/movie/" + ID;
            webView.loadUrl(url);
        }
        return view;
    }

    private class MyWebChromeClient extends WebChromeClient {
        Context context;

        public MyWebChromeClient(Context context) {
            super();
            this.context = context;
        }
    }

    @Override
    public void onClick(View v) {
        boolean isAdded = false;
        switch (v.getId()) {
            case R.id.addFavorite:
                for (int i = 0; i < SharedPrefs.ID.size(); ++i) {
                    if (String.valueOf(ID).equals(SharedPrefs.ID.get(i))) {
                        isAdded = true;
                    }
                }
                if (!isAdded) {
                    SharedPrefs.ID.add(new String(String.valueOf(ID)));
                    v.findViewById(R.id.addFavorite).setBackgroundResource(R.drawable.star);
                    Toast.makeText(getContext(), bundle.getString("title") + " is added to favorite", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < SharedPrefs.ID.size(); ++i){
                        if (String.valueOf(ID).equals(SharedPrefs.ID.get(i))){
                            SharedPrefs.ID.remove(i);
                        }
                    }
                    v.findViewById(R.id.addFavorite).setBackgroundResource(R.drawable.ic_star_border);
                    Toast.makeText(getContext(), bundle.getString("title") + " is removed from your favorite list", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonBack:
                if (webView.canGoBack())
                    webView.goBack();
                break;
            case R.id.buttonForward:
                if (webView.canGoForward())
                    webView.goForward();
                break;
            default:
                break;
        }
    }
}
