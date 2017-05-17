package com.company.zicure.registerkey.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.company.zicure.registerkey.R;
import com.company.zicure.registerkey.activity.BlocContentActivity;

import java.io.File;
import java.io.IOException;

import gallery.zicure.company.com.modellibrary.utilize.JavaScriptInterface;
import gallery.zicure.company.com.modellibrary.utilize.VariableConnect;

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
            SharedPreferences pref = getActivity().getSharedPreferences(VariableConnect.keyFile, Context.MODE_PRIVATE);
            token = pref.getString(getString(R.string.token_login), null);

            setWebView();
        }
    }

    public void setWebView(){
        ((BlocContentActivity)getActivity()).showLoadingDialog();

        webView.setWebViewClient(new AppBrowser());
        webView.setWebChromeClient(new AppBrowserChrome());
        webView.setVerticalScrollBarEnabled(true);
        webView.setClickable(true);

        webSettings = webView.getSettings();

        // improve webView performance
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);

//        String cookies = CookieManager.getInstance().getCookie(url);
//        if (cookies!= null){
//            Log.d("tag_cookies", cookies);
//            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//            webSettings.setAppCacheEnabled(true);
//        }

        webView.addJavascriptInterface(new JavaScriptInterface(), "Token");
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

    public void clearCookies(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        }else{
            CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(getActivity());
            cookieSyncManager.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncManager.stopSync();
            cookieSyncManager.sync();
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
//            //filter url to same url between url blog and url of webview loadding
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

            ((BlocContentActivity)getActivity()).dismissDialog();
        }
    }

    public class AppBrowserChrome extends WebChromeClient {

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            mUploadMesssage = filePathCallback;

            File imageStoragePath = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "Connect"
            );
            if (!imageStoragePath.exists()) {
                imageStoragePath.mkdirs();
            }

            try {
                File file = File.createTempFile(String.valueOf(System.currentTimeMillis()), ".jpg", imageStoragePath);
                mCapturedImageURI = Uri.fromFile(file);

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                }

                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("image/*");

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { takePictureIntent });

                startActivityForResult(chooserIntent, 2500);
                return true;

            } catch (IOException ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }

    public void saveInstanceState(Bundle outState) {
        webView.saveState(outState);
    }

    public void restoreInstanceState(Bundle savedInstanceState) {
        webView.restoreState(savedInstanceState);
    }
}
