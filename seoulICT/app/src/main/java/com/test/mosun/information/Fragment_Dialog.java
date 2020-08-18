package com.test.mosun.information;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.test.mosun.AppManager;
import com.test.mosun.R;

public class Fragment_Dialog extends DialogFragment {

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_mask_stamp_popup, container, false);
        TextView maskStamp = view.findViewById(R.id.maskCount);
        TextView maskDescription = view.findViewById(R.id.maskDescription);
        ImageView backButton = (ImageView) view.findViewById(R.id.maskPopupClose);
        String maskCountString = String.valueOf(AppManager.getInstance().maskCount);
        String maskDescriptionString = String.valueOf(5-AppManager.getInstance().maskCount);
        maskStamp.setText(maskCountString+"개 적립됨");
        maskDescription.setText("보상까지 "+maskDescriptionString+"개 남음");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

}