package com.company.zicure.campusconnect.fragment;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.company.zicure.campusconnect.R;
import com.company.zicure.campusconnect.adapter.IDCardAdapter;
import com.company.zicure.campusconnect.customView.LabelView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import gallery.zicure.company.com.modellibrary.models.profile.ResponseIDCard;
import gallery.zicure.company.com.modellibrary.utilize.ModelCart;
import gallery.zicure.company.com.modellibrary.utilize.ResizeScreen;
import gallery.zicure.company.com.modellibrary.models.profile.ResponseIDCard.ResultProfile.ProfileData;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link IDCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IDCardFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    //MAKE : Parameters
    private String mParam1;
    private String mParam2;
    private MultiFormatWriter multiFormatWriter = null;
    private BitMatrix bitMatrix = null;
    private BarcodeEncoder barcodeEncoder = null;
    private int width = 0, statusIMG = 0;
    private ProfileData information = null;

    //MAKE : View
    private ImageView imgProfileCard = null, imgSwitch = null;
    private LabelView screenNameTH = null, screenNameEN = null;
    private RecyclerView listInformation = null;

    public IDCardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IDCardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IDCardFragment newInstance(String param1, String param2) {
        IDCardFragment fragment = new IDCardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_idcard, container, false);
        imgProfileCard = (ImageView) root.findViewById(R.id.img_id_card);
        imgSwitch = (ImageView) root.findViewById(R.id.icon_change_img);
        screenNameTH = (LabelView) root.findViewById(R.id.name_id_card);
        screenNameEN = (LabelView) root.findViewById(R.id.name_en_id_card);
        listInformation = (RecyclerView) root.findViewById(R.id.list_information);

        imgSwitch.setOnClickListener(this);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null){
            resizeScaleImage();
            setInformation();

            listInformation.setLayoutManager(new LinearLayoutManager(getActivity()));
            listInformation.setAdapter(new IDCardAdapter(information));
            listInformation.setItemAnimator(new DefaultItemAnimator());
        }
    }

    private ResponseIDCard.ResultProfile.ProfileData getInformation(){
        return ModelCart.getInstance().getProfile();
    }

    private void setInformation(){
        information = getInformation();
        changedImage();
        screenNameTH.setText(information.getFirstNameTH() + " " + information.getLastNameTH());
        screenNameEN.setText(information.getFirstNameEN()+ " " + information.getLastNameEN());
    }

    private Bitmap generateQRCode(int width, int height){
        if (multiFormatWriter == null){
            multiFormatWriter = new MultiFormatWriter();

            try{
                bitMatrix = multiFormatWriter.encode(information.getUsername(), BarcodeFormat.QR_CODE, width, height);
                barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                return bitmap;
            }catch (WriterException e){
                e.printStackTrace();
                return null;
            }
        }

        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
        return bitmap;
    }

    @TargetApi(21)
    private void changedImage(){
        switch (statusIMG){
            case 0: {
                if (getInformation().getImgPath() != null) {
                    Glide.with(getActivity())
                            .load(getInformation().getImgPath())
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imgProfileCard);

                    imgSwitch.setImageDrawable(getActivity().getDrawable(R.drawable.ic_qrcode));
                    statusIMG = 1;
                }break;
            }
            case 1:{
                imgProfileCard.setImageBitmap(generateQRCode(width,width));
                imgSwitch.setImageResource(R.drawable.icon_person);
                statusIMG = 0;
            }
        }
    }

    private void resizeScaleImage() {
        ResizeScreen resize = new ResizeScreen(getActivity());
        width = resize.widthScreen(2) + 100;

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imgProfileCard.getLayoutParams();
        params.width = width;
        params.height = width;

        imgProfileCard.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.icon_change_img:{
                changedImage();
                break;
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
}
