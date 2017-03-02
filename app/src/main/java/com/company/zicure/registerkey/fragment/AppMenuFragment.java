package com.company.zicure.registerkey.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.company.zicure.registerkey.MainMenuActivity;
import com.company.zicure.registerkey.R;
import com.company.zicure.registerkey.utilize.JavaScriptInterface;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppMenuFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "url";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String url;
    private String mParam2;

    private String token;

    //VIew
    private WebView webView = null;
    private NestedScrollView scrollView = null;

    public AppMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param url Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppMenuFragment newInstance(String url, String param2) {
        AppMenuFragment fragment = new AppMenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, url);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_app_menu, container, false);
        webView = (WebView) root.findViewById(R.id.appView);
        scrollView = (NestedScrollView) root.findViewById(R.id.scrollViewFragment);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        scrollView.setNestedScrollingEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new AppBrowser());
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new JavaScriptInterface(), "Token");

        if (savedInstanceState == null){
            SharedPreferences pref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            token = pref.getString(getString(R.string.token_login), null);

            webView.loadUrl(url);
        }

        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    public class AppBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //filter url to same url between url blog and url of webview loadding
            try{
                StringBuilder builder = new StringBuilder();
                builder.append("(function() {");
                builder.append("var inputs = document.getElementById('UserToken');");
                builder.append("inputs.value = '"+token+"';");
                builder.append("document.forms[0].submit();");
                builder.append("})();");

                String result = "javascript:" + builder.toString();
                view.loadUrl(result);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
