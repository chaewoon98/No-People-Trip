package com.test.mosun.loading.viewpager_help;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.test.mosun.MainActivity;
import com.test.mosun.R;

public class Help_5 extends Fragment {
    private String title;
    private int page;

    public Help_5(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LinearLayout linearLayout=(LinearLayout)inflater.inflate(R.layout.help_2,container,false);

        ImageView image=(ImageView) linearLayout.findViewById(R.id.help_image);
        TextView text = (TextView) linearLayout.findViewById(R.id.help_text);

        image.setImageResource(R.drawable.help5);
        text.setText("다녀온 관광지도 \n" +
                "한눈에 확인 가능해요!\n");

        Button skip = (Button) linearLayout.findViewById(R.id.start);
        skip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return linearLayout;
    }
}