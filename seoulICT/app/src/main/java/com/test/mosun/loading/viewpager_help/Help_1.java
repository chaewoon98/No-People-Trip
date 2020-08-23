package com.test.mosun.loading.viewpager_help;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.test.mosun.MainActivity;
import com.test.mosun.R;

public class Help_1 extends Fragment {
    private String title;
    private int page;

    public Help_1(){

    }
    // newInstance constructor for creating fragment with arguments
//    public static Help_1 newInstance(int page, String title) {
//        Help_1 fragment = new Help_1();
//        Bundle args = new Bundle();
////        args.putInt("someInt", page);
////        args.putString("someTitle", title);
//        fragment.setArguments(args);
//        return fragment;
//    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        page = getArguments().getInt("someInt", 0);
//        title = getArguments().getString("someTitle");

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_help, container, false);
////        EditText tvLabel = (EditText) view.findViewById(R.id.editText);
////        tvLabel.setText(page + " -- " + title);
//        return view;


        LinearLayout linearLayout=(LinearLayout)inflater.inflate(R.layout.help_1,container,false);
//        LinearLayout background=(LinearLayout)linearLayout.findViewById(R.id.background);
//        TextView page_num=(TextView)linearLayout.findViewById(R.id.page_num);
//        page_num.setText(String.valueOf(1));
//        background.setBackground(new ColorDrawable(0xff6dc6d2));

        Button skip = (Button) linearLayout.findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });


        return linearLayout;
    }
}
