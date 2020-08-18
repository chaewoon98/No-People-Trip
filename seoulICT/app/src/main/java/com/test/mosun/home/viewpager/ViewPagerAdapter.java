package com.test.mosun.home.viewpager;

import android.view.LayoutInflater;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private LayoutInflater mInflater;
//    Context mContex
    int MAX_PAGE = 3;
    Fragment cur_fragment = new Fragment();
    Fragment[] fragments = new Fragment[3];

    public ViewPagerAdapter(FragmentManager fragmentManager) {
       super(fragmentManager);
        fragments[0] = new page_1();
        fragments[1] = new page_1();
        fragments[2] = new page_1();
    }
//    public ViewPagerAdapter(LayoutInflater inflater) {
//        this.mInflater = inflater;
//    }

    @Override
    public int getCount() {
        return MAX_PAGE;
//        return Integer.MAX_VALUE;
    }

//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view == object;
//    }
//
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        View view = mInflater.inflate(R.layout.fragment_view_page, null);
////        TextView textView = (TextView)view.findViewById(R.id.);
////        textView.setText("Fragment Page"+position);
//        container.addView(view);
//        return view;
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((View)object);
//    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return "Fragment "+position;
//    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        int realPos = position % (MAX_PAGE);
//        MyView = myView = new MyView(mContext);
//        // View 속성 설정
//        // 위의 realPos 기반으로 현재 뷰에서 보여주어야 하는 값을 세팅한다던가 하는 작업 필요
//        ((ViewPager) container).addView(myView);
//        return myView;
//    }

    public Fragment getItem(int position) {
        if (position < 0 || MAX_PAGE <= position)
            return null;
//        int realPosition = position % MAX_PAGE;

        switch (position) {
            case 0:
                cur_fragment = new page_1();
                break;
            case 1:
                cur_fragment = new page_1();  //page_2
                break;
            case 2:
                cur_fragment = new page_1();  //page_3
                break;
        }
        return cur_fragment;
    }

//    @Override
//    public int getCount() {
//        return Integer.MAX_VALUE;
//    }
//
////    @Override
////    public CharSequence getPageTitle(int position) {
////        String title = mTitleList.get(position % mActualTitleListSize);
////        return title;
////    }
//
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        int virtualPosition = position % MAX_PAGE;
//        return super.instantiateItem(container, virtualPosition);
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        int virtualPosition = position % MAX_PAGE;
//        super.destroyItem(container, virtualPosition, object);
//    }
}
