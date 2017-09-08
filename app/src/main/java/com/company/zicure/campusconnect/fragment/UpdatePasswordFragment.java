package com.company.zicure.campusconnect.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.zicure.campusconnect.R;
import com.company.zicure.campusconnect.customView.ButtonView;
import com.company.zicure.campusconnect.customView.EditTextView;
import com.company.zicure.campusconnect.network.ClientHttp;

import gallery.zicure.company.com.modellibrary.common.BaseActivity;
import gallery.zicure.company.com.modellibrary.models.updatepassword.RequestForgotPassword;
import gallery.zicure.company.com.modellibrary.models.updatepassword.RequestUpdatePassword;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdatePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdatePasswordFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "username";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String username;
    private String mParam2;

    /** Make: View **/
    private TextInputLayout inputPass = null;
    private TextInputLayout inputConfirmPass = null;
    private EditTextView newPassEdit = null;
    private EditTextView confirmPassEdit = null;
    private ButtonView btnUpdatePass = null;

    public UpdatePasswordFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdatePasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdatePasswordFragment newInstance(String username, String param2) {
        UpdatePasswordFragment fragment = new UpdatePasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, username);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_update_password, container, false);
        bindView(root);
        return root;
    }

    private void bindView(View root) {
        inputPass = (TextInputLayout) root.findViewById(R.id.input_edit_update1);
        inputConfirmPass = (TextInputLayout) root.findViewById(R.id.input_edit_update2);
        newPassEdit = (EditTextView) root.findViewById(R.id.username_update);
        confirmPassEdit = (EditTextView) root.findViewById(R.id.email_update);
        btnUpdatePass = (ButtonView) root.findViewById(R.id.btn_update);
        btnUpdatePass.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState == null) {
            initView();
        }
    }

    private void initView(){
        inputPass.setHint(getString(R.string.text_password_th));
        inputConfirmPass.setHint(getString(R.string.confirm_password));

        newPassEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        newPassEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());

        confirmPassEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        confirmPassEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    @Override
    public void onClick(View v) {
        RequestUpdatePassword requestUpdate = new RequestUpdatePassword();
        RequestUpdatePassword.UserForgot user = new RequestUpdatePassword().new UserForgot();
        user.setUsername(username);
        user.setNewPassword(newPassEdit.getText().toString().trim());
        user.setConfirmPassword(confirmPassEdit.getText().toString());
        requestUpdate.setUser(user);

        ((BaseActivity) getActivity()).showLoadingDialog();
        ClientHttp.getInstance(getActivity()).updatePassword(requestUpdate);
    }
}
