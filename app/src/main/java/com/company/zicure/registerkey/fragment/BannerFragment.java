package com.company.zicure.registerkey.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.company.zicure.registerkey.R;

import gallery.zicure.company.com.modellibrary.utilize.ResizeScreen;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BannerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BannerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "pager";

    // TODO: Rename and change types of parameters
    private String pager;

    private ImageView imgBanner;

    public BannerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BannerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BannerFragment newInstance(String pager) {
        BannerFragment fragment = new BannerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, pager);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pager = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_banner, container, false);
        imgBanner = (ImageView)root.findViewById(R.id.img_banner);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null){
            setLayoutParamsBanner();
            setImageBanner();
        }
    }

    private void setLayoutParamsBanner(){
        ResizeScreen resizeScreen = new ResizeScreen(getActivity());
        int height = resizeScreen.widthScreen(2);
        int widht = resizeScreen.widthScreen(1);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) imgBanner.getLayoutParams();
        params.height = height;
        params.width = widht;
        imgBanner.setLayoutParams(params);
    }

    private void setImageBanner(){
        Glide.with(getActivity())
                .load(pager)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgBanner);
    }
}
