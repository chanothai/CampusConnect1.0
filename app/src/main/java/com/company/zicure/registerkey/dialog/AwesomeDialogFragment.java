package com.company.zicure.registerkey.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.company.zicure.registerkey.R;
import com.company.zicure.registerkey.customView.ButtonDialogView;
import com.company.zicure.registerkey.customView.EditTextView;
import com.company.zicure.registerkey.customView.LabelView;

/**
 * Created by Pakgon on 7/24/2017 AD.
 */

public class AwesomeDialogFragment extends DialogFragment {

    //Properties
    private static final String KEY_TITLE = "key_title";
    private static final String KEY_MESSAGE = "key_message";
    private static final String KEY_POSITIVE = "key_positive";
    private static final String KEY_NEGATIVE  = "key_negative";
    private static final String KEY_HASPIN = "key_has_pin";

    private int title;
    private int message;
    private int positive;
    private int negative;
    private boolean hasPIN;

    //View
    private LabelView titleDialog = null;
    private LabelView messageDialog = null;
    private ButtonDialogView btnDialogPositive = null;
    private ButtonDialogView btnDialogNegative = null;
    private EditTextView editPin = null;

    public static AwesomeDialogFragment newInstance(@StringRes int title, @StringRes int message, @StringRes int positive, @StringRes int negative, boolean hasPIN) {
        AwesomeDialogFragment fragment = new AwesomeDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TITLE, title);
        bundle.putInt(KEY_MESSAGE, message);
        bundle.putInt(KEY_POSITIVE, positive);
        bundle.putInt(KEY_NEGATIVE, negative);
        bundle.putBoolean(KEY_HASPIN, hasPIN);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            restoreArguments(getArguments());
        }else{
            restoreInstanceState(getArguments());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getDialog().setCanceledOnTouchOutside(false);
        setCancelable(false);

        View root = inflater.inflate(R.layout.fragment_awesome_dialog, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        setupView();
    }

    private void bindView(View view) {
        titleDialog = (LabelView) view.findViewById(R.id.title_dialog);
        messageDialog = (LabelView) view.findViewById(R.id.txt_message_dialog);
        btnDialogPositive = (ButtonDialogView) view.findViewById(R.id.btn_dialog_positive);
        btnDialogNegative = (ButtonDialogView) view.findViewById(R.id.btn_dialog_negative);
        editPin = (EditTextView) view.findViewById(R.id.edit_pin_dialog);
    }

    private void setupView(){
        if (hasPIN) {
            editPin.setVisibility(View.VISIBLE);
        }

        titleDialog.setText(title);
        messageDialog.setText(message);
        btnDialogPositive.setText(positive);
        btnDialogNegative.setText(negative);

        btnDialogPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnDialogListener listener = getOnDialogListener();
                if (listener != null) {
                    String pinCodeStr = editPin.getText().toString().trim();
                    if (hasPIN) {
                        if (!pinCodeStr.isEmpty()){
                            listener.onPositiveButtonClick(pinCodeStr);
                            dismiss();
                        }else{
                            listener.onPositiveButtonClick(null);
                        }
                    }else{
                        dismiss();
                    }
                }
            }
        });

        btnDialogNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnDialogListener listener = getOnDialogListener();
                if (listener != null) {
                    listener.onNegativeButtonClick();
                }

                dismiss();
            }
        });
    }

    private OnDialogListener getOnDialogListener() {
        Fragment fragment = getParentFragment();
        try{
            if (fragment != null) {
                return (OnDialogListener) fragment;
            }else{
                return (OnDialogListener) getActivity();
            }
        }catch (ClassCastException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void restoreInstanceState(Bundle bundle){
        title = bundle.getInt(KEY_TITLE);
        message = bundle.getInt(KEY_MESSAGE);
        positive = bundle.getInt(KEY_POSITIVE);
        negative = bundle.getInt(KEY_NEGATIVE);
        hasPIN = bundle.getBoolean(KEY_HASPIN);
    }

    private void restoreArguments(Bundle bundle) {
        title = bundle.getInt(KEY_TITLE);
        message = bundle.getInt(KEY_MESSAGE);
        positive = bundle.getInt(KEY_POSITIVE);
        negative = bundle.getInt(KEY_NEGATIVE);
        hasPIN = bundle.getBoolean(KEY_HASPIN);
    }

    public interface OnDialogListener {
        void onPositiveButtonClick(String pinCode);
        void onNegativeButtonClick();
    }

    public static class Builder {
        private int title;
        private int message;
        private int positive;
        private int negative;
        private boolean hasPIN;

        public Builder() {

        }

        public Builder setTitle(@StringRes int title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(@StringRes int message) {
            this.message = message;
            return this;
        }

        public Builder setPositive(@StringRes int positive) {
            this.positive = positive;
            return this;
        }

        public Builder setNegative(@StringRes int negative) {
            this.negative = negative;
            return this;
        }

        public Builder setPIN(boolean hasPIN) {
            this.hasPIN = hasPIN;
            return this;
        }

        public AwesomeDialogFragment build() {
            return AwesomeDialogFragment.newInstance(title, message, positive, negative, hasPIN);
        }
    }
}
