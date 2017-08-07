package com.company.zicure.campusconnect.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.company.zicure.campusconnect.R;

import java.util.List;

import gallery.zicure.company.com.modellibrary.models.bloc.ResponseBlocUser;
import gallery.zicure.company.com.modellibrary.utilize.ModelCart;

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

    private int currentPager = 0;

    //create dots
    private TextView[] dots;
    private LinearLayout dotLayout;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_banner, container, false);
        imgBanner = (ImageView)root.findViewById(R.id.banner_img);
        dotLayout = (LinearLayout) root.findViewById(R.id.layoutDot);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null){
            setImageBanner();
            addBottomDots(pager);
        }
    }

    private List<ResponseBlocUser.ResultBlocUser.DataBloc.UserAccessControl> getCategoryData(){
        return ModelCart.getInstance().getUserBloc().getResult().getData().getBloc();
    }

    private void setImageBanner(){
        Glide.with(getActivity())
                .load(getCategoryData().get(pager).getCategory().getImagePath())
                .fitCenter()
                .override(768, 432)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgBanner);
    }

    private void addBottomDots(int currentPage){
        try{
            dots = new TextView[getCategoryData().size()];
            dotLayout.removeAllViews();
            for (int i = 0; i < dots.length; i++){
                dots[i] = new TextView(getActivity());
                dots[i].setText(Html.fromHtml("&#8226;"));
                dots[i].setTextSize(35);
                dots[i].setTextColor(getResources().getColor(R.color.color_dot_inactive));
                dotLayout.addView(dots[i]);
            }

            if (dots.length > 0){
                dots[currentPage].setTextColor(getResources().getColor(R.color.color_dot_active));
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
