package com.example.quocanhnguyen.retrofitexample.UI.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public FragmentDetail() throws SnappydbException {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        webView = (WebView) view.findViewById(R.id.webView);
        view.findViewById(R.id.addFavorite).setOnClickListener(this);
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        FragmentDetail fragmentDetail = (FragmentDetail) getFragmentManager().findFragmentByTag("detailFrag");

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
                SharedPrefs.put(SharedPrefs.ID, bundle.getInt("Id"));
                Toast.makeText(getContext(), bundle.getString("title") + " movie is added to favorite", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.showFavorite:
//                Toast.makeText(getContext(), bundle.getString("title") + " is in favorite list", Toast.LENGTH_SHORT).show();
//                break;
            default:
                break;
        }
    }

}
