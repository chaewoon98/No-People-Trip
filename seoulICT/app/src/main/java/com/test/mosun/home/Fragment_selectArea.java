package com.test.mosun.home;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.test.mosun.AppManager;
import com.test.mosun.MainActivity;
import com.test.mosun.R;
import com.test.mosun.information.Fragment_MyStamp;
import com.test.mosun.stamp.Fragment_Stamp;

import java.util.List;

public class Fragment_selectArea extends Fragment {

    private static final long MIN_CLICK_INTERVAL = 600;
    private long mLastClickTime;
    List<areaItem> list;
    areaItem item;

    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성합니다.
    public static Fragment_selectArea newInstance() {
        return new Fragment_selectArea();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.fragment_select_area, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        ImageButton back_btn = (ImageButton)view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {  //뒤로가기 버튼 누르면 마이페이지(이전 페이지)로 돌아간다.

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(Fragment_Home.newInstance());
            }
        });

        list = AppManager.getInstance().getAreaList();

        ListView listView = view.findViewById(R.id.listview_select_area);
        listView.setVisibility(View.VISIBLE);
        AreaAdapter areaAdapter = new AreaAdapter(getContext(), R.layout.item_area_list, list);
        listView.setAdapter(areaAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                long currentClickTime = SystemClock.uptimeMillis();
                long elapsedTime = currentClickTime - mLastClickTime;
                mLastClickTime = currentClickTime;

                // 중복 클릭인 경우
                if (elapsedTime <= MIN_CLICK_INTERVAL) {
                    return;
                }

                //스탬프 fragment로 이동 + 지역 말해주기
                Fragment bundleFragment = Fragment_Stamp.newInstance();
                Bundle bundle = new Bundle();
                item = list.get(i);
                bundle.putString("area",item.getName());
                bundleFragment.setArguments(bundle);
                Fragment newFragment = new Fragment_MyStamp();
                ((MainActivity)getActivity()).replaceFragment(bundleFragment);
            }
        });

        return view;
    }

}
class AreaAdapter extends BaseAdapter {
    private Context context;
    int layout;
    List<areaItem> list;
    areaItem  item;
    LayoutInflater layoutInflater;

    public AreaAdapter(Context context, int layout, List<areaItem> list) {
        this.list = list;
        this.layout = layout;
        this.context = context;

        layoutInflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = layoutInflater.inflate(layout, null);


        item = list.get(i);
//        ImageView imageView = view.findViewById(R.id.image_area_thumb);
        TextView textName = view.findViewById(R.id.name);
        textName.setText(item.getName());
        TextView textDescription = view.findViewById(R.id.description);
        textDescription.setText(item.getDescription());
        view.setBackground(ContextCompat.getDrawable(context, R.drawable.border_normal_khaki));
        //imageView.setImageResource(Integer.parseInt(item.getImg_url()));
//        try {
//            Field field = R.drawable.class.getField(item.getImg_url());
//            int drawableID = field.getInt(null);
//            Glide.with(view).load(drawableID).into(imageView);
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }





        return view;
    }

}

//backspace 처리 필요
