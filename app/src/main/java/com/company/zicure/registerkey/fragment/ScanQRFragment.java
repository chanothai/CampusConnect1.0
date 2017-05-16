package com.company.zicure.registerkey.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.company.zicure.registerkey.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScanQRFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScanQRFragment extends Fragment implements ZXingScannerView.ResultHandler{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ZXingScannerView mScannerView = null;

    //View
    private ZXingScannerView scannerView = null;


    public ScanQRFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScanQRFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScanQRFragment newInstance(String param1, String param2) {
        ScanQRFragment fragment = new ScanQRFragment();
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
        View root = inflater.inflate(R.layout.fragment_scan_qr, container, false);
        scannerView = (ZXingScannerView) root.findViewById(R.id.view_scan_qr);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null){
            qrScanner();
        }
    }

    @Override
    public void handleResult(Result result) {
        Toast.makeText(getActivity(), "" + result, Toast.LENGTH_SHORT).show();
    }

    public void qrScanner(){
//        mScannerView = new ZXingScannerView(getActivity());
//        getActivity().setContentView(mScannerView);
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public void onResume() {
        super.onResume();
        scannerView.resumeCameraPreview(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }
}
