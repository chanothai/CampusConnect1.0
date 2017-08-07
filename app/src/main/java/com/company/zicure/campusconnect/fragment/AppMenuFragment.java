package com.company.zicure.campusconnect.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.company.zicure.campusconnect.R;

import gallery.zicure.company.com.modellibrary.common.BaseActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppMenuFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "url";

    // TODO: Rename and change types of parameters
    private String url;
    private String mParam2;

    private String token;

    //VIew
    public static WebView webView = null;
    private NestedScrollView scrollView = null;
    private WebSettings webSettings = null;

    public ValueCallback<Uri[]> mUploadMesssage = null;
    public Uri mCapturedImageURI = null;

    public AppMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param url Parameter 1.
     * @return A new instance of fragment AppMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppMenuFragment newInstance(String url) {
        AppMenuFragment fragment = new AppMenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_app_menu, container, false);
        webView = (WebView) root.findViewById(R.id.appView);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null){
            setWebView();
        }
    }

    public void setWebView(){
        ((BaseActivity)getActivity()).showLoadingDialog();

        webView.setWebViewClient(new AppBrowser());
        webView.setVerticalScrollBarEnabled(true);
        webView.setClickable(true);
        webView.requestFocus(View.FOCUS_DOWN);

        webSettings = webView.getSettings();

        // improve webView performance
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl(url);
    }
    
    public void clearCache(){
        try{
            webView.clearCache(true);
            webView.clearHistory();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public class AppBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            ((BaseActivity)getActivity()).dismissDialog();
        }
    }

    public void saveInstanceState(Bundle outState) {
        try {
            webView.saveState(outState);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void restoreInstanceState(Bundle savedInstanceState) {
        try{
            webView.restoreState(savedInstanceState);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
