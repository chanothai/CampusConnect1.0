package com.company.zicure.registerkey.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.company.zicure.registerkey.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import gallery.zicure.company.com.modellibrary.models.ResponseUserInfo;
import gallery.zicure.company.com.modellibrary.utilize.ModelCart;
import gallery.zicure.company.com.modellibrary.utilize.ResizeScreen;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link IDCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IDCardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MultiFormatWriter multiFormatWriter = null;

    //View
    private ImageView imgProfileCard = null, imgQRCard = null;
    private TextView txtIDCard = null, txtMajor = null, txtStudy = null, txtFullName = null;


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
        imgQRCard = (ImageView) root.findViewById(R.id.img_qrcode_id_catd);
        txtIDCard = (TextView) root.findViewById(R.id.number_id_card);
        txtMajor = (TextView) root.findViewById(R.id.major_label);
        txtStudy = (TextView) root.findViewById(R.id.study_label);
        txtFullName = (TextView) root.findViewById(R.id.name_id_card);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null){
            resizeScaleImage();
            setInformation();
        }
    }

    private ResponseUserInfo.ResultUserInfo.DataUserInfo.User getInformation(){
        return ModelCart.getInstance().getUserInfo().getResult().getData().getUser();
    }

    private void setInformation(){
        if (getInformation().getImgPath() != null){
            Glide.with(getActivity())
                    .load(getInformation().getImgPath())
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfileCard);
        }

        txtFullName.setText(getInformation().getFirstName() + " " + getInformation().getLastName());
        txtIDCard.setText(getInformation().getCitizenID());
        txtMajor.setText("คณะ : วิทยาศาสตร์");
        txtStudy.setText("สาขา : วิทยาการคอมพิวเตอร์");

        generateQRCode(500, 500);
    }

    private void generateQRCode(int width, int height){
        if (multiFormatWriter == null){
            multiFormatWriter = new MultiFormatWriter();

            try{
                BitMatrix bitMatrix = multiFormatWriter.encode(getInformation().getCitizenID(), BarcodeFormat.QR_CODE, width, height);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                imgQRCard.setImageBitmap(bitmap);
            }catch (WriterException e){
                e.printStackTrace();
            }
        }
    }

    private void resizeScaleImage() {
        ResizeScreen resize = new ResizeScreen(getActivity());
        int width = resize.widthScreen(2) + 100;

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imgProfileCard.getLayoutParams();
        params.width = width;
        params.height = width;

        imgProfileCard.setLayoutParams(params);
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
