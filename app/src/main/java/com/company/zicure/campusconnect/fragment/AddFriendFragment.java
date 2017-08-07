package com.company.zicure.campusconnect.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.zicure.campusconnect.R;
import com.company.zicure.campusconnect.network.ClientHttp;
import com.google.gson.Gson;
import com.google.zxing.Result;

import gallery.zicure.company.com.modellibrary.common.BaseActivity;
import gallery.zicure.company.com.modellibrary.models.contact.RequestAddContact;
import gallery.zicure.company.com.modellibrary.models.contact.RequestAddContact.Contact;
import gallery.zicure.company.com.modellibrary.utilize.ModelCart;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFriendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFriendFragment extends Fragment implements ZXingScannerView.ResultHandler {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /** Make: View **/
    private ZXingScannerView scannerView = null;


    public AddFriendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFriendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFriendFragment newInstance(String param1, String param2) {
        AddFriendFragment fragment = new AddFriendFragment();
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
        View root = inflater.inflate(R.layout.fragment_add_friend, container, false);
        onBindView(root);
        return root;
    }

    private void onBindView(View view){
        scannerView = (ZXingScannerView) view.findViewById(R.id.view_scan_qr);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null){
            initCamera();
        }
    }

    private void initCamera(){
        scannerView.setResultHandler(this);
    }

    @Override
    public void handleResult(Result result) {
        setRequestAddContact(result.getText());
    }

    private void setRequestAddContact(String qrcode){
        Contact contact = new Contact();
        contact.setToken(ModelCart.getInstance().getKeyModel().getToken());
        contact.setUserContact(qrcode);

        RequestAddContact addContact = new RequestAddContact();
        addContact.setContact(contact);

        String resultSJON = new Gson().toJson(addContact);
        Log.d("AddContact", resultSJON);

        ((BaseActivity) getActivity()).showLoadingDialog();
        ClientHttp.getInstance(getActivity()).requestAddContact(addContact);
    }

    public void resumeCamera(){
        initCamera();
        scannerView.resumeCameraPreview(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        scannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }
}
