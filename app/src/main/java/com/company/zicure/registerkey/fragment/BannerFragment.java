package com.company.zicure.registerkey.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.company.zicure.registerkey.R;

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
    private int pager;

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
    public static BannerFragment newInstance(int pager) {
        BannerFragment fragment = new BannerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, pager);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pager = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_banner, container, false);
        imgBanner = (ImageView)root.findViewById(R.id.img_banner);
        imgBanner.setImageResource(pager);
        return root;
    }

}
