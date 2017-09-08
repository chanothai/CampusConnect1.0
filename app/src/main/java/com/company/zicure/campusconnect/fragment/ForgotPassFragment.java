package com.company.zicure.campusconnect.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.zicure.campusconnect.R;
import com.company.zicure.campusconnect.customView.ButtonView;
import com.company.zicure.campusconnect.customView.EditTextView;
import com.company.zicure.campusconnect.network.ClientHttp;

import gallery.zicure.company.com.modellibrary.common.BaseActivity;
import gallery.zicure.company.com.modellibrary.models.updatepassword.RequestForgotPassword;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgotPassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgotPassFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /** Make: View **/
    private EditTextView usernameEdit = null;
    private EditTextView emailEdit = null;
    private ButtonView btnUpdate = null;

    public ForgotPassFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgotPassFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgotPassFragment newInstance(String param1, String param2) {
        ForgotPassFragment fragment = new ForgotPassFragment();
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
        View root = inflater.inflate(R.layout.fragment_forgot_pass, container, false);
        onBindView(root);
        return root;
    }

    private void onBindView(View view){
        usernameEdit = (EditTextView) view.findViewById(R.id.username_update);
        emailEdit = (EditTextView) view.findViewById(R.id.email_update);
        btnUpdate = (ButtonView) view.findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);
    }

    public String getUsername(){
        return usernameEdit.getText().toString().trim();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        RequestForgotPassword request = new RequestForgotPassword();
        RequestForgotPassword.UserUpdate user = new RequestForgotPassword().new UserUpdate();
        user.setUsername(usernameEdit.getText().toString().trim());
        user.setEmail(emailEdit.getText().toString().trim());

        request.setUserUpdate(user);

        ((BaseActivity) getActivity()).showLoadingDialog();
        ClientHttp.getInstance(getActivity()).forgotPassword(request);
    }
}
