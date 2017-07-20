package com.example.quocanhnguyen.retrofitexample.model.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.quocanhnguyen.retrofitexample.R;
import com.snappydb.SnappydbException;

public class FragmentDetail extends Fragment implements View.OnClickListener {
    WebView webView;
    Bundle bundle;
    String url = "";
//    DB snapdb = DBFactory.open(getContext(), "favorite movies");

    public FragmentDetail() throws SnappydbException {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        webView = (WebView) view.findViewById(R.id.webView);
        view.findViewById(R.id.addFavorite).setOnClickListener(this);

        bundle = getArguments();

        webView.setWebViewClient(new WebViewClient());
        if (bundle != null) {
            url = "https://www.themoviedb.org/movie/" + bundle.getInt("Id");
            webView.loadUrl(url);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addFavorite:
//                try {
//                    snapdb.put("url", url);
//                    snapdb.putInt("id", bundle.getInt("Id"));
//                } catch (SnappydbException e) {
//                    e.printStackTrace();
//                }
                break;
            default:
                break;
        }
    }
}
