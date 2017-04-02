package profilemof.zicure.company.com.profilemof.fragment;


import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.MultiFormatWriter;
import com.squareup.otto.Subscribe;

import org.json.JSONObject;

import java.util.ArrayList;

import gallery.zicure.company.com.modellibrary.models.BaseResponse;
import gallery.zicure.company.com.modellibrary.models.DataModel;
import gallery.zicure.company.com.modellibrary.models.RequestUserCode;
import gallery.zicure.company.com.modellibrary.models.UserRequest;
import gallery.zicure.company.com.modellibrary.utilize.ModelCart;
import gallery.zicure.company.com.modellibrary.utilize.ResizeScreen;
import gallery.zicure.company.com.modellibrary.utilize.VariableConnect;
import profilemof.zicure.company.com.profilemof.R;
import profilemof.zicure.company.com.profilemof.activity.ProfileActivity;
import profilemof.zicure.company.com.profilemof.networks.ClientHttp;
import profilemof.zicure.company.com.profilemof.security.EncryptionAES;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActivateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivateFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBE

    // TODO: Rename and change types of parameters
    private String[] arrUser;

    private MultiFormatWriter multiFormatWriter = null;

    //View
    private EditText editCode = null;
    private Button btnActivate = null;

    private String authCode = null;
    private String token = null;
    private String username = null;
    private byte[] key = null;


    public ActivateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddCashFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivateFragment newInstance(String[] arrUser) {
        ActivateFragment fragment = new ActivateFragment();
        Bundle args = new Bundle();
        args.putStringArray(VariableConnect.userSecret, arrUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            arrUser = getArguments().getStringArray(VariableConnect.userSecret);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_activate, container, false);
        editCode = (EditText) root.findViewById(R.id.edit_code);
        btnActivate = (Button) root.findViewById(R.id.btn_activate);
        btnActivate.setOnClickListener(this);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null){
            token = ModelCart.getInstance().getKeyModel().getToken();
            username = ModelCart.getInstance().getKeyModel().getUsername();
            authCode = ModelCart.getInstance().getKeyModel().getAuthCode();
            key = ModelCart.getInstance().getKeyModel().getKey();

            setParamBtn();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_activate) {
            authCode = editCode.getText().toString().trim();
            createModel();
        }
    }

    private void setParamBtn(){
        ResizeScreen screen = new ResizeScreen(getActivity());
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)btnActivate.getLayoutParams();
        params.width = screen.widthScreen(3);
        params.height = screen.widthScreen(3);
        btnActivate.setLayoutParams(params);
    }

    private void createModel(){
        RequestUserCode request = new RequestUserCode();
        RequestUserCode.DeviceToken deviceToken = new RequestUserCode.DeviceToken();
        deviceToken.setUserCode(authCode);

        UserRequest user = new UserRequest();
        user.setToken(token);

        request.setDeviceToken(deviceToken);
        request.setUser(user);

        Log.d("RequestUserCode", new Gson().toJson(request));
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String encrypt = EncryptionAES.newInstance(key).encrypt(gson.toJson(request));

        if (!encrypt.isEmpty()){
            DataModel dataModel = new DataModel();
            dataModel.setData(encrypt);

            DataModel.User dataUser = new DataModel.User();
            dataUser.setUsername(username);
            dataModel.setUser(dataUser);

            ((ProfileActivity) getActivity()).showLoadingDialog();
            ClientHttp.getInstance(getActivity()).approveDevice(dataModel);
        }
    }

}
